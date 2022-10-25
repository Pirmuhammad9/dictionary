package uz.gita.dictionaryxp.presentation.ui.screen

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import uz.gita.dictionaryxp.R
import uz.gita.dictionaryxp.databinding.ScreenWordBinding
import uz.gita.dictionaryxp.presentation.ui.adapter.ViewPagerAdapter
import uz.gita.dictionaryxp.presentation.viewModel.WordScreenViewModel
import uz.gita.dictionaryxp.presentation.viewModel.impl.WordScreenViewModelImpl

class WordScreen : Fragment(R.layout.screen_word) {
    private val binding by viewBinding(ScreenWordBinding::bind)
    private var pagerAdapter: ViewPagerAdapter? = null
    private val args by navArgs<WordScreenArgs>()
    private val viewModel: WordScreenViewModel by viewModels<WordScreenViewModelImpl>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(object :OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val tabLayout = binding.tabLayout
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.copy.setOnClickListener {
            saveToClipBoard(args.english)
            showSnackbar(it, args.english)
        }
        pagerAdapter =
            ViewPagerAdapter(childFragmentManager, lifecycle, args.uzbek, args.id, args.note)
        binding.word.text = args.english
        tabLayout.addTab(tabLayout.newTab().setText("UZBEK"))
        tabLayout.addTab(tabLayout.newTab().setText("NOTES"))
        val like = binding.liked
        val viewPager = binding.viewPager
        val b = args.fav
        if (b == 1) {
            like.setImageResource(R.drawable.ic_heart)
            like.tag = R.drawable.ic_heart
        } else {
            like.setImageResource(R.drawable.ic_heart_outline)
            like.tag = R.drawable.ic_heart_outline
        }
        viewPager.adapter = pagerAdapter
        
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
           if(position == 0){
               tab.text="MEANING"
           }else{
               tab.text="NOTES"
           }
        }.attach()
        like.setOnClickListener {
            if (like.tag == R.drawable.ic_heart) {
                like.tag = R.drawable.ic_heart_outline
                like.setImageResource(R.drawable.ic_heart_outline)
                viewModel.update(args.id, 0, args.uzbek)
            } else {
                like.tag = R.drawable.ic_heart
                like.setImageResource(R.drawable.ic_heart)
                viewModel.update(args.id, 1, args.uzbek)
            }
        }
    }

    private fun saveToClipBoard(text: String) {
        val clipboard =
            requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Copied Text", text)
        clipboard.setPrimaryClip(clip)
    }

    private fun showSnackbar(view: View, message:String){
        val snack = Snackbar.make(view, args.english + " copied", Snackbar.LENGTH_SHORT).setAction("Action", null)
        val view = snack.view
        view.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.red1))
        val tv:TextView = view.findViewById(com.google.android.material.R.id.snackbar_text)
        tv.setTextColor(Color.WHITE)
        snack.show()
    }
}