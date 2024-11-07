package ru.averkiev.budget.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.averkiev.budget.R
import ru.averkiev.budget.databinding.FragmentSignInBinding
import ru.averkiev.budget.utils.DBHelper

class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding ?: throw Exception()
    private val args: SignInFragmentArgs by navArgs()

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var login: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etEmail = view.findViewById(R.id.etEmail)
        etPassword = view.findViewById(R.id.etPassword)
        val tvError: TextView = view.findViewById(R.id.tvError)

        val user = args.user
        user?.let {
            binding.etEmail.setText(it.email)
            binding.etPassword.setText(it.pass)
        }

        if (user != null)
            login = user.login
        else
            login = "???"

        binding.btnSignIn.setOnClickListener {
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

                    if (login == "???")
                        login = emailInput.substring(0, emailInput.indexOf("@"))

                    val action = SignInFragmentDirections.actionSignInFragmentToHomeFragment(login)
                    findNavController().navigate(action)

                } else {
                    tvError.text = "Ошибка: невалидные данные для входа"
                    tvError.visibility = View.VISIBLE
                }
            } else {
                tvError.text = "Ошибка: некорректные данные"
                tvError.visibility = View.VISIBLE
            }
        }

        binding.tvSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }

    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }

}