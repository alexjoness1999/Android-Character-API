package com.kroger.classapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.kroger.classapp.R
import com.kroger.classapp.databinding.FragmentCharacterListBinding
import com.kroger.classapp.ui.adapter.SuperHeroCharacterAdapter
import com.kroger.classapp.viewmodel.SuperHeroViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharacterListFragment : Fragment() {

    private var _binding: FragmentCharacterListBinding? = null
    private val binding get() = _binding!!

    private val superHeroViewModel: SuperHeroViewModel by activityViewModels()
    private val characterAdapter = SuperHeroCharacterAdapter { character, _ ->
        requireActivity().supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragment_container_view, CharacterDetailFragment.newInstance(character.id))
            addToBackStack(null)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCharacterListBinding.inflate(inflater, container, false)
        setupObservers()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = characterAdapter
        }
        superHeroViewModel.fillData()
    }

    fun setupObservers(){
        lifecycleScope.launch {
            superHeroViewModel.characters.collect{event ->
                when (event) {
                    SuperHeroViewModel.SuperHeroCharacterEvent.Failure -> {
                        binding.recyclerView.isVisible = false
                        binding.progressBar.isVisible = false
                        binding.errorMessage.isVisible = true
                    }
                    SuperHeroViewModel.SuperHeroCharacterEvent.Loading -> {
                        binding.recyclerView.isVisible = false
                        binding.progressBar.isVisible = true
                        binding.errorMessage.isVisible = false
                    }
                    is SuperHeroViewModel.SuperHeroCharacterEvent.Success -> {
                        characterAdapter.refreshData(event.characters)
                        binding.recyclerView.isVisible = true
                        binding.progressBar.isVisible = false
                        binding.errorMessage.isVisible = true
                    }
                }
            }
        }
    }
}
