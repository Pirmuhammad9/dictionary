package uz.gita.dictionaryxp.presentation.ui.screen

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import uz.gita.dictionaryxp.R
import uz.gita.dictionaryxp.presentation.viewModel.SplashViewModel
import uz.gita.dictionaryxp.presentation.viewModel.impl.SplashViewModelImpl

class SplashScreen:Fragment(R.layout.screen_spash) {
    private val viewModel:SplashViewModel by viewModels<SplashViewModelImpl>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().window.navigationBarColor =
            ContextCompat.getColor(requireContext(), R.color.red)
    }

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.openNextScreenLiveData.observe(this@SplashScreen){
            findNavController().navigate(SplashScreenDirections.actionSplashScreenToDictionaryScreen())
        }
    }
}