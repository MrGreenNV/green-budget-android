package ru.averkiev.budget.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ru.averkiev.budget.databinding.FragmentCharacterBinding
import ru.averkiev.budget.models.CharacterEntity
import ru.averkiev.budget.repositories.CharacterRepository
import ru.averkiev.budget.repositories.KtorNetwork
import ru.averkiev.budget.utils.AppDatabase
import ru.averkiev.budget.utils.CharacterAdapter

class CharacterFragment : Fragment() {

    private var _binding: FragmentCharacterBinding? = null
    private val binding get() = _binding ?: throw Exception()
    private lateinit var repository: CharacterRepository
    private val network = KtorNetwork()
    private var currentPage = 1
    private var isLoading = false

    private lateinit var characterAdapter: CharacterAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        characterAdapter = CharacterAdapter(listOf())
        binding.recyclerViewCharacters.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewCharacters.adapter = characterAdapter

        val database = AppDatabase.getInstance(requireContext())
        repository = CharacterRepository(database.characterDao())

        loadCharactersFromDatabase()

        binding.btnUpdateCharacters.setOnClickListener {
            updateCharactersFromApi()
        }

        binding.recyclerViewCharacters.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                if (!isLoading && totalItemCount <= lastVisibleItem + 5) {
                    isLoading = true
                    currentPage++
                    loadNextPage()
                }
            }
        })

//        binding.btnNextPage.setOnClickListener {
//            currentPage++
//            fetchCharactersPage(currentPage)
//        }
//
//        binding.btnPreviousPage.setOnClickListener {
//            if (currentPage > 1) {
//                currentPage--
//                fetchCharactersPage(currentPage)
//            } else {
//                Log.d("CharacterFragment", "Already on the first page")
//            }
//        }

    }

    private fun loadNextPage() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val apiCharacters = network.getCharacters(10, currentPage)

                if (apiCharacters.isNotEmpty()) {
                    val entities = apiCharacters.map { response ->
                        CharacterEntity(
                            name = response.name,
                            gender = response.gender,
                            culture = response.culture,
                            born = response.born,
                            titles = response.titles,
                            aliases = response.aliases,
                            playedBy = response.playedBy
                        )
                    }

                    val layoutManager = binding.recyclerViewCharacters.layoutManager as LinearLayoutManager//
                    val currentPosition = layoutManager.findFirstVisibleItemPosition()//

                    repository.saveCharacters(entities)

                    val characters = repository.getAllCharacters().first()
                    characterAdapter.addNewCharacters(characters)

                    layoutManager.scrollToPosition(currentPosition)//


                    activity?.runOnUiThread {
                        Toast.makeText(requireContext(), "Данные успешно подгружены", Toast.LENGTH_SHORT).show()
                    }

                } else {
                    Log.d("CharacterFragment", "No more characters to load.")
                }

                isLoading = false

            } catch (e: Exception) {
                Log.e("CharacterFragment", "Error fetching characters for page $currentPage: ${e.message}")
                isLoading = false
            }
        }
    }

    private fun loadCharactersFromDatabase() {
        lifecycleScope.launch {
            repository.getAllCharacters().collect { characters ->
                if (characters.isNotEmpty()) {
                    binding.recyclerViewCharacters.adapter = CharacterAdapter(characters)
                } else {
                    fetchAndSaveCharacters()
                }
            }
        }
    }

    private fun updateCharactersFromApi() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val apiCharacters = network.getCharacters(10, 1)

                val entities = apiCharacters.map { response ->
                    CharacterEntity(
                        name = response.name,
                        gender = response.gender,
                        culture = response.culture,
                        born = response.born,
                        titles = response.titles,
                        aliases = response.aliases,
                        playedBy = response.playedBy
                    )
                }

                Log.d("CharacterFragment", "ENTITIES = $entities")

                repository.clearCharacters()
                repository.saveCharacters(entities)

                val layoutManager = binding.recyclerViewCharacters.layoutManager as LinearLayoutManager
                val currentPosition = layoutManager.findFirstVisibleItemPosition()

                val characters = repository.getAllCharacters().first()
                activity?.runOnUiThread {
                    characterAdapter.updateCharacters(characters)

                    layoutManager.scrollToPosition(currentPosition)

                    Toast.makeText(requireContext(), "Данные успешно обновлены", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("CharacterFragment", "Error updating characters: ${e.message}")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun fetchAndSaveCharacters() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val apiCharacters = network.getCharacters(10, 1)

                val entities = apiCharacters.map { response ->
                    CharacterEntity(
                        name = response.name,
                        gender = response.gender,
                        culture = response.culture,
                        born = response.born,
                        titles = response.titles,
                        aliases = response.aliases,
                        playedBy = response.playedBy
                    )
                }

                repository.saveCharacters(entities)

                Log.d("CharacterFragment", "Characters saved to database")
            } catch (e: Exception) {
                Log.e("CharacterFragment", "Error fetching characters: ${e.message}")
            }
        }
    }

//    private fun fetchCharactersPage(pageNumber: Int) {
//        lifecycleScope.launch(Dispatchers.IO) {
//            try {
//                val apiCharacters = network.getCharacters(10, pageNumber)
//
//                if (apiCharacters.isEmpty()) {
//                    // Если нет данных, показываем сообщение или скрываем кнопку для следующей страницы
//                    withContext(Dispatchers.Main) {
//                        Log.d("CharacterFragment", "No more pages available")
//                        binding.btnNextPage.isEnabled = false // Отключаем кнопку "Next Page"
//                    }
//                } else {
//                    val entities = apiCharacters.map { response ->
//                        CharacterEntity(
//                            name = response.name,
//                            gender = response.gender,
//                            culture = response.culture,
//                            born = response.born,
//                            titles = response.titles,
//                            aliases = response.aliases,
//                            playedBy = response.playedBy
//                        )
//                    }
//
//                    repository.saveCharacters(entities)
//                    Log.d("CharacterFragment", "Characters saved to database for page $pageNumber")
//
//                    loadCharactersFromDatabase()
//
//                    withContext(Dispatchers.Main) {
//                        binding.btnNextPage.isEnabled = true
//                    }
//                }
//
//            } catch (e: Exception) {
//                Log.e("CharacterFragment", "Error fetching characters for page $pageNumber: ${e.message}")
//            }
//        }
//    }

}