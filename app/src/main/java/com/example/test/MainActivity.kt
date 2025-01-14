package com.example.test

import android.Manifest
import android.app.AlertDialog
import android.app.DownloadManager
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.test.R
import java.io.File


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

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        webView = findViewById(R.id.webView)
        // 检查并请求权限
//        checkAndRequestPermissions()

        // 配置 WebView 设置
        webView.settings.javaScriptEnabled = true // 启用 JavaScript
        webView.settings.domStorageEnabled = true // 启用 DOM 存储（如果需要）

        // 设置 WebViewClient 和 WebChromeClient
        webView.webViewClient = WebViewClient() // 阻止跳转到外部浏览器
        webView.webChromeClient = WebChromeClient()

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
            fun saveFile(content: String) {
                saveFileToExternalStorage(content)
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


}