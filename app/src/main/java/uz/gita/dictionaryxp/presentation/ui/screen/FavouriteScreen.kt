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
import uz.gita.dictionaryxp.databinding.ScreenFavouriteBinding
import uz.gita.dictionaryxp.presentation.ui.adapter.Adapter
import uz.gita.dictionaryxp.presentation.viewModel.FavouriteViewModel
import uz.gita.dictionaryxp.presentation.viewModel.impl.FavouriteViewModelImpl

class FavouriteScreen : Fragment(R.layout.screen_favourite) {
    private lateinit var binding: ScreenFavouriteBinding
    private val viewModel: FavouriteViewModel by viewModels<FavouriteViewModelImpl>()
    private val args by navArgs<FavouriteScreenArgs>()
    private val adapter = Adapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter.setOnItemClick { id, english, uzbek, fav, note ->
            findNavController().navigate(
                FavouriteScreenDirections.actionFavouriteScreenToWordScreen(
                    english,
                    uzbek,
                    id,
                    fav,
                    note
                )
            )
        }
        requireActivity().onBackPressedDispatcher.addCallback(object :OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getNotedData(args.isEnglish)
        binding = ScreenFavouriteBinding.bind(view)
        viewModel.favouriteLiveData.observe(viewLifecycleOwner, observer)
        viewModel.emptyListLiveData.observe(viewLifecycleOwner, emptyObserver)
        binding.list.adapter = adapter
        binding.list.layoutManager = LinearLayoutManager(requireContext())
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private val observer = Observer<Cursor> {
        adapter.isEnglish = args.isEnglish
        adapter.cursor = it
        binding.list.adapter = adapter
    }
    private val emptyObserver = Observer<Boolean> {
        if (it){
            binding.emptyData.visibility = View.VISIBLE
        }else{
            binding.emptyData.visibility = View.GONE
        }
    }
}