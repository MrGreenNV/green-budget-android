package ru.averkiev.budget.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import ru.averkiev.budget.R
import ru.averkiev.budget.activities.MainActivity
import ru.averkiev.budget.models.User
import ru.averkiev.budget.utils.DBHelper

class SignUpFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnBack: Button = view.findViewById(R.id.btnBack)
        val btnReg: Button = view.findViewById(R.id.button_reg)
        val loginEditText: EditText = view.findViewById(R.id.user_login)
        val emailEditText: EditText = view.findViewById(R.id.user_email)
        val passEditText: EditText = view.findViewById(R.id.user_pass)

        btnBack.setOnClickListener {
            (activity as? MainActivity)?.navigateToSignInFragment()
        }

        btnReg.setOnClickListener {
            val login = loginEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val pass = passEditText.text.toString().trim()

            if (login.isEmpty() || email.isEmpty() || pass.isEmpty())

                Toast.makeText(
                    requireContext(),
                    "Заполнены не все данные!",
                    Toast.LENGTH_SHORT
                ).show()
            else if (!isValidEmail(email) || !isValidPassword(pass))

                Toast.makeText(
                    requireContext(),
                    "Данные не корректны!",
                    Toast.LENGTH_SHORT
                ).show()
            else {

                val user = User(login, email, pass)
                val db = DBHelper(requireContext(), null)
                db.addUser(user)

                val bundle = Bundle().apply {
                    putSerializable("user", user)
                }

                val signInFragment = SignInFragment()
                signInFragment.arguments = bundle

                Toast.makeText(
                    requireContext(),
                    "Зарегистрирован новый пользователь: $login",
                    Toast.LENGTH_SHORT
                ).show()

                (activity as? MainActivity)?.navigateToSignInFragment(bundle)

                loginEditText.text.clear()
                emailEditText.text.clear()
                passEditText.text.clear()
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