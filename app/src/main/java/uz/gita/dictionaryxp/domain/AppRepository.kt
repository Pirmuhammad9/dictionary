package uz.gita.dictionaryxp.domain

import android.database.Cursor
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    fun getAllWords(): Flow<Result<Cursor>>
    fun getFavouriteWords(): Flow<Result<Cursor>>
    fun getAllQueryWords(words: String): Flow<Result<Cursor>>
    fun addtoFavourite(id:Int, favourite:Int):Flow<Unit>
    fun addToNote(id: Int, note:String):Flow<Unit>
    fun getNotedWords():Flow<Result<Cursor>>
}