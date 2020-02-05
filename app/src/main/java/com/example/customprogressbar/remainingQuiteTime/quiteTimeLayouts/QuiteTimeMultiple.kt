package com.example.customprogressbar.remainingQuiteTime.quiteTimeLayouts

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.customprogressbar.R
import com.example.customprogressbar.remainingQuiteTime.QuiteTimeTimer
import com.example.customprogressbar.remainingQuiteTime.QuiteTimeTimerListener
import com.example.customprogressbar.remainingQuiteTime.QuiteTimeGridUserLayout
import com.example.customprogressbar.remainingQuiteTime.models.RemainingQuiteTime

class QuiteTimeMultiple(
        private val context: Context,
        private val root: LinearLayout
) {

    private val layoutInflater = LayoutInflater.from(context)
    private val quiteTimeRemainingList = arrayListOf<RemainingQuiteTime>()

    fun addQuiteTimeRemaining(element: RemainingQuiteTime) {
        addAllQuiteTimeRemaining(listOf(element))
    }

    fun addAllQuiteTimeRemaining(list: List<RemainingQuiteTime>) {
        quiteTimeRemainingList.addAll(list)
        buildMultipleUsers(list)
    }

    private fun buildMultipleUsers(list: List<RemainingQuiteTime>) {
       for(quiteTime in list) {
           val view = layoutInflater.inflate(R.layout.quite_time_multiple_remaining_item, null)
           populateView(view, quiteTime)
           root.addView(view)
       }
    }

    private fun populateView(view: View, quiteTimeRemaining: RemainingQuiteTime) {
        val gridUsers = view.findViewById<GridLayout>(R.id.gv_quite_time_users)
        val remainingQuiteTime = view.findViewById<TextView>(R.id.tv_remaining_quite_time)
        val stopButton = view.findViewById<ImageView>(R.id.btn_stop_quite_time)

        val timerListener = createTimerListener(remainingQuiteTime, quiteTimeRemaining)
        QuiteTimeTimer.subscribeToTimer(timerListener)

        QuiteTimeGridUserLayout(context, gridUsers, quiteTimeRemaining.quiteTimeUsers)

        stopButton.setOnClickListener {
            if(quiteTimeRemaining.quiteTimeUsers.size == 1) {
                quiteTimeRemainingList.remove(quiteTimeRemaining)
                QuiteTimeTimer.unsubscribeFromTimer(timerListener)
                root.removeView(view)
            } else {
                //show dialog
            }
        }
    }

    private fun createTimerListener(remainingQuiteTime: TextView, quiteTimeRemaining: RemainingQuiteTime) = object: QuiteTimeTimerListener {
        override fun onTick(): Int? {
            var shouldRemoveIndex: Int? = null

            quiteTimeRemaining.decrementSecondsRemainig()
            remainingQuiteTime.text = quiteTimeRemaining.formattedTimeRemaining
            if (quiteTimeRemaining.isFinished) {
                val quitePosition = quiteTimeRemainingList.indexOf(quiteTimeRemaining)
                quiteTimeRemainingList.remove(quiteTimeRemaining)
                root.removeViewAt(quitePosition)
                shouldRemoveIndex = quitePosition
            }

            return shouldRemoveIndex
        }

    }


}