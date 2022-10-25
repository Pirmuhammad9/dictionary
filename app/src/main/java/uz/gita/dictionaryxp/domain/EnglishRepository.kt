package uz.gita.dictionaryxp.domain

import android.database.Cursor
import kotlinx.coroutines.flow.Flow

interface EnglishRepository {

    fun getAllWords(): Flow<Result<Cursor>>
    fun getFavouriteWords(): Flow<Result<Cursor>>
    fun getAllQueryWords(words: String): Flow<Result<Cursor>>
    fun addtoFavourite(id:String, favourite:Int): Flow<Unit>
    fun addToNote(id: String, note:String): Flow<Unit>
    fun getNotedWords(): Flow<Result<Cursor>>

}