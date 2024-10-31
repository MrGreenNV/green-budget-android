package ru.averkiev.budget.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ru.averkiev.budget.R

class SignInActivity : AppCompatActivity() {

    companion object {
        const val TAG_NAME = "SignInActivity"
    }

    private val validEmail = "user@mail.ru"
    private val validPassword = "password"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG_NAME, "started 'onCreate'")
        setContentView(R.layout.activity_sign_in)

        val etEmail: EditText = findViewById(R.id.etEmail)
        val etPassword: EditText = findViewById(R.id.etPassword)
        val tvError: TextView = findViewById(R.id.tvError)
        val btnSignIn: Button = findViewById(R.id.btnSignIn)
        val tvSignUp: TextView = findViewById(R.id.tvSignUp)

        btnSignIn.setOnClickListener {
            val emailInput = etEmail.text.toString().trim()
            val passwordInput = etPassword.text.toString().trim()

            if (isValidEmail(emailInput) && isValidPassword(passwordInput)) {
                if (emailInput == validEmail && passwordInput == validPassword) {
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    tvError.text = "Ошибка: невалидные данные для входа"
                    tvError.visibility = View.VISIBLE
                }
            } else {
                tvError.text = "Ошибка: некорректные данные"
                tvError.visibility = View.VISIBLE
            }
        }

        tvSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
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

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }
}