package uz.gita.dictionaryxp.presentation.viewModel.impl

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.gita.dictionaryxp.presentation.viewModel.SplashViewModel

class SplashViewModelImpl : ViewModel(), SplashViewModel {
    override val openNextScreenLiveData = MutableLiveData<Unit>()
    private val handler = Handler(Looper.getMainLooper())

    init {
        viewModelScope.launch() {
            delay(1000)
            openNextScreenLiveData.postValue(Unit)
            handler.removeCallbacksAndMessages(null)
        }
    }

}