package ru.averkiev.budget.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.averkiev.budget.R
import ru.averkiev.budget.activities.MainActivity
import ru.averkiev.budget.databinding.FragmentSignUpBinding
import ru.averkiev.budget.models.User
import ru.averkiev.budget.utils.DBHelper

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loginEditText: EditText = view.findViewById(R.id.user_login)
        val emailEditText: EditText = view.findViewById(R.id.user_email)
        val passEditText: EditText = view.findViewById(R.id.user_pass)

        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
        }

        binding.buttonReg.setOnClickListener {
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

                Toast.makeText(
                    requireContext(),
                    "Зарегистрирован новый пользователь: $login",
                    Toast.LENGTH_SHORT
                ).show()

                val action = SignUpFragmentDirections.actionSignUpFragmentToSignInFragment(user)
                findNavController().navigate(action)

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