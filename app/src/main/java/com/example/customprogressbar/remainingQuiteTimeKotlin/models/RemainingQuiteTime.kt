package com.example.customprogressbar.remainingQuiteTimeKotlin.models

import com.example.customprogressbar.remainingQuiteTime.models.QuiteTimeUser

class RemainingQuiteTime constructor(
        val quiteTimeUsers: List<QuiteTimeUser>,
        private var secondsRemaining: Int = 0) {

    companion object {
        private const val secondsInHours = 3600
        private const val secondsInMinutes = 60
    }

    val isFinished: Boolean
        get() = secondsRemaining == 0


    val formattedTimeRemaining: String
        get() {
            val hours = secondsRemaining / secondsInHours
            val minutes = secondsRemaining % secondsInHours / secondsInMinutes
            val seconds = secondsRemaining % secondsInMinutes


            var formattedTime = ""

            if (hours != 0 && minutes != 0) {
                formattedTime = "$hours $minutes "
            } else if (hours == 0 && minutes != 0) {
                formattedTime = "$minutes "
            }
            formattedTime += "$seconds"

            return formattedTime
        }

    fun decrementSecondsRemainig() {
        this.secondsRemaining--
    }
}