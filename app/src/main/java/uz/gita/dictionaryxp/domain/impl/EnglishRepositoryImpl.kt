package uz.gita.dictionaryxp.domain.impl

import android.database.Cursor
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.dictionaryxp.data.local.EnglishDatabase
import uz.gita.dictionaryxp.domain.EnglishRepository

class EnglishRepositoryImpl: EnglishRepository {
    val database: EnglishDatabase = EnglishDatabase.getDatabase()
    companion object {
        lateinit var repository: EnglishRepository
        fun init() {
            if (::repository.isInitialized) return
            repository = EnglishRepositoryImpl()
        }

        fun getRepository2() = repository
    }

    override fun getAllWords(): Flow<Result<Cursor>> = flow {
        emit(Result.success(database.getAllWords()))
    }.catch {
        emit(Result.failure(Exception(it)))
    }.flowOn(Dispatchers.IO)

    override fun getFavouriteWords(): Flow<Result<Cursor>> = flow {
        emit(Result.success(database.getFavourite()))
    }.catch {
        emit(Result.failure(Exception(it)))
    }.flowOn(Dispatchers.IO)

    override fun getAllQueryWords(words: String): Flow<Result<Cursor>> = flow {
        emit(Result.success(database.getAllQueryWords(words)))
    }.catch {
        emit(Result.failure(Exception(it)))
    }.flowOn(Dispatchers.IO)

    override fun addtoFavourite(id: String, favourite: Int) = flow<Unit> {
        database.addToFavourite(id, favourite)
        Log.d("AAAAA", "id->$id   --  fav->$favourite")
    }.catch {
    }.flowOn(Dispatchers.IO)

    override fun addToNote(id: String, note: String) = flow<Unit> {
        database.addNote(id, note)
    }.flowOn(Dispatchers.IO)

    override fun getNotedWords(): Flow<Result<Cursor>> = flow {
        emit(Result.success(database.getNotedWord()))
    }.catch {
        emit(Result.failure(Exception(it)))
    }.flowOn(Dispatchers.IO)
}