package ru.averkiev.budget.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.averkiev.budget.R
import ru.averkiev.budget.databinding.FragmentOnboardBinding

class OnboardFragment : Fragment() {

    private var _binding: FragmentOnboardBinding? = null
    private val binding get() = _binding ?: throw Exception()

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
    ): View {
        _binding = FragmentOnboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateContent(binding.onboardImage, binding.onboardTitle, binding.onboardSubtitle)

        binding.btnPrev.setOnClickListener {
            if (currentIndex > 0) {
                currentIndex--
                updateContent(binding.onboardImage, binding.onboardTitle, binding.onboardSubtitle)
            }
        }

        binding.btnNext.setOnClickListener {
            if (currentIndex < titles.size - 1) {
                currentIndex++
                updateContent(binding.onboardImage, binding.onboardTitle, binding.onboardSubtitle)
            } else {
                findNavController().navigate(R.id.action_onboardFragment_to_signInFragment)
            }
        }

        binding.btnSettings.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_settingsFragment)
        }
    }

    private fun updateContent(imageView: ImageView, titleView: TextView, subtitleView: TextView) {
        imageView.setImageResource(images[currentIndex])
        titleView.text = titles[currentIndex]
        subtitleView.text = subtitles[currentIndex]
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}