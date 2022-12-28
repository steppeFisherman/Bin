package com.example.bin.ui

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.bin.databinding.ActivityWebViewBinding
import com.example.bin.utils.visible

class WebViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebViewBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val uri = intent.extras?.getString("url").toString()
        if (uri.isNotBlank()) {
            binding.apply {
                webView.webViewClient = object : WebViewClient() {

                    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                        binding.progressBarWeb.visible(true)
                    }

                    override fun onPageFinished(view: WebView, url: String) {
                        binding.progressBarWeb.visible(false)
                    }
                }

                webView.settings.loadsImagesAutomatically = true
                webView.settings.javaScriptEnabled = true
                webView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
                webView.loadUrl(uri)
            }
        }
    }
}