package uz.gita.dictionaryxp.data.local

import android.content.Context
import android.database.Cursor

class EnglishDatabase(context: Context) : DBHelper(context, "english.db", 1) {

    companion object {
        private lateinit var instance: EnglishDatabase
        fun init(context: Context) {
            if (::instance.isInitialized) return
            instance = EnglishDatabase(context = context)
        }

        fun getDatabase() = instance
    }

    fun getAllWords(): Cursor {
        val query = "SELECT * from entries"
        val cursor = database().rawQuery(query, null)
        return cursor
    }

    fun getAllQueryWords(word: String): Cursor {
        val query1 = "SELECT * From entries where entries.word LIKE '%$word%'  "
        return database().rawQuery(query1, null)
    }

    fun addToFavourite(id: String, favourite: Int) {
        val query = "UPDATE entries SET is_favourite = $favourite WHERE entries.definition = \"$id\""
        database().execSQL(query)
    }

    fun getFavourite(): Cursor {
        val query = "SELECT * FROM entries WHERE is_favourite = 1"
        return database().rawQuery(query, null)
    }

    fun addNote(id: String, note: String) {
        val query = "UPDATE entries SET note = \"$note\" WHERE entries.definition = \"$id\""
        database().execSQL(query)
    }

    fun getNotedWord(): Cursor {
        val query = "SELECT * FROM entries where length(note)>0"
        return database().rawQuery(query, null)
    }
}