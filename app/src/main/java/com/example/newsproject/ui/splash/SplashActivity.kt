package com.example.newsproject.ui.splash

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.example.newsproject.R
import com.example.newsproject.ui.articles.NewsActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    companion object {
        private const val MIN_OFFSET = 0f
        private const val MAX_OFFSET = 1f
        private const val MILLIS = 2000L
        private const val REPEAT_COUNT = 0
    }

    private lateinit var alphaAnimator: ObjectAnimator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        makeFullScreen()
        setContentView(R.layout.activity_splash)

        alphaAnimator = ObjectAnimator.ofFloat(application_logo, View.ALPHA, MIN_OFFSET, MAX_OFFSET)

        configure(alphaAnimator).start()

        Handler().postDelayed({
            startActivity(Intent(this, NewsActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }, MILLIS)

    }

    override fun onStop() {
        super.onStop()
        alphaAnimator.cancel()
    }

    /**
     * Конфигурируется анимация
     */
    private fun configure(animator: ObjectAnimator): Animator {
        animator.duration = MILLIS
        animator.repeatCount = REPEAT_COUNT
        return animator

    }

    /**
     * В методе сначала убирается заголовок, потом делается полноэкранный режим
     */
    private fun makeFullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }
}
