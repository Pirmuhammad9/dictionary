package uz.gita.dictionaryxp.presentation.ui.adapter

import android.database.Cursor
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import uz.gita.dictionaryxp.R
import uz.gita.dictionaryxp.databinding.ItemDictionaryBinding
import uz.gita.dictionaryxp.util.paintQueryPart

class Adapter() : RecyclerView.Adapter<Adapter.Holder>() {
    var cursor: Cursor? = null
    var query: String? = null
    var onRememberClick: ((Int, Int, Int) -> Unit)? = null
    var onItemClick: ((Int, String, String, Int, String) -> Unit)? = null
    var lastPos = -1
    var isEnglish = false

    inner class Holder(val binding: ItemDictionaryBinding) : RecyclerView.ViewHolder(binding.root) {
        val title: TextView = binding.title
        val subTitle: TextView = binding.subTitle

        init {
            subTitle.isSelected = true
            itemView.setOnClickListener {
                cursor?.moveToPosition(absoluteAdapterPosition)
                if (!isEnglish) {
                    onItemClick?.invoke(
                        cursor!!.getInt(0),
                        cursor!!.getString(1),
                        cursor!!.getString(4),
                        cursor!!.getInt(6),
                        cursor!!.getString(7)
                    )
                } else {
                    onItemClick?.invoke(
                        -1,
                        cursor!!.getString(0),
                        cursor!!.getString(2),
                        cursor!!.getInt(3),
                        cursor!!.getString(4)
                    )
                }
            }
        }

        fun bind() {
            cursor?.let {
                it.moveToPosition(absoluteAdapterPosition)
                if (!isEnglish) {
                    Log.d("LANG", "$isEnglish")
                    subTitle.text = it.getString(4)
                    title.text =
                        if (query == null) it.getString(1)
                        else {
                            it.getString(1).paintQueryPart(query!!)
                        }
                } else {
                    subTitle.text = it.getString(2)
                    title.text =
                        if (query == null) it.getString(0)
                        else {
                            it.getString(0).paintQueryPart(query!!)
                        }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder = Holder(
        ItemDictionaryBinding
            .bind(
                LayoutInflater.from(parent.context).inflate(R.layout.item_dictionary, parent, false)
            )
    )

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.slide_in_left)
        if (position > lastPos) {
            holder.itemView.animation = animation
            lastPos = position
        }
        holder.bind()
    }

    override fun getItemCount(): Int {
        cursor?.let {
            return it.count
        }
        return 0
    }

    @JvmName("setOnRememberClick1")
    fun setOnRememberClick(bl: (Int, Int, Int) -> Unit) {
        onRememberClick = bl
    }

    @JvmName("setOnItemClick1")
    fun setOnItemClick(block: (Int, String, String, Int, String) -> Unit) {
        onItemClick = block
    }
}