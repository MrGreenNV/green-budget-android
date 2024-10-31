package ru.averkiev.budget.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import ru.averkiev.budget.R
import ru.averkiev.budget.activities.MainActivity
import ru.averkiev.budget.utils.DBHelper

class SignInFragment : Fragment() {

    private lateinit var login: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etEmail: EditText = view.findViewById(R.id.etEmail)
        val etPassword: EditText = view.findViewById(R.id.etPassword)
        val tvError: TextView = view.findViewById(R.id.tvError)
        val btnSignIn: Button = view.findViewById(R.id.btnSignIn)
        val tvSignUp: TextView = view.findViewById(R.id.tvSignUp)

        btnSignIn.setOnClickListener {
            val emailInput = etEmail.text.toString().trim()
            val passwordInput = etPassword.text.toString().trim()

            if (emailInput.isEmpty() || passwordInput.isEmpty())
                Toast.makeText(
                    requireContext(),
                    "Поля не должны быть пустыми!",
                    Toast.LENGTH_SHORT
                ).show()

            val db = DBHelper(requireContext(), null)
            val isAuth = db.existUser(emailInput, passwordInput)

            if (isValidEmail(emailInput) && isValidPassword(passwordInput)) {
                if (isAuth) {
                    Toast.makeText(
                        requireContext(),
                        "Пользователь авторизован",
                        Toast.LENGTH_SHORT
                    ).show()

                    login = emailInput.substring(0, emailInput.indexOf("@"))

                    (activity as? MainActivity)?.navigateToHomeFragment()
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
            (activity as? MainActivity)?.navigateToSignUpFragment()
        }

    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }

}