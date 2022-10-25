package uz.gita.dictionaryxp.presentation.viewModel

import android.database.Cursor
import androidx.lifecycle.LiveData
import uz.gita.dictionaryxp.data.model.Item

interface DictionaryScreenViewModel {
    val loadAllWordsLiveData: LiveData<Cursor>
    val errorLiveData: LiveData<String>
    val emptyListLiveData: LiveData<Boolean>
    val loadQueryWord: LiveData<Pair<Cursor, String>>
    val openDrawerLiveData: LiveData<Unit>
    val closeDrawerLiveData: LiveData<Unit>
    val loadRandomItemLiveData: LiveData<Item>
    fun getWordsByQuery(word: String, isEnglish: Boolean)
    fun update(id: String, favourite: Int, isEnglish: Boolean)
    fun load(isEnglish: Boolean)
    fun openDrawer()
    fun closeDrawer()
    fun loadItem()
}