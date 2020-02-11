package com.example.customprogressbar.remainingQuiteTime.models


class RemainingQuiteTime constructor(
        val quiteTimeUsers: ArrayList<QuiteTimeUser>,
        var secondsRemaining: Int = 0) {

    var stopped: Boolean = false

    val isFinished: Boolean
        get() = secondsRemaining == 0


    fun decrementSecondsRemainig() {
        this.secondsRemaining--
    }
}

