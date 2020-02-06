package com.example.customprogressbar.remainingQuiteTime.quiteTimeLayouts

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.customprogressbar.R
import com.example.customprogressbar.remainingQuiteTime.*
import com.example.customprogressbar.remainingQuiteTime.QuiteTimeGridUserLayout
import com.example.customprogressbar.remainingQuiteTime.models.RemainingQuiteTime

class QuiteTimeMultiple(
        context: Context,
        root: LinearLayout,
        layoutProvider: LayoutChangedListener,
        remainingQuiteTimeList: ArrayList<RemainingQuiteTime>
) : QuiteTimeLayout(context, root, layoutProvider, remainingQuiteTimeList) {

    private val layoutInflater = LayoutInflater.from(context)

    init {
        createHeader()
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
        QuiteTimeTimer.resetTimer()
    }

    private fun createHeader() {
        val textView = TextView(context)
        textView.gravity = Gravity.CENTER
        textView.textSize = 20f
        textView.text = "Quite Time ends in:"
        root.addView(textView)
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
        QuiteTimeTimer.subscribeToTimer(timerListener)

        QuiteTimeGridUserLayout(context, gridUsers, quiteTimeRemaining.quiteTimeUsers, R.layout.quite_time_multiple_user_item)

        stopButton.setOnClickListener {
            if (quiteTimeRemaining.quiteTimeUsers.size == 1) {
                quiteTimeRemaining.stopped = true
            } else {
                //
            }
        }
    }


    private fun createTimerListener(remainingQuiteTime: TextView, quiteTimeRemaining: RemainingQuiteTime) = object : QuiteTimeTimerListener {

        override fun onTick() {
            quiteTimeRemaining.decrementSecondsRemainig()
            remainingQuiteTime.text = quiteTimeRemaining.formattedTimeRemaining
        }

        override fun onFinished(index: Int) {
            remainingQuiteTimeList.remove(quiteTimeRemaining)
            root.removeViewAt(index + 1)
            layoutProvider.itemRemoved()
        }

        override fun isFinished() = quiteTimeRemaining.isFinished

        override fun isStopped() = quiteTimeRemaining.stopped


    }


}