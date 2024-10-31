package ru.averkiev.budget.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import ru.averkiev.budget.R
import ru.averkiev.budget.activities.MainActivity

class OnboardFragment : Fragment() {

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_onboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val onboardImage: ImageView = view.findViewById(R.id.onboardImage)
        val onboardTitle: TextView = view.findViewById(R.id.onboardTitle)
        val onboardSubtitle: TextView = view.findViewById(R.id.onboardSubtitle)
        val btnPrev: Button = view.findViewById(R.id.btnPrev)
        val btnNext: Button = view.findViewById(R.id.btnNext)

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
                (activity as? MainActivity)?.navigateToSignInFragment()
            }
        }
    }

    private fun updateContent(imageView: ImageView, titleView: TextView, subtitleView: TextView) {
        imageView.setImageResource(images[currentIndex])
        titleView.text = titles[currentIndex]
        subtitleView.text = subtitles[currentIndex]
    }
}