package ru.averkiev.budget.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ru.averkiev.budget.R
import ru.averkiev.budget.models.User
import ru.averkiev.budget.utils.DBHelper

class SignInActivity : AppCompatActivity() {

    private lateinit var login: String

    companion object {
        const val TAG_NAME = "SignInActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG_NAME, "started 'onCreate'")
        setContentView(R.layout.activity_sign_in)

        val etEmail: EditText = findViewById(R.id.etEmail)
        val etPassword: EditText = findViewById(R.id.etPassword)
        val tvError: TextView = findViewById(R.id.tvError)
        val btnSignIn: Button = findViewById(R.id.btnSignIn)
        val tvSignUp: TextView = findViewById(R.id.tvSignUp)
        val user: User? = intent.getSerializableExtra("user", User::class.java)

        if (user != null) {
            etEmail.setText(user.email)
            etPassword.setText(user.pass)
            login = user.login
        }

        btnSignIn.setOnClickListener {
            val emailInput = etEmail.text.toString().trim()
            val passwordInput = etPassword.text.toString().trim()

            if (emailInput.isEmpty() || passwordInput.isEmpty())
                Toast.makeText(
                    this,
                    "Поля не должны быть пустыми!",
                    Toast.LENGTH_SHORT
                ).show()

            val db = DBHelper(this, null)
            val isAuth = db.existUser(emailInput, passwordInput)

            if (isValidEmail(emailInput) && isValidPassword(passwordInput)) {
                if (isAuth) {
                    Toast.makeText(
                        this,
                        "Пользователь авторизован",
                        Toast.LENGTH_SHORT
                    ).show()

                    if (user == null) {
                        login = emailInput.substring(0, emailInput.indexOf("@"))
                    }

                    val intent = Intent(this, HomeActivity::class.java).apply {
                        putExtra("loginName", login)
                    }
                    startActivity(intent)
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