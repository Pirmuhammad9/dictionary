package uz.gita.dictionaryxp.presentation.viewModel.impl

import android.database.Cursor
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.dictionaryxp.domain.impl.AppRepositoryImpl
import uz.gita.dictionaryxp.domain.impl.EnglishRepositoryImpl
import uz.gita.dictionaryxp.presentation.viewModel.NoteScreenViewModel

class NoteScreenViewModelImpl : NoteScreenViewModel, ViewModel() {
    private val repository = AppRepositoryImpl.getRepository2()
    private val repositoryEnglish = EnglishRepositoryImpl.getRepository2()
    override val emptyNotedData = MutableLiveData<Boolean>()
    override val loadNotedData = MutableLiveData<Cursor>()

    override fun getNotedData(isEnglish: Boolean) {
        if (isEnglish) {
            repositoryEnglish.getNotedWords().onEach {
                if (it.isSuccess) {
                    if (it.getOrNull() != null && it.getOrNull()!!.count > 0) {
                        loadNotedData.value = it.getOrNull()
                        emptyNotedData.value = false
                    } else {
                        emptyNotedData.value = true
                    }
                }
            }.launchIn(viewModelScope)
        } else {
            repository.getNotedWords().onEach {
                if (it.isSuccess) {
                    if (it.getOrNull() != null && it.getOrNull()!!.count > 0) {
                        loadNotedData.value = it.getOrNull()
                        emptyNotedData.value = false
                    } else {
                        emptyNotedData.value = true
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
}