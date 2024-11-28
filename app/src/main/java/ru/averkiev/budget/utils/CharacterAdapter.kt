package ru.averkiev.budget.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.averkiev.budget.databinding.ItemCharacterBinding
import ru.averkiev.budget.models.CharacterEntity

class CharacterAdapter(private var characters: List<CharacterEntity>) :
    RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    inner class CharacterViewHolder(private val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(characterResponse: CharacterEntity) {
            "name: ${if (characterResponse.name.isNullOrEmpty()) "Unknown" else characterResponse.name}".also {
                binding.tvName.text = it
            }
            "culture: ${if (characterResponse.culture.isNullOrEmpty()) "Unknown" else characterResponse.culture}".also {
                binding.tvCulture.text = it
            }
            "born: ${if (characterResponse.born.isNullOrEmpty()) "Unknown" else characterResponse.born}".also {
                binding.tvBorn.text = it
            }
            "titles: ${
                if (characterResponse.titles.isNullOrEmpty()) "None" else characterResponse.titles.joinToString(
                    ", "
                )
            }".also { binding.tvTitles.text = it }
            "aliases: ${
                if (characterResponse.aliases.isNullOrEmpty()) "None" else characterResponse.aliases.joinToString(
                    ", "
                )
            }".also { binding.tvAliases.text = it }
            "playedBy: ${
                if (characterResponse.playedBy.isNullOrEmpty()) "None" else characterResponse.playedBy.joinToString(
                    ", "
                )
            }".also { binding.tvPlayedBy.text = it }
        }
    }

    fun addNewCharacters(newCharacters: List<CharacterEntity>) {
        val currentList = characters.toMutableList()  // Получаем текущий список
        currentList.addAll(newCharacters)  // Добавляем новые элементы в конец
        characters = currentList  // Обновляем адаптер
        notifyItemRangeInserted(characters.size - newCharacters.size, newCharacters.size)  // Добавляем новые элементы
    }

    fun updateCharacters(newCharacters: List<CharacterEntity>) {
        characters = newCharacters
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = ItemCharacterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(characters[position])
    }

    override fun getItemCount(): Int {
        return characters.size
    }

    // DiffUtil.Callback для оптимизации обновления списка
    class CharacterDiffCallback : DiffUtil.ItemCallback<CharacterEntity>() {

        // Сравниваем элементы, чтобы понять, что изменилось
        override fun areItemsTheSame(oldItem: CharacterEntity, newItem: CharacterEntity): Boolean {
            return oldItem.name == newItem.name  // Пример: сравниваем по имени
        }

        // Сравниваем содержимое элементов
        override fun areContentsTheSame(oldItem: CharacterEntity, newItem: CharacterEntity): Boolean {
            return oldItem == newItem
        }
    }
}