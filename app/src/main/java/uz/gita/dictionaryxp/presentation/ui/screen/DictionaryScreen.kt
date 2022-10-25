package uz.gita.dictionaryxp.presentation.ui.screen

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uz.gita.dictionaryxp.R
import uz.gita.dictionaryxp.data.model.Item
import uz.gita.dictionaryxp.databinding.ScreenDictionaryBinding
import uz.gita.dictionaryxp.presentation.ui.adapter.Adapter
import uz.gita.dictionaryxp.presentation.viewModel.DictionaryScreenViewModel
import uz.gita.dictionaryxp.presentation.viewModel.impl.DictionaryScreenViewModelImpl


class DictionaryScreen : Fragment(R.layout.screen_dictionary) {
    private lateinit var binding: ScreenDictionaryBinding
    private val viewModel: DictionaryScreenViewModel by viewModels<DictionaryScreenViewModelImpl>()
    private val adapter = Adapter()
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var drawerLayout: DrawerLayout
    private var isEnglish = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter.setOnItemClick { id, english, uzbek, fav, note ->
            Log.d("WWWW", "PPPP")
            findNavController().navigate(
                DictionaryScreenDirections.actionDictionaryScreenToWordScreen(
                    english,
                    uzbek,
                    id,
                    fav,
                    note
                )
            )

        }
        requireActivity().window.navigationBarColor =
            ContextCompat.getColor(requireContext(), R.color.red)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = ScreenDictionaryBinding.bind(view)
        binding.list.visibility = View.GONE
        drawerLayout = binding.drawer
        binding.mainUz.isSelected = true
        if (isEnglish) {
            binding.text.setText(R.string.english_english)
        }
        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.lin2.visibility == View.VISIBLE) {
                    onBackPressed()
                } else {
                    requireActivity().finish()
                }
            }
        })
        animate(binding.historyCard, binding.noteCard)
        binding.searchIcon.setOnClickListener {
            binding.lin1.visibility = View.GONE
            binding.lin2.visibility = View.VISIBLE
            binding.sv.requestFocus()
            openKeyboard()
        }
        binding.back.setOnClickListener {
            onBackPressed()
        }
        binding.searchFloat.setOnClickListener {
            binding.lin1.visibility = View.GONE
            binding.lin2.visibility = View.VISIBLE
            binding.sv.requestFocus()
            openKeyboard()
        }
        binding.refresh.setOnClickListener { viewModel.loadItem() }
        binding.history.setOnClickListener {
            val bundle=Bundle()
            findNavController().navigate(
                DictionaryScreenDirections.actionDictionaryScreenToFavouriteScreen(
                    isEnglish
                )
            )
        }
        binding.navigation.findViewById<TextView>(R.id.dictionaryUz).setOnClickListener {
            isEnglish = false
            binding.text.setText(R.string.english_uzbek)
            viewModel.closeDrawer()
            lifecycleScope.launch(Dispatchers.IO) {
                delay(300)
                withContext(Dispatchers.Main) {
                    animate(binding.historyCard, binding.noteCard)
                    viewModel.load(isEnglish)
                }
            }
        }
        binding.navigation.findViewById<TextView>(R.id.dictionaryEn).setOnClickListener {
            isEnglish = true
            binding.text.setText(R.string.english_english_)
            viewModel.closeDrawer()
            lifecycleScope.launch(Dispatchers.IO) {
                delay(300)
                withContext(Dispatchers.Main) {
                    animate(binding.historyCard, binding.noteCard)
                    viewModel.load(isEnglish)
                }
            }
        }
        binding.navigation.findViewById<TextView>(R.id.exercise).setOnClickListener {
            viewModel.closeDrawer()
            lifecycleScope.launch(Dispatchers.IO) {
                delay(300)
                withContext(Dispatchers.Main) {
                    if(checkForInternet(requireContext())){
                        findNavController().navigate(DictionaryScreenDirections.actionDictionaryScreenToExerciseScreen())
                    }else{
                        showSnackbar(it)
                    }
                }
            }
        }
        binding.note.setOnClickListener {
            findNavController().navigate(
                DictionaryScreenDirections.actionDictionaryScreenToNotesScreen(
                    isEnglish
                )
            )
        }
        binding.navigation.findViewById<TextView>(R.id.rate).setOnClickListener {
            viewModel.closeDrawer()
            if (checkForInternet(requireContext())) {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=uz.gita.dictionarywithmvvm")
                )
                startActivity(intent)
            } else {
                showSnackbar(it)
            }
        }
        binding.navigation.findViewById<TextView>(R.id.share).setOnClickListener {
            viewModel.closeDrawer()
            if (checkForInternet(requireContext())) {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                val app_url = " https://play.google.com/store/apps/details?id=uz.gita.dictionarywithmvvm"
                shareIntent.putExtra(Intent.EXTRA_TEXT, app_url)
                startActivity(Intent.createChooser(shareIntent, "Share via"))
            } else {
                showSnackbar(it)
            }
        }
        binding.navigation.findViewById<TextView>(R.id.about).setOnClickListener {
            viewModel.closeDrawer()
            findNavController().navigate(DictionaryScreenDirections.actionDictionaryScreenToAboutScreen())
        }
        viewModel.loadAllWordsLiveData.observe(viewLifecycleOwner, loadAllWordsObserver)
        viewModel.loadRandomItemLiveData.observe(viewLifecycleOwner, loadRandomItemObserver)
        viewModel.openDrawerLiveData.observe(viewLifecycleOwner, openDrawerObserver)
        viewModel.closeDrawerLiveData.observe(viewLifecycleOwner, closeDrawerObserver)
        binding.list.layoutManager = LinearLayoutManager(requireContext())
        viewModel.loadQueryWord.observe(viewLifecycleOwner, loadQueryWordsObserver)
        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
        binding.burger.setOnClickListener {
            viewModel.openDrawer()
        }
        binding.sv.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                handler.removeCallbacksAndMessages(null)
                viewModel.getWordsByQuery(query!!, true)
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                handler.removeCallbacksAndMessages(null)
                if (p0.isNullOrEmpty()) {
                    binding.list.visibility = View.GONE
                    binding.base.visibility = View.VISIBLE
                } else {
                    binding.base.visibility = View.GONE
                    binding.list.visibility = View.VISIBLE
                    viewModel.getWordsByQuery(p0!!.lowercase(), isEnglish)
                }
                return true
            }
        })
    }

    override fun onStop() {
        super.onStop()
        clearSearch()
        binding.list.visibility = View.GONE
    }

    private val loadQueryWordsObserver = Observer<Pair<Cursor, String>> {
        adapter.isEnglish = isEnglish
        adapter.cursor = it.first
        adapter.query = it.second
        binding.list.adapter = adapter
    }

    private val loadAllWordsObserver = Observer<Cursor> {
        adapter.isEnglish = isEnglish
        adapter.cursor = it
        binding.list.adapter = adapter
    }

    private val openDrawerObserver = Observer<Unit> {
        drawerLayout.openDrawer(Gravity.START)
    }
    private val closeDrawerObserver = Observer<Unit> {
        drawerLayout.closeDrawer(Gravity.START)
    }
    private val loadRandomItemObserver = Observer<Item> {
        YoYo.with(Techniques.FadeIn).duration(600).playOn(binding.main)
        YoYo.with(Techniques.FadeIn).duration(600).playOn(binding.mainUz)
        binding.main.text = it.word
        binding.mainUz.text = it.meaning
    }

    private fun animate(view1: View, view2: View) {
        val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_anim)
        val animationF = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_anim_second)
        view1.animation = animation
        view2.animation = animationF
    }

    private fun openKeyboard() {
        val inputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInputFromWindow(
            requireActivity().currentFocus!!.windowToken,
            InputMethodManager.SHOW_FORCED,
            0
        )
    }

    private fun onBackPressed() {
        clearSearch()
        binding.lin1.visibility = View.VISIBLE
        binding.lin2.visibility = View.GONE
        binding.list.visibility = View.GONE
        binding.base.visibility = View.VISIBLE
        binding.emptyData.visibility = View.GONE
    }

    private fun clearSearch() {
        binding.sv.setQuery("", false)
    }

    private fun showSnackbar(view: View) {
        val snack = Snackbar.make(view, "No internet connection", Snackbar.LENGTH_SHORT)
            .setAction("Action", null)
        val view = snack.view
        view.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.red1))
        val tv: TextView = view.findViewById(com.google.android.material.R.id.snackbar_text)
        tv.setTextColor(Color.WHITE)
        snack.show()
    }

    private fun checkForInternet(context: Context): Boolean {

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }
}