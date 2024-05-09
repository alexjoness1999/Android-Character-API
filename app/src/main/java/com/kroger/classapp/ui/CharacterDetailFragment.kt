import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.kroger.classapp.databinding.FragmentCharacterDetailBinding
import com.kroger.classapp.model.SuperHeroCharacter
import com.kroger.classapp.viewmodel.CharacterDetailViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CharacterDetailFragment : Fragment() {

    private var _binding: FragmentCharacterDetailBinding? = null
    private val binding get() = _binding!!

    private val characterDetailViewModel: CharacterDetailViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val characterId = requireArguments().getInt(BUNDLE_ID)
        characterDetailViewModel.fetchCharacterById(characterId)

        setupObservers()
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            characterDetailViewModel.character.collect { event ->
                when (event) {
                    is CharacterDetailViewModel.CharacterDetailEvent.Failure -> {
                        binding.recyclerView.isVisible = false
                        binding.progressBar.isVisible = false
                        binding.errorMessage.isVisible = true
                    }
                    CharacterDetailViewModel.CharacterDetailEvent.Loading -> {
                        binding.recyclerView.isVisible = false
                        binding.progressBar.isVisible = true
                        binding.errorMessage.isVisible = false
                    }
                    is CharacterDetailViewModel.CharacterDetailEvent.Success -> {
                        displayCharacter(event.character)
                        binding.recyclerView.isVisible = true
                        binding.progressBar.isVisible = false
                        binding.errorMessage.isVisible = true
                    }
                }
            }
        }
    }

    fun displayCharacter(character: SuperHeroCharacter) {
        binding.characterNameDetail.text = character.name
        binding.characterAliasDetail.text = character.alias.toString()
        binding.characterBirthplaceDetail.text = character.birthplace
        Glide.with(this).load(character.img).into(binding.characterImageDetail)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val BUNDLE_ID = "character_id"

        fun newInstance(id: Int) = CharacterDetailFragment().apply {
            arguments = Bundle().apply {
                putInt(BUNDLE_ID, id)
            }
        }
    }
}
