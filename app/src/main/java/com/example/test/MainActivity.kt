package com.example.test

import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.test.R


class MainActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        webView = findViewById(R.id.webView)

        // 配置 WebView 设置
        webView.settings.javaScriptEnabled = true // 启用 JavaScript

        // 设置 WebViewClient 和 WebChromeClient
        webView.webViewClient = WebViewClient() // 阻止跳转到外部浏览器
        webView.webChromeClient = WebChromeClient()

        //////
        webView.addJavascriptInterface(object {
            @JavascriptInterface
            fun showToast(message: String) {
                Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
            }
        }, "Android")
        /////
        // 加载本地 HTML 文件
        val filePath = "file:///android_asset/html/index.html"
        webView.loadUrl(filePath)
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
}