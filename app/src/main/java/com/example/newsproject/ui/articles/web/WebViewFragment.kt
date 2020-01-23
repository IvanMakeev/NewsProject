package com.example.newsproject.ui.articles.web

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.newsproject.R
import kotlinx.android.synthetic.main.fragment_web_view.*

class WebViewFragment : Fragment() {

    companion object {
        private const val URL = "url"

        fun newInstance(url: String): WebViewFragment {
            val fragment = WebViewFragment()
            val args = Bundle()
            args.putString(URL, url)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var webViewClient: WebViewClient

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_web_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val url = arguments?.getString(URL)
        if (savedInstanceState != null) {
            web_view.restoreState(savedInstanceState)
        } else {
            webViewClient = initWebViewClient()
            web_view.webViewClient = webViewClient
            web_view.loadUrl(url)
            web_view.settings.apply {
                cacheMode = WebSettings.LOAD_NO_CACHE
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        web_view.saveState(outState)
    }

    private fun initWebViewClient(): WebViewClient {
        return object : WebViewClient() {
            @SuppressWarnings("deprecation")
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }

            @TargetApi(Build.VERSION_CODES.N)
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                view?.loadUrl(request?.url.toString())
                return true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                loading_page_progress?.let {
                    it.visibility = View.GONE
                }
            }
        }
    }
}