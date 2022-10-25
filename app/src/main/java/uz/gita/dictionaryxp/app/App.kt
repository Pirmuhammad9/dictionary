package uz.gita.dictionaryxp.app

import android.app.Application
import uz.gita.dictionaryxp.data.local.EnglishDatabase
import uz.gita.dictionaryxp.data.local.MyDatabase
import uz.gita.dictionaryxp.domain.impl.AppRepositoryImpl
import uz.gita.dictionaryxp.domain.impl.EnglishRepositoryImpl


class App:Application(){
    override fun onCreate() {
        super.onCreate()
        MyDatabase.init(this)
        EnglishDatabase.init(this)
        AppRepositoryImpl.init()
        EnglishRepositoryImpl.init()
    }
}