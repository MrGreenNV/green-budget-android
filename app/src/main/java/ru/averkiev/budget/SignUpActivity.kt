package ru.averkiev.budget

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)

        val btnBack: Button = findViewById(R.id.btnBack)

        btnBack.setOnClickListener {
            intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}