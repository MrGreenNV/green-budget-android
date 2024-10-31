package ru.averkiev.budget.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ru.averkiev.budget.R

class OnboardActivity : AppCompatActivity() {

    companion object {
        const val TAG_NAME = "OnboardActivity"
    }

    private val images = arrayOf(
        R.drawable.onboard_image_1,
        R.drawable.onboard_image_2,
        R.drawable.onboard_image_3
    )
    private val titles = arrayOf(
        "Welcome!",
        "Welcome!",
        "Welcome!"
    )
    private val subtitles = arrayOf(
        "не это..",
        "и не это..",
        "и даже не это.."
    )

    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG_NAME, "started 'onCreate'")
        setContentView(R.layout.activity_onboard)

        val onboardImage: ImageView = findViewById(R.id.onboardImage)
        val onboardTitle: TextView = findViewById(R.id.onboardTitle)
        val onboardSubtitle: TextView = findViewById(R.id.onboardSubtitle)
        val btnPrev: Button = findViewById(R.id.btnPrev)
        val btnNext: Button = findViewById(R.id.btnNext)

        updateContent(onboardImage, onboardTitle, onboardSubtitle)

        btnPrev.setOnClickListener {
            if (currentIndex > 0) {
                currentIndex--
                updateContent(onboardImage, onboardTitle, onboardSubtitle)
            }
        }

        btnNext.setOnClickListener {
            if (currentIndex < titles.size - 1) {
                currentIndex++
                updateContent(onboardImage, onboardTitle, onboardSubtitle)
            } else {
                val intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun onStart() {
        Log.d(TAG_NAME, "started 'onStart'")
        super.onStart()
    }

    override fun onResume() {
        Log.d(TAG_NAME, "started 'onResume'")
        super.onResume()
    }

    override fun onPause() {
        Log.d(TAG_NAME, "started 'onPause'")
        super.onPause()
    }

    override fun onStop() {
        Log.d(TAG_NAME, "started 'onStop'")
        super.onStop()
    }

    override fun onDestroy() {
        Log.d(TAG_NAME, "started 'onDestroy'")
        super.onDestroy()
    }

    private fun updateContent(imageView: ImageView, titleView: TextView, subtitleView: TextView) {
        imageView.setImageResource(images[currentIndex])
        titleView.text = titles[currentIndex]
        subtitleView.text = subtitles[currentIndex]
    }
}