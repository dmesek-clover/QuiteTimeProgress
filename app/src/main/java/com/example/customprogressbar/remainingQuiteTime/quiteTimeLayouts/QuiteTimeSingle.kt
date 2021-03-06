package com.example.customprogressbar.remainingQuiteTime.quiteTimeLayouts

import android.content.Context
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.customprogressbar.R
import com.example.customprogressbar.remainingQuiteTime.*
import com.example.customprogressbar.remainingQuiteTime.dialogs.QuiteTimeRemoved
import com.example.customprogressbar.remainingQuiteTime.dialogs.QuiteTimeRemovedListener
import com.example.customprogressbar.remainingQuiteTime.models.QuiteTimeUser
import com.example.customprogressbar.remainingQuiteTime.models.RemainingQuiteTime

//is not used right now
class QuiteTimeSingle(
        context: Context,
        root: LinearLayout,
        layoutProvider: LayoutChangedListener,
        remainingQuiteTimeList: ArrayList<RemainingQuiteTime>
) : QuiteTimeLayout(context, root, layoutProvider, remainingQuiteTimeList) {

    private val layoutInflater = LayoutInflater.from(context)

    init {
        root.visibility = View.VISIBLE
        buildSingleUser(remainingQuiteTimeList[0]) //the list is surely the size of 1
    }

    override fun detachLayout() {
        QuiteTimeTimer.resetTimer()
    }

    override fun addedSingle(remainingQuiteTime: RemainingQuiteTime) {
        //ignore
    }

    override fun addedMultiple(remainingQuiteTime: List<RemainingQuiteTime>) {
        //ignore
    }

    private fun buildSingleUser(remainingQuiteTime: RemainingQuiteTime) {
        val view = layoutInflater.inflate(R.layout.quite_time_single_remaining_item, null)
        populateView(view, remainingQuiteTime)
        root.addView(view)
    }

    private fun populateView(view: View, quiteTimeRemaining: RemainingQuiteTime) {
        val gridUsers = view.findViewById<GridLayout>(R.id.gv_quite_time_users)
        val remainingQuiteTime = view.findViewById<TextView>(R.id.tv_remaining_quite_time)
        val stopButton = view.findViewById<ImageView>(R.id.btn_stop_quite_time)

        val timerListener = createTimerListener(remainingQuiteTime, quiteTimeRemaining)
        QuiteTimeTimer.subscribeToTimer(timerListener)

        val gridLayout = QuiteTimeGridUserLayout(context, gridUsers,
                quiteTimeRemaining.quiteTimeUsers, R.layout.quite_time_user_item)

        stopButton.setOnClickListener {
            if (quiteTimeRemaining.quiteTimeUsers.size == 1) {
                quiteTimeRemaining.stopped = true
            } else {
                QuiteTimeRemoved(quiteTimeRemaining.quiteTimeUsers, object : QuiteTimeRemovedListener {
                    override fun removePressed(quiteTimeUser: QuiteTimeUser) {
                        gridLayout.deleteQuiteTimeUser(quiteTimeUser)
                    }

                }).show(( context as AppCompatActivity).supportFragmentManager, "EndQuiteTimeDialog")
                //TODO: notify listener
            }
        }
    }

    private fun createTimerListener(remainingQuiteTime: TextView, quiteTimeRemaining: RemainingQuiteTime) = object : QuiteTimeTimerListener {

        var allowModification = true

        override fun onTick() {
            if (allowModification) {
                quiteTimeRemaining.decrementSecondsRemainig()
                remainingQuiteTime.text = TimeFormatter.formatRemainingSecondsHHmmSS(quiteTimeRemaining.secondsRemaining)
                allowModification = false
                wait500ms()
            }
        }

        //if this is not added, some problems occur when switching from multiple to single layout
        private fun wait500ms() {
            object : CountDownTimer(500, 500) {
                override fun onFinish() {
                    allowModification = true
                }

                override fun onTick(p0: Long) {
                    //ignore
                }

            }.start()
        }

        override fun onFinished(index: Int) {
            remainingQuiteTimeList.remove(quiteTimeRemaining)
            layoutListener.layoutChanged()
        }

        override fun isFinished() = quiteTimeRemaining.isFinished

        override fun isStopped() = quiteTimeRemaining.stopped

    }

}