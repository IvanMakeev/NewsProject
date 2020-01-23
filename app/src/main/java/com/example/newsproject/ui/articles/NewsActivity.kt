package com.example.newsproject.ui.articles

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import com.example.newsproject.AppDelegate
import com.example.newsproject.R
import com.example.newsproject.ui.articles.contract.WebViewContract
import com.example.newsproject.ui.articles.web.WebViewFragment
import kotlinx.android.synthetic.main.activity_news.*

class NewsActivity : AppCompatActivity(), WebViewContract {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.root_view, ArticlesFragment.newInstance())
                .commit()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (supportFragmentManager.backStackEntryCount == 0) {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing) {
            AppDelegate.getInjector().clearFragmentComponent()
        }
    }

    override fun openWebViewFragment(url: String) {
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.root_view, WebViewFragment.newInstance(url))
            .commit()
    }
}
