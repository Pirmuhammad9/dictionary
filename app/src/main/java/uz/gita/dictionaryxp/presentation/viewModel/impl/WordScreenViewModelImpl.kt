package uz.gita.dictionaryxp.presentation.viewModel.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.dictionaryxp.domain.impl.AppRepositoryImpl
import uz.gita.dictionaryxp.domain.impl.EnglishRepositoryImpl
import uz.gita.dictionaryxp.presentation.viewModel.WordScreenViewModel

class WordScreenViewModelImpl : WordScreenViewModel, ViewModel() {
    private val repository = AppRepositoryImpl.getRepository2()
    private val repositoryenglish = EnglishRepositoryImpl.getRepository2()
    override fun update(id: Int, favourite: Int, meaning: String) {
        if (id < 0) {
            repositoryenglish.addtoFavourite(meaning, favourite).onEach {
            }.launchIn(viewModelScope)
        } else {
            repository.addtoFavourite(id, favourite).onEach {
            }.launchIn(viewModelScope)
        }
    }
}