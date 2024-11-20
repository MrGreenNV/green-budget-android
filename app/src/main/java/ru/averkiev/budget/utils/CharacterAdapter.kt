package ru.averkiev.budget.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.averkiev.budget.databinding.ItemCharacterBinding
import ru.averkiev.budget.models.CharacterResponse

class CharacterAdapter(private val characterResponses: List<CharacterResponse>) :
    RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

        inner class CharacterViewHolder(private val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(characterResponse: CharacterResponse) {
                "name: ${if (characterResponse.name.isNullOrEmpty()) "Unknown" else characterResponse.name}".also { binding.tvName.text = it }
                "culture: ${if (characterResponse.culture.isNullOrEmpty()) "Unknown" else characterResponse.culture}".also { binding.tvCulture.text = it }
                "born: ${if (characterResponse.born.isNullOrEmpty()) "Unknown" else characterResponse.born}".also { binding.tvBorn.text = it }
                "titles: ${if (characterResponse.titles.isNullOrEmpty()) "None" else characterResponse.titles.joinToString(", ")}".also { binding.tvTitles.text = it }
                "aliases: ${if (characterResponse.aliases.isNullOrEmpty()) "None" else characterResponse.aliases.joinToString(", ")}".also { binding.tvAliases.text = it }
                "playedBy: ${if (characterResponse.playedBy.isNullOrEmpty()) "None" else characterResponse.playedBy.joinToString(", ")}".also { binding.tvPlayedBy.text = it }
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
        holder.bind(characterResponses[position])
    }

    override fun getItemCount(): Int {
        return characterResponses.size
    }
}