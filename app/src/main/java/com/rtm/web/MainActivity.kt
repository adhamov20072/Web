package com.rtm.web

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.rtm.web.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var url: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.webView.webViewClient = WebView(this)
        binding.btnSearch.setOnClickListener {
            url = binding.edText.text.toString()
            binding.webView.loadUrl(url)
        }
        binding.swipetoRefresh.setOnRefreshListener { binding.webView.loadUrl(url) }
    }


    class WebView constructor(val activity: Activity) : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: android.webkit.WebView,
            url: String
        ): Boolean {
            view.loadUrl(url)

            return true
        }


        override fun shouldOverrideUrlLoading(
            view: android.webkit.WebView,
            request: WebResourceRequest
        ): Boolean {
            view.loadUrl(request.url.toString())
            return true
        }

        override fun onReceivedError(
            view: android.webkit.WebView?,
            request: WebResourceRequest?,
            error: WebResourceError?
        ) {
            Toast.makeText(activity, error.toString(), Toast.LENGTH_SHORT).show()
        }

        override fun onPageCommitVisible(view: android.webkit.WebView?, url: String?) {
            super.onPageCommitVisible(view, url)
          activity.findViewById<SwipeRefreshLayout>(R.id.swipetoRefresh).isRefreshing=false
        }
    }
}