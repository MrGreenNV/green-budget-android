package ru.averkiev.budget.utils

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.averkiev.budget.databinding.ItemCharacterBinding
import ru.averkiev.budget.models.Character

class CharacterAdapter(private val characters: List<Character>) :
    RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

        inner class CharacterViewHolder(private val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(character: Character) {
                "name: ${if (character.name.isNullOrEmpty()) "Unknown" else character.name}".also { binding.tvName.text = it }
                "culture: ${if (character.culture.isNullOrEmpty()) "Unknown" else character.culture}".also { binding.tvCulture.text = it }
                "born: ${if (character.born.isNullOrEmpty()) "Unknown" else character.born}".also { binding.tvBorn.text = it }
                "titles: ${if (character.titles.isNullOrEmpty()) "None" else character.titles.joinToString(", ")}".also { binding.tvTitles.text = it }
                "aliases: ${if (character.aliases.isNullOrEmpty()) "None" else character.aliases.joinToString(", ")}".also { binding.tvAliases.text = it }
                "playedBy: ${if (character.playedBy.isNullOrEmpty()) "None" else character.playedBy.joinToString(", ")}".also { binding.tvPlayedBy.text = it }
            }
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
}