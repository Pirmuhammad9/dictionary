package uz.gita.dictionaryxp.presentation.viewModel.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.dictionaryxp.domain.impl.AppRepositoryImpl
import uz.gita.dictionaryxp.domain.impl.EnglishRepositoryImpl
import uz.gita.dictionaryxp.presentation.viewModel.SecondScreenViewModel

class SecondScreenViewModelImpl : ViewModel(), SecondScreenViewModel {
    private val repository = AppRepositoryImpl.getRepository2()
    private val repositoryEnglish = EnglishRepositoryImpl.getRepository2()
    override fun addNote(id: Int, note: String, meaning: String) {
        if (id < 0) {
            repositoryEnglish.addToNote(meaning, note).onEach { }.launchIn(viewModelScope)
        } else {
            repository.addToNote(id, note).onEach { }.launchIn(viewModelScope)
        }
    }
}