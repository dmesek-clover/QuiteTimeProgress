package com.example.customprogressbar.quiteTimeStats.models

data class QuiteTime(
        val totalAmount: Int,
        val usedAmount: Int,
        var dayOfWeek: DayOfWeek? = null
)