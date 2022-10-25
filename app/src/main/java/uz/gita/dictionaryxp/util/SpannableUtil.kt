package uz.gita.dictionaryxp.util

import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan

fun String.paintQueryPart(query: String): Spannable {
    var start = this.indexOf(query)
    if (start < 0) start = 0
    var end = start + query.length
    if (end>this.length) end = 0
    if (this.equals(query, true)) {
        start = 0
        end = this.length
    }
    val spannable = SpannableString(this)
    spannable.setSpan(StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
    spannable.setSpan(
        ForegroundColorSpan(Color.RED),
        start,
        end,
        Spannable.SPAN_INCLUSIVE_EXCLUSIVE
    )
    return spannable
}