package com.example.test

import android.app.DownloadManager
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.webkit.JavascriptInterface
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import kotlin.math.log


//class MainActivity : AppCompatActivity() {
//    private lateinit var webView: WebView
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        webView = findViewById(R.id.webView)
//
//        // 配置 WebView 设置
//        webView.settings.javaScriptEnabled = true // 启用 JavaScript
//
//        // 设置 WebViewClient 和 WebChromeClient
//        webView.webViewClient = WebViewClient() // 阻止跳转到外部浏览器
//        webView.webChromeClient = WebChromeClient()
//
//        //////
//        webView.addJavascriptInterface(object {
//            @JavascriptInterface
//            fun showToast(message: String) {
//                Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
//            }
//        }, "Android")
//        /////
//        // 加载本地 HTML 文件
//        val filePath = "file:///android_asset/html/index.html"
//        webView.loadUrl(filePath)
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        // 释放资源，避免内存泄漏
//        webView.apply {
//            clearCache(true)
//            clearHistory()
//            removeAllViews()
//            destroy()
//        }
//    }
//}
class MainActivity : AppCompatActivity() {

    companion object {
        private const val REQUEST_CODE_STORAGE_PERMISSION = 1
    }

    private val REQUEST_CODE_FILE_PICKER = 1

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        webView = findViewById(R.id.webView)
        // 检查并请求权限
//        checkAndRequestPermissions()

        webView.settings.allowFileAccess = true // 允许访问文件
        webView.settings.allowContentAccess = true // 允许访问内容
        webView.settings.allowFileAccessFromFileURLs = true // 允许从文件 URL 访问
        webView.settings.allowUniversalAccessFromFileURLs = true // 允许从文件 URL 进行跨域访问

        // 配置 WebView 设置
        webView.settings.javaScriptEnabled = true // 启用 JavaScript

        // 设置 WebViewClient 和 WebChromeClient
        webView.webViewClient = WebViewClient() // 阻止跳转到外部浏览器
        // 设置 WebChromeClient
        webView.webChromeClient = object : WebChromeClient() {
            override fun onShowFileChooser(
                webView: WebView?,
                filePathCallback: ValueCallback<Array<Uri>>?,
                fileChooserParams: FileChooserParams?
            ): Boolean {
                // 在这里处理文件选择逻辑
                return super.onShowFileChooser(webView, filePathCallback, fileChooserParams)
            }
        }

        // 其他 WebView 配置
        webView.settings.domStorageEnabled = true // 如果需要使用 localStorage
        webView.settings.allowFileAccess = true // 允许访问文件
        webView.settings.allowContentAccess = true // 允许访问内容

        // 添加 JavaScript 接口
        webView.addJavascriptInterface(object {
            @JavascriptInterface
            fun showToast(message: String) {
                Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
            }
            @JavascriptInterface
            fun saveFile(content: String,name: String) {
                saveFileToSharedStorage(content,name)
            }
            @JavascriptInterface
            fun openFilePicker() {
                // 打开文件选择器
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "*/*" // 允许选择所有文件类型
                startActivityForResult(intent, REQUEST_CODE_FILE_PICKER)
            }

            @JavascriptInterface
            fun addQuestions(shareId: String, questionText: String) {
                Thread {
                    val data = addShareExam(shareId, questionText)
                    println(questionText)
                    println(data)
                    runOnUiThread {
                        webView.loadUrl("javascript:receiveAddShareExamResponse('${JavaScriptUtils.escape(data)}')")
                    }
                }.start()
            }

            @JavascriptInterface
            fun getSharedQuestions(id:String) {
                Thread {
                    val data = getShareExam(id)
                    println(data)
                    runOnUiThread {
                        webView.loadUrl("javascript:receiveOnlineQuestion('${JavaScriptUtils.escape(data)}')")
                    }
                }.start()
            }

        }, "Android")

        // 设置下载监听器
        webView.setDownloadListener { url, userAgent, contentDisposition, mimetype, contentLength ->
            // 使用 DownloadManager 处理下载
            val request = android.app.DownloadManager.Request(Uri.parse(url))
            request.setMimeType(mimetype)
            request.addRequestHeader("User-Agent", userAgent)
            request.setDescription("下载文件")
            request.setTitle("questions.txt") // 设置下载文件的名称
            request.allowScanningByMediaScanner()
            request.setNotificationVisibility(android.app.DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "questions.txt")

            val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            downloadManager.enqueue(request)

            Toast.makeText(this@MainActivity, "开始下载文件", Toast.LENGTH_SHORT).show()
        }

        // 加载本地 HTML 文件
        val filePath = "file:///android_asset/html/index.html"
        webView.loadUrl(filePath)

        // 检查并请求存储权限（Android 6.0 及以上需要）
//        checkAndRequestPermissions()
    }

    override fun onDestroy() {
        super.onDestroy()
        // 释放资源，避免内存泄漏
        webView.apply {
            clearCache(true)
            clearHistory()
            removeAllViews()
            destroy()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_FILE_PICKER && resultCode == RESULT_OK) {
            val uri = data?.data
            if (uri != null) {
                // 读取文件内容
                val fileContent = readFileContent(uri)
                println(fileContent)
                val escapedContent = JSONObject.quote(fileContent)
                // 检查文件内容是否以 "答案：" 开头
                if (fileContent.startsWith("答案：")) {
                    // 如果以 "答案：" 开头，只调用 parseAnswersFile
                    webView.evaluateJavascript("parseAnswersFile($escapedContent)", null)
                } else {
                    // 否则，调用 parseQuestionsFile
                    webView.evaluateJavascript("parseQuestionsFile($escapedContent)", null)
                }
            }
        }
    }

    private fun readFileContent(uri: Uri): String {
        return try {
            val inputStream = contentResolver.openInputStream(uri)
            val reader = BufferedReader(InputStreamReader(inputStream))
            val stringBuilder = StringBuilder()
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                stringBuilder.append(line).append("\n")
            }
            reader.close()
            stringBuilder.toString()
        } catch (e: Exception) {
            e.printStackTrace()
            "读取文件失败：${e.message}"
        }
    }


    private fun saveFileToExternalStorage(content: String) {
        try {
            // 获取应用的私有目录
            val file = File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "questions.txt")
            file.writeText(content) // 将内容写入文件
            Toast.makeText(this, "文件已保存到：${file.absolutePath}", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "保存文件失败：${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
    private fun saveFileToSharedStorage(content: String,name:String) {
        val values = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "$name.txt")
            put(MediaStore.MediaColumns.MIME_TYPE, "text/plain")
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
        }

        val resolver = contentResolver
        val uri = resolver.insert(MediaStore.Files.getContentUri("external"), values)
        uri?.let {
            resolver.openOutputStream(it)?.use { outputStream ->
                outputStream.write(content.toByteArray())
            }
            Toast.makeText(this, "文件已保存到共享存储", Toast.LENGTH_SHORT).show()
        }
    }

    fun addShareExam(shareId: String, question: String): String {
        val response = StringBuilder()
        try {
            val url = URL("http://${server.url}/addShareExam")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.doOutput = true
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")

            val encodedShareId = URLEncoder.encode(shareId, "UTF-8")
            val encodedQuestion = URLEncoder.encode(question, "UTF-8")
            val data = "shareId=$encodedShareId&questionText=$encodedQuestion"

            val outputStream = connection.getOutputStream()
            outputStream.write(data.toByteArray())
            outputStream.flush()
            outputStream.close()

            val inputStream = connection.inputStream
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))
            var line: String?
            while (bufferedReader.readLine().also { line = it } != null) {
                response.append(line)
            }
            bufferedReader.close()
            connection.disconnect()
        } catch (e: Exception) {
            e.printStackTrace()
            response.append("Error: ${e.message}")
        }
        return response.toString()
    }

    fun getShareExam(id:String): String {
        val response = StringBuilder()
        try {
            val url = "http://${server.url}/getShareExam?id="+id
            val connection = URL(url).openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connect()

            val inputStream = connection.inputStream
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))
            var line: String?
            while (bufferedReader.readLine().also { line = it } != null) {
                response.append(line)
            }
            bufferedReader.close()
            connection.disconnect()
        } catch (e: Exception) {
            e.printStackTrace()
            response.append("Error: ${e.message}")
        }
        return response.toString()
    }
}

object JavaScriptUtils {
    fun escape(str: String): String {
        return str.replace("'", "\\'")
    }
}