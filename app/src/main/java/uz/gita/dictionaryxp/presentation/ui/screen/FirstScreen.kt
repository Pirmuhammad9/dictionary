package uz.gita.dictionaryxp.presentation.ui.screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.dictionaryxp.R
import uz.gita.dictionaryxp.databinding.ScreenFirstBinding

class FirstScreen:Fragment(R.layout.screen_first) {
    private val binding by viewBinding(ScreenFirstBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var word:String? =null
        arguments?.let {
            word = it.getString("TTT")
        }
        binding.wordFirstPage.text = word?:"not found"
    }
}