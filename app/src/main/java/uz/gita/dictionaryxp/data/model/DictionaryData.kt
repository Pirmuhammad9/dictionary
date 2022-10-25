package uz.gita.dictionaryxp.data.model

data class DictionaryData(
    val id: Int,
    val english: String,
    val transcript: String,
    val uzbek: String,
    val countable: String,
    val is_Favourite: Int
)