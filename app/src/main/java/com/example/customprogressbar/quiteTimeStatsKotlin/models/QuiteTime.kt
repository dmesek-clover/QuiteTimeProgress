package com.example.customprogressbar.quiteTimeStatsKotlin.models

data class QuiteTime(
        val totalAmount: Int,
        val usedAmount: Int,
        var dayOfWeek: DayOfWeek? = null
)