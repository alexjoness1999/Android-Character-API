package com.kroger.classapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kroger.classapp.R
import com.kroger.classapp.databinding.CharacterCardViewBinding
import com.kroger.classapp.model.SuperHeroCharacter

class SuperHeroCharacterAdapter(
    private val onCharacterClicked: (character: SuperHeroCharacter, position: Int) -> Unit,
) : RecyclerView.Adapter<SuperHeroCharacterAdapter.SuperHeroCharacterViewHolder>() {

    inner class SuperHeroCharacterViewHolder(
        private val binding: CharacterCardViewBinding,
        private val onCharacterClicked: (position: Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                onCharacterClicked(adapterPosition)
            }
        }

        fun bind(character: SuperHeroCharacter) {
            binding.characterTitle.text = binding.root.context.getString(R.string.character_name, character.name)
            binding.characterDescription.text = binding.root.context.getString(R.string.character_gender, character.gender)
            Glide.with(binding.root).load(character.img).into(binding.characterImage)
        }
    }

    private val superHeroCharacters = mutableListOf<SuperHeroCharacter>()

    @SuppressLint("NotifyDataSetChanged")
    fun refreshData(characters: List<SuperHeroCharacter>){
        superHeroCharacters.clear()
        superHeroCharacters.addAll(characters)
        notifyDataSetChanged()
    }



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): SuperHeroCharacterViewHolder {
        val binding =
            CharacterCardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SuperHeroCharacterViewHolder(binding) { position ->
            onCharacterClicked(superHeroCharacters[position], position)
        }
    }

    override fun getItemCount() = superHeroCharacters.size

    override fun getItemId(position: Int) = position.toLong()

    override fun onBindViewHolder(holder: SuperHeroCharacterViewHolder, position: Int) {
        val character = superHeroCharacters[position]
        holder.bind(character)
    }
}