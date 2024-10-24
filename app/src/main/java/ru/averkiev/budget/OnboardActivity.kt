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
//        val id: Int
//        if (currentIndex == 0)
//            id = this.resources.getIdentifier("onboard_image_1", "drawable", packageName)
//        else if (currentIndex == 1)
//            id = this.resources.getIdentifier("onboard_image_2", "drawable", packageName)
//        else
//            id = this.resources.getIdentifier("onboard_image_3", "drawable", this.packageName)

        imageView.setImageResource(images[currentIndex])
        titleView.text = titles[currentIndex]
        subtitleView.text = subtitles[currentIndex]
    }
}