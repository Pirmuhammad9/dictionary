package uz.gita.dictionaryxp.presentation.ui.screen

import android.database.Cursor
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import uz.gita.dictionaryxp.R
import uz.gita.dictionaryxp.databinding.ScreenNotesBinding
import uz.gita.dictionaryxp.presentation.ui.adapter.Adapter
import uz.gita.dictionaryxp.presentation.viewModel.NoteScreenViewModel
import uz.gita.dictionaryxp.presentation.viewModel.impl.NoteScreenViewModelImpl

class NotesScreen : Fragment(R.layout.screen_notes) {
    private lateinit var binding: ScreenNotesBinding
    private val viewModel: NoteScreenViewModel by viewModels<NoteScreenViewModelImpl>()
    private val args by navArgs<NotesScreenArgs>()
    private val adapter = Adapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter.setOnItemClick { id, english, uzbek, fav, note ->
            findNavController().navigate(
                NotesScreenDirections.actionNotesScreenToWordScreen(
                    english,
                    uzbek,
                    id,
                    fav,
                    note
                )
            )
        }
        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = ScreenNotesBinding.bind(view)
        viewModel.getNotedData(args.isEnglish)
        viewModel.loadNotedData.observe(viewLifecycleOwner, observer)
        viewModel.emptyNotedData.observe(viewLifecycleOwner, emptyObserver)
        binding.list.adapter = adapter
        binding.list.layoutManager = LinearLayoutManager(requireContext())
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private val observer = Observer<Cursor> {
        adapter.cursor = it
        adapter.isEnglish = args.isEnglish
        binding.list.adapter = adapter
    }

    private val emptyObserver = Observer<Boolean> {
        if (it) {
            binding.emptyData.visibility = View.VISIBLE
        } else {
            binding.emptyData.visibility = View.GONE
        }
    }
}