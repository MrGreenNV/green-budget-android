package ru.averkiev.budget.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import ru.averkiev.budget.databinding.FragmentCharacterBinding
import ru.averkiev.budget.repositories.KtorNetwork
import ru.averkiev.budget.utils.CharacterAdapter

class CharacterFragment : Fragment() {

    private var _binding: FragmentCharacterBinding? = null
    private val binding get() = _binding ?: throw Exception()

    private val network = KtorNetwork()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCharacterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewCharacters.layoutManager = LinearLayoutManager(requireContext())

        lifecycleScope.launchWhenCreated {
            val characters = network.getCharacters(50, 1)
            if (characters.isNotEmpty())
                binding.recyclerViewCharacters.adapter = CharacterAdapter(characters)
            else
                Log.e("CharacterFragment", "No characters received")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}