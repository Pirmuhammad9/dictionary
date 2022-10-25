package uz.gita.dictionaryxp.presentation.viewModel.impl

import android.database.Cursor
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.dictionaryxp.domain.impl.AppRepositoryImpl
import uz.gita.dictionaryxp.domain.impl.EnglishRepositoryImpl
import uz.gita.dictionaryxp.presentation.viewModel.FavouriteViewModel

class FavouriteViewModelImpl : ViewModel(), FavouriteViewModel {
    private val repository = AppRepositoryImpl.getRepository2()
    private val repositoryEnglish = EnglishRepositoryImpl.getRepository2()
    override val favouriteLiveData = MutableLiveData<Cursor>()
    override val emptyListLiveData = MutableLiveData<Boolean>()

    override fun getNotedData(isEnglish: Boolean) {
        if (isEnglish) {
            repositoryEnglish.getFavouriteWords().onEach {
                if (it.isSuccess) {
                    if (it.getOrNull() != null && it.getOrNull()!!.count > 0) {
                        favouriteLiveData.value = it.getOrNull()
                        emptyListLiveData.value = false
                    } else {
                        emptyListLiveData.value = true
                    }
                }
            }.launchIn(viewModelScope)
        } else {
            repository.getFavouriteWords().onEach {
                if (it.getOrNull() != null && it.getOrNull()!!.count > 0) {
                    favouriteLiveData.value = it.getOrNull()
                    emptyListLiveData.value = false
                } else {
                    emptyListLiveData.value = true
                }
            }.launchIn(viewModelScope)
        }
    }
}