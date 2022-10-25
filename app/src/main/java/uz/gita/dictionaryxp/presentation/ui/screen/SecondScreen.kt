package uz.gita.dictionaryxp.presentation.ui.screen

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.dictionaryxp.R
import uz.gita.dictionaryxp.databinding.ScreenSecondBinding
import uz.gita.dictionaryxp.presentation.viewModel.SecondScreenViewModel
import uz.gita.dictionaryxp.presentation.viewModel.impl.SecondScreenViewModelImpl

class SecondScreen : Fragment(R.layout.screen_second) {
    private val viewModel: SecondScreenViewModel by viewModels<SecondScreenViewModelImpl>()
    private val binding by viewBinding(ScreenSecondBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var idNote = 1
        var note: String?
        var meaning:String? = null
        arguments?.let {
            note = it.getString("NOTE")
            idNote = it.getInt("ID")
            meaning = it.getString("MEANING")
            binding.addNote.setText(note)
        }
        binding.addNote.addTextChangedListener {
            it.toString()
            viewModel.addNote(idNote, it.toString(), meaning!!)
        }
    }
}
