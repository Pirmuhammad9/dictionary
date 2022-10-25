package uz.gita.dictionaryxp.presentation.viewModel.impl

import android.database.Cursor
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.dictionaryxp.data.model.Item
import uz.gita.dictionaryxp.domain.impl.AppRepositoryImpl
import uz.gita.dictionaryxp.domain.impl.EnglishRepositoryImpl
import uz.gita.dictionaryxp.presentation.viewModel.DictionaryScreenViewModel
import kotlin.random.Random

class DictionaryScreenViewModelImpl : ViewModel(), DictionaryScreenViewModel {
    private val repository = AppRepositoryImpl.getRepository2()
    private val repositoryEnglish = EnglishRepositoryImpl.getRepository2()
    override val loadAllWordsLiveData = MutableLiveData<Cursor>()
    override val loadQueryWord = MutableLiveData<Pair<Cursor, String>>()
    override val openDrawerLiveData = MutableLiveData<Unit>()
    override val closeDrawerLiveData = MutableLiveData<Unit>()
    override val loadRandomItemLiveData = MutableLiveData<Item>()
    override val errorLiveData = MutableLiveData<String>()
    override val emptyListLiveData = MutableLiveData<Boolean>()
    private lateinit var cursor: Cursor
    private var english = false

    init {
        load(false)
    }

    override fun getWordsByQuery(word: String, isEnglish: Boolean) {
        english = isEnglish
        if (!english) {
            repository.getAllQueryWords(word).onEach {
                it.onSuccess {
                    if (it.count > 0) {
                        loadQueryWord.value = Pair(it, word)
                        emptyListLiveData.value = false
                    } else {
                        emptyListLiveData.value = true
                    }
                }.onFailure {
                    errorLiveData.value = it.message
                }
            }.launchIn(viewModelScope)
        } else {
            repositoryEnglish.getAllQueryWords(word).onEach {
                it.onSuccess {
                    if (it.count > 0) {
                        loadQueryWord.value = Pair(it, word)
                        emptyListLiveData.value = false
                    } else {
                        emptyListLiveData.value = true
                    }
                }.onFailure {
                    errorLiveData.value = it.message
                }
            }.launchIn(viewModelScope)
        }
    }

    override fun update(id: String, favourite: Int, isEnglish: Boolean) {
        english = isEnglish
        if (!english) {
            repository.addtoFavourite(id.toInt(), favourite).onEach {
            }.launchIn(viewModelScope)
        } else {
            repositoryEnglish.addtoFavourite(id, favourite).onEach {
            }.launchIn(viewModelScope)
        }
    }

    override fun load(isEnglish: Boolean) {
        english = isEnglish
        if (!english) {
            repository.getAllWords().onEach {
                it.onSuccess {
                    loadAllWordsLiveData.value = it
                    cursor = it
                    loadItem()
                }
                it.onFailure {
                    errorLiveData.value = it.message
                }
            }.launchIn(viewModelScope)
        } else {
            repositoryEnglish.getAllWords().onEach {
                it.onSuccess {
                    loadAllWordsLiveData.value = it
                    cursor = it
                    loadItem()
                }
                it.onFailure {
                    errorLiveData.value = it.message
                }
            }.launchIn(viewModelScope)
        }
    }

    override fun openDrawer() {
        openDrawerLiveData.value = Unit
    }

    override fun closeDrawer() {
        closeDrawerLiveData.value = Unit
    }

    override fun loadItem() {
        val random = Random.nextInt(cursor.count - 1)
        cursor.moveToPosition(random)
        if (!english) {
            loadRandomItemLiveData.value = Item(cursor.getString(1), cursor.getString(4))
        } else {
            loadRandomItemLiveData.value = Item(cursor.getString(0), cursor.getString(2))
        }
    }

}