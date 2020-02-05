package com.example.customprogressbar.remainingQuiteTime

import android.os.CountDownTimer
import java.util.LinkedList

interface QuiteTimeTimerListener {
    fun onTick(): Int?
}

object QuiteTimeTimer {

    private const val millisInSecond = 1000

    private val timer: CountDownTimer
    private var timerListeners = LinkedList<QuiteTimeTimerListener>()

    init {
        timer = object : CountDownTimer(java.lang.Long.MAX_VALUE, millisInSecond.toLong()) {
            override fun onTick(l: Long) {
                var removePosition: Int? = null
                for (timerListener in timerListeners) {
                    if (removePosition == null) {
                        removePosition = timerListener.onTick()
                    } else {
                        timerListener.onTick()
                    }
                }

                if (removePosition != null) {
                    //unsubscribed, so that the timer can stop ticking if no one is listening
                    unsubscribeFromTimer(timerListeners[removePosition])
                }
            }

            override fun onFinish() {
                //ignore
            }
        }
    }

    fun subscribeToTimer(quiteTimeTimerListener: QuiteTimeTimerListener) {
        if (timerListeners.isEmpty()) {
            timer.start()
        }
        timerListeners.add(quiteTimeTimerListener)
    }

    fun unsubscribeFromTimer(quiteTimeTimerListener: QuiteTimeTimerListener) {
        timerListeners.remove(quiteTimeTimerListener)
        if (timerListeners.isEmpty()) {
            timer.cancel()
        }
    }
}