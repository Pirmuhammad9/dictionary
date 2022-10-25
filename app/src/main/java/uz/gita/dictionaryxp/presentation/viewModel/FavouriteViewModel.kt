package uz.gita.dictionaryxp.presentation.viewModel

import android.database.Cursor
import androidx.lifecycle.LiveData

interface FavouriteViewModel {
    val favouriteLiveData:LiveData<Cursor>
    val emptyListLiveData:LiveData<Boolean>
    fun getNotedData(isEnglish:Boolean)
}