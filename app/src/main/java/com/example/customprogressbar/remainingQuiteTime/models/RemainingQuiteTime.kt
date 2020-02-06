package com.example.customprogressbar.remainingQuiteTime.models

class RemainingQuiteTime constructor(
        val quiteTimeUsers: List<QuiteTimeUser>,
        private var secondsRemaining: Int = 0) {

    companion object {
        private const val secondsInHours = 3600
        private const val secondsInMinutes = 60
    }

    var stopped: Boolean = false

    val isFinished: Boolean
        get() = secondsRemaining == 0


    val formattedTimeRemaining: String
        get() {
            val hours = secondsRemaining / secondsInHours
            val minutes = secondsRemaining % secondsInHours / secondsInMinutes
            val seconds = secondsRemaining % secondsInMinutes

            var formattedTime = ""
            if (hours != 0 && minutes != 0) {
                formattedTime = "${hours}h ${minutes}m "
            } else if (hours == 0 && minutes != 0) {
                formattedTime = "${minutes}m "
            }
            formattedTime += "${seconds}s"

            return formattedTime
        }

    fun decrementSecondsRemainig() {
        this.secondsRemaining--
    }
}