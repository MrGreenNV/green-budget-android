package ru.averkiev.budget.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ru.averkiev.budget.R
import ru.averkiev.budget.models.User
import ru.averkiev.budget.utils.DBHelper

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val btnBack: Button = findViewById(R.id.btnBack)
        val btnReg: Button = findViewById(R.id.button_reg)
        val loginEditText: EditText = findViewById(R.id.user_login)
        val emailEditText: EditText = findViewById(R.id.user_email)
        val passEditText: EditText = findViewById(R.id.user_pass)

        btnBack.setOnClickListener {
            intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnReg.setOnClickListener {
            val login = loginEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val pass = passEditText.text.toString().trim()

            if (login.isEmpty() || email.isEmpty() || pass.isEmpty())

                Toast.makeText(
                    this,
                    "Заполнены не все данные!",
                    Toast.LENGTH_SHORT
                ).show()

            else if (!isValidEmail(email) || !isValidPassword(pass))

                Toast.makeText(
                    this,
                    "Данные не корректны!",
                    Toast.LENGTH_SHORT
                ).show()

            else {

                val user = User(login, email, pass)
                val db = DBHelper(this, null)
                db.addUser(user)

                Toast.makeText(
                    this,
                    "Зарегистрирован новый пользователь: $login",
                    Toast.LENGTH_SHORT
                ).show()

                loginEditText.text.clear()
                emailEditText.text.clear()
                passEditText.text.clear()

                intent = Intent(this, SignInActivity::class.java).apply {
                    putExtra("login", login)
                    putExtra("email", email)
                    putExtra("pass", pass)

                    putExtra("user", user)
                }
                startActivity(intent)
                finish()

            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }
}