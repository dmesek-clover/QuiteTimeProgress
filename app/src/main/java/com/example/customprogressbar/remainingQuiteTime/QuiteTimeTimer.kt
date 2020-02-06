package com.example.customprogressbar.remainingQuiteTime

import android.os.CountDownTimer
import java.util.LinkedList

interface QuiteTimeTimerListener {
    fun onTick()
    fun onFinished(index: Int)
    fun isFinished(): Boolean
    fun isStopped(): Boolean
}

object QuiteTimeTimer {

    private const val millisInSecond = 1000

    private val timer: CountDownTimer
    private val timerListeners = LinkedList<QuiteTimeTimerListener>()


    init {
        timer = object : CountDownTimer(java.lang.Long.MAX_VALUE, millisInSecond.toLong()) {
            override fun onTick(l: Long) {
                var finishedTimerListener: QuiteTimeTimerListener? = null
                var stoppedTimerListener: QuiteTimeTimerListener? = null
                for (timerListener in timerListeners) {
                    timerListener.onTick()
                    if(timerListener.isFinished()) {
                        finishedTimerListener = timerListener
                    }
                    if(timerListener.isStopped()) {
                        stoppedTimerListener = timerListener
                    }
                }
                if(finishedTimerListener != null) {
                    val index = timerListeners.indexOf(finishedTimerListener)

                    unsubscribeFromTimer(finishedTimerListener)
                    finishedTimerListener.onFinished(index)
                }

                if(stoppedTimerListener != null) {
                    val index = timerListeners.indexOf(stoppedTimerListener)

                    unsubscribeFromTimer(stoppedTimerListener)
                    stoppedTimerListener.onFinished(index)
                }
            }

            override fun onFinish() {
                //ignore
            }
        }
    }

    fun resetTimer() {
        for(timerListener in timerListeners) {
            unsubscribeFromTimer(timerListener)
        }
    }

    fun subscribeToTimer(quiteTimeTimerListener: QuiteTimeTimerListener) {
        timerListeners.add(quiteTimeTimerListener)
        if (timerListeners.size == 1) {
            timer.start()
        }
    }

    fun unsubscribeFromTimer(quiteTimeTimerListener: QuiteTimeTimerListener) {
        if (timerListeners.size == 1) {
            timer.cancel()
        }
        timerListeners.remove(quiteTimeTimerListener)
    }
}