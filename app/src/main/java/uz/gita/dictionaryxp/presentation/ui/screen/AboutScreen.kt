package uz.gita.dictionaryxp.presentation.ui.screen

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import uz.gita.dictionaryxp.R
import uz.gita.dictionaryxp.databinding.ScreenAboutBinding

class AboutScreen:Fragment(R.layout.screen_about) {
    private val binding by viewBinding(ScreenAboutBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.facebook.setOnClickListener {
            if (checkForInternet(requireContext())){
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/pirmuhammad.xusanov"))
                startActivity(intent)
            }else{
                showSnackbar(it)
            }
        }
         binding.telegram.setOnClickListener {
            if (checkForInternet(requireContext())){
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/Pirmuhammad1"))
                startActivity(intent)
            }else{
                showSnackbar(it)
            }
        }
         binding.gmail.setOnClickListener {
            if (checkForInternet(requireContext())){
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://mail.google.com/mail/u/0/?tab=km#inbox"))
                startActivity(intent)
            }else{
                showSnackbar(it)
            }
        }
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }
    private fun checkForInternet(context: Context): Boolean {

        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
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
    private fun showSnackbar(view: View){
        val snack = Snackbar.make(view, "No internet connection", Snackbar.LENGTH_SHORT).setAction("Action", null)
        val view = snack.view
        view.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.red1))
        val tv: TextView = view.findViewById(com.google.android.material.R.id.snackbar_text)
        tv.setTextColor(Color.WHITE)
        snack.show()
    }
}