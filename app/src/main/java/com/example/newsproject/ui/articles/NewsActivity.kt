package com.example.newsproject.ui.articles

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.newsproject.R

class NewsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            setContentView(R.layout.activity_news)
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
        Log.d("NewsActivity", "onDestroy")
    }
}
