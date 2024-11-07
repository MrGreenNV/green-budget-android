package ru.averkiev.budget.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ru.averkiev.budget.R
import ru.averkiev.budget.fragments.HomeFragment
import ru.averkiev.budget.fragments.OnboardFragment
import ru.averkiev.budget.fragments.SignInFragment
import ru.averkiev.budget.fragments.SignUpFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            navigateToOnboardFragment()
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    fun navigateToOnboardFragment() {
        val onboardFragment = OnboardFragment()

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, onboardFragment)
            .commit()
    }

    fun navigateToSignInFragment(bundle: Bundle? = null) {
        val signInFragment = SignInFragment()

        if (bundle != null) {
            signInFragment.arguments = bundle
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, signInFragment)
            .addToBackStack(null)
            .commit()
    }

    fun navigateToHomeFragment(bundle: Bundle? = null) {
        val homeFragment = HomeFragment()

        if (bundle != null)
            homeFragment.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, homeFragment)
            .commit()
    }

    fun navigateToSignUpFragment() {
        val signUpFragment = SignUpFragment()

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, signUpFragment)
            .addToBackStack(null)
            .commit()
    }
}