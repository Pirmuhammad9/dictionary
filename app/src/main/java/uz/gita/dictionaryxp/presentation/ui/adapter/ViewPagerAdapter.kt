package uz.gita.dictionaryxp.presentation.ui.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import uz.gita.dictionaryxp.presentation.ui.screen.FirstScreen
import uz.gita.dictionaryxp.presentation.ui.screen.SecondScreen

class ViewPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle,private val word : String, private val idN:Int, private val note:String) :
    FragmentStateAdapter(fm, lifecycle) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return FirstScreen().apply {
                val bundle = Bundle()
                bundle.putString("TTT", word)
                arguments = bundle
            }
            else -> return SecondScreen().apply {
                val bundle = Bundle()
                bundle.putInt("ID", idN)
                bundle.putString("NOTE", note)
                bundle.putString("MEANING", word)
                arguments = bundle
            }
        }
    }
}