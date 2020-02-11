package com.example.customprogressbar.remainingQuiteTime

import android.text.Spannable
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import com.example.customprogressbar.remainingQuiteTime.TimeFormatter.biggerFontRatio

object TimeFormatter {

    const val biggerFontRatio = 1.67f
    private const val secondsInHours = 3600
    private const val secondsInMinutes = 60

    fun formatRemainingTime(secondsRemaining: Int): SpannableString {
        val areDoubleDigits = arrayListOf<Boolean>()
        val hours = secondsRemaining / secondsInHours
        val minutes = secondsRemaining % secondsInHours / secondsInMinutes
        val seconds = secondsRemaining % secondsInMinutes

        var formattedTime = ""
        if (hours != 0 && minutes != 0) {
            formattedTime = "${hours}h ${minutes}m "
            areDoubleDigits.add(hours/10 > 0)
            areDoubleDigits.add(minutes/10 > 0)
        } else if (hours == 0 && minutes != 0) {
            formattedTime = "${minutes}m "
            areDoubleDigits.add(minutes/10 > 0)
        }
        formattedTime += "${seconds}s"
        areDoubleDigits.add(seconds/10 > 0)

        return createSpannableString(formattedTime, areDoubleDigits)
    }

    private fun createSpannableString(formattedTime: String, areDoubleDigits: List<Boolean>): SpannableString {
        val spannable = SpannableString(formattedTime)
        var currentIndex = 0
        for(isDoubleDigit in areDoubleDigits) {
            val numberSpace = if (isDoubleDigit) 2 else 1
            spannable.setRelativeSizeSpan(currentIndex, currentIndex + numberSpace)
            currentIndex += numberSpace + 2
        }
        return spannable
    }

}

fun SpannableString.setRelativeSizeSpan(startIndex: Int, endIndex: Int) {
    setSpan(RelativeSizeSpan(biggerFontRatio), startIndex,
            endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
}