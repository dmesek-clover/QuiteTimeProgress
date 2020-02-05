package com.example.customprogressbar.remainingQuiteTime.quiteTimeLayouts

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.customprogressbar.R
import com.example.customprogressbar.remainingQuiteTime.LayoutChangedListener
import com.example.customprogressbar.remainingQuiteTime.QuiteTimeGridUserLayout
import com.example.customprogressbar.remainingQuiteTime.QuiteTimeTimer
import com.example.customprogressbar.remainingQuiteTime.QuiteTimeTimerListener
import com.example.customprogressbar.remainingQuiteTime.models.RemainingQuiteTime

class QuiteTimeMultiple(
        context: Context,
        root: LinearLayout,
        layoutProvider: LayoutChangedListener,
        remainingQuiteTimeList: ArrayList<RemainingQuiteTime>
) : QuiteTimeLayout(context, root, layoutProvider, remainingQuiteTimeList) {

    private val layoutInflater = LayoutInflater.from(context)
    private val timerListeners = arrayListOf<QuiteTimeTimerListener>()

    init {
        root.visibility = View.VISIBLE
        buildMultipleUsers(remainingQuiteTimeList)
    }

    override fun addedSingle(remainingQuiteTime: RemainingQuiteTime) {
        addedMultiple(listOf(remainingQuiteTime))
    }

    override fun addedMultiple(remainingQuiteTime: List<RemainingQuiteTime>) {
        buildMultipleUsers(remainingQuiteTime)
    }

    override fun detachLayout() {
        for (timerListener in timerListeners) {
            QuiteTimeTimer.unsubscribeFromTimer(timerListener)
        }
    }

    private fun buildMultipleUsers(list: List<RemainingQuiteTime>) {
        for (quiteTime in list) {
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
        timerListeners.add(timerListener)
        QuiteTimeTimer.subscribeToTimer(timerListener)

        QuiteTimeGridUserLayout(context, gridUsers, quiteTimeRemaining.quiteTimeUsers)

        stopButton.setOnClickListener {
            if (quiteTimeRemaining.quiteTimeUsers.size == 1) {
                deleteQuiteTime(quiteTimeRemaining, timerListener, view)
            } else {
                //show dialog
            }
        }
    }

    private fun deleteQuiteTime(quiteTimeRemaining: RemainingQuiteTime, timerListener: QuiteTimeTimerListener, view: View) {
        remainingQuiteTimeList.remove(quiteTimeRemaining)
        QuiteTimeTimer.unsubscribeFromTimer(timerListener)
        timerListeners.remove(timerListener)
        root.removeView(view)
        layoutProvider.itemRemoved()
    }

    private fun createTimerListener(remainingQuiteTime: TextView, quiteTimeRemaining: RemainingQuiteTime) = object : QuiteTimeTimerListener {
        override fun onTick(): Int? {
            var shouldRemoveIndex: Int? = null

            quiteTimeRemaining.decrementSecondsRemainig()
            remainingQuiteTime.text = quiteTimeRemaining.formattedTimeRemaining
            if (quiteTimeRemaining.isFinished) {
                val quitePosition = remainingQuiteTimeList.indexOf(quiteTimeRemaining)
                remainingQuiteTimeList.remove(quiteTimeRemaining)
                root.removeViewAt(quitePosition)
                shouldRemoveIndex = quitePosition
            }

            return shouldRemoveIndex
        }

    }


}