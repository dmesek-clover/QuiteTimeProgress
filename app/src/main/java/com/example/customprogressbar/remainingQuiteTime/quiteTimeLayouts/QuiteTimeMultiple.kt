package com.example.customprogressbar.remainingQuiteTime.quiteTimeLayouts

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.customprogressbar.R
import com.example.customprogressbar.remainingQuiteTime.*
import com.example.customprogressbar.remainingQuiteTime.dialogs.EndQuiteTimeDialog
import com.example.customprogressbar.remainingQuiteTime.dialogs.EndQuiteTimeDialogListener
import com.example.customprogressbar.remainingQuiteTime.models.QuiteTimeUser
import com.example.customprogressbar.remainingQuiteTime.models.RemainingQuiteTime


class QuiteTimeMultiple(
        context: Context,
        root: LinearLayout,
        layoutListener: LayoutChangedListener,
        remainingQuiteTimeList: ArrayList<RemainingQuiteTime>
) : QuiteTimeLayout(context, root, layoutListener, remainingQuiteTimeList) {

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
        layoutInflater.inflate(R.layout.quite_time_multiple_remaining_header, null).also {
            root.addView(it)
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
        val stopImage = view.findViewById<ImageView>(R.id.btn_stop_quite_time)

        val timerListener = createTimerListener(remainingQuiteTime, quiteTimeRemaining)
        QuiteTimeTimer.subscribeToTimer(timerListener)

        val gridLayout = QuiteTimeGridUserLayout(context, gridUsers,
                quiteTimeRemaining.quiteTimeUsers, R.layout.quite_time_user_item)

        stopImage.setup(quiteTimeRemaining, gridLayout)
    }

    private fun ImageView.setup(quiteTimeRemaining: RemainingQuiteTime, gridLayout: QuiteTimeGridUserLayout) {
        setOnClickListener {
            if (quiteTimeRemaining.quiteTimeUsers.size == 1) {
                quiteTimeRemaining.stopped = true
            } else {
                EndQuiteTimeDialog(quiteTimeRemaining.quiteTimeUsers, object : EndQuiteTimeDialogListener {
                    override fun removePressed(quiteTimeUser: QuiteTimeUser) {
                        gridLayout.deleteQuiteTimeUser(quiteTimeUser)
                    }

                }).show(( context as AppCompatActivity).supportFragmentManager, "EndQuiteTimeDialog")
                //TODO: add listener
            }
        }
    }

    private fun createTimerListener(remainingQuiteTime: TextView, quiteTimeRemaining: RemainingQuiteTime) = object : QuiteTimeTimerListener {

        override fun onTick() {
            quiteTimeRemaining.decrementSecondsRemainig()

            remainingQuiteTime.text = TimeFormatter.formatRemainingSecondsHHmmSS(quiteTimeRemaining.secondsRemaining)
        }

        override fun onFinished(index: Int) {
            remainingQuiteTimeList.remove(quiteTimeRemaining)
            root.removeViewAt(index + 1)
            layoutListener.layoutChanged()
        }

        override fun isFinished() = quiteTimeRemaining.isFinished

        override fun isStopped() = quiteTimeRemaining.stopped


    }


}

