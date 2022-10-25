package uz.gita.dictionaryxp.presentation.viewModel

import android.database.Cursor
import androidx.lifecycle.LiveData

interface NoteScreenViewModel {
    val emptyNotedData:LiveData<Boolean>
    val loadNotedData:LiveData<Cursor>
    fun getNotedData(isEnglish:Boolean)
}