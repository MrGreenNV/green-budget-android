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

        if (savedInstanceState == null)
            replaceFragment(OnboardFragment())
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    fun navigateToSignInFragment() {
        replaceFragment(SignInFragment())
    }

    fun navigateToHomeFragment() {
        replaceFragment(HomeFragment())
    }

    fun navigateToSignUpFragment() {
        replaceFragment(SignUpFragment())
    }
}