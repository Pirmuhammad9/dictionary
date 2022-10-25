package uz.gita.dictionaryxp.presentation.ui.screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.dictionaryxp.R
import uz.gita.dictionaryxp.databinding.ScreenExerciseBinding

class ExerciseScreen : Fragment(R.layout.screen_exercise) {
    private val binding by viewBinding(ScreenExerciseBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.webView.loadUrl("https://www.englishtestsonline.com/english-vocabulary-tests/")
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}