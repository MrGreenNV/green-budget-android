package ru.averkiev.budget

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class OnboardActivity : AppCompatActivity() {

    private val images = arrayOf(
        R.drawable.onboard_image1,
        R.drawable.onboard_image2,
        R.drawable.onboard_image3,
    )
    private val titles = arrayOf(
        "Welcome!",
        "Fun_1",
        "Fun_2"
    )
    private val subtitles = arrayOf(
        "desc app",
        "desc fun_1",
        "desc fun_2"
    )

    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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

    private fun updateContent(imageView: ImageView, titleView: TextView, subtitleView: TextView) {
        imageView.setImageResource(images[currentIndex])
        titleView.text = titles[currentIndex]
        subtitleView.text = subtitles[currentIndex]
    }
}