package uz.gita.dictionaryxp.data.local

import android.content.Context
import android.database.Cursor

class MyDatabase(context: Context) : DBHelper(context, "dictionary.db", 1) {
    companion object {
        lateinit var instance: MyDatabase
        fun init(context: Context) {
            if (::instance.isInitialized) return
            instance = MyDatabase(context)
        }

        fun getDatabase(): MyDatabase = instance
    }

    fun getAllWords(): Cursor {
        val query = "SELECT * from dictionary"
        val cursor = database().rawQuery(query, null)
        return cursor
    }

    fun getAllQueryWords(word: String): Cursor {
        val query1 = "SELECT * From dictionary where dictionary.english LIKE '%$word%'  "
        return database().rawQuery(query1, null)
    }

    fun addToFavourite(id: Int, favourite: Int) {
        val query = "UPDATE dictionary SET is_favourite = $favourite WHERE dictionary.id =$id"
        database().execSQL(query)
    }

    fun getFavourite(): Cursor {
        val query = "SELECT * FROM dictionary WHERE is_favourite = 1"
        return database().rawQuery(query, null)
    }

    fun addNote(id: Int, note: String) {
        val query = "UPDATE dictionary SET note = \"$note\" WHERE dictionary.id = $id"
        database().execSQL(query)
    }

    fun getNotedWord(): Cursor {
        val query = "SELECT * FROM dictionary where length(note)>0"
        return database().rawQuery(query, null)
    }
}