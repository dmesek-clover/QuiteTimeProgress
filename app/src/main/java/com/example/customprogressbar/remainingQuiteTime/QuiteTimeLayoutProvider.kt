package com.example.customprogressbar.remainingQuiteTime

import android.content.Context
import android.widget.LinearLayout
import com.example.customprogressbar.remainingQuiteTime.models.LayoutProviderState
import com.example.customprogressbar.remainingQuiteTime.models.RemainingQuiteTime
import com.example.customprogressbar.remainingQuiteTime.quiteTimeLayouts.QuiteTimeEmpty
import com.example.customprogressbar.remainingQuiteTime.quiteTimeLayouts.QuiteTimeLayout
import com.example.customprogressbar.remainingQuiteTime.quiteTimeLayouts.QuiteTimeMultiple
import com.example.customprogressbar.remainingQuiteTime.quiteTimeLayouts.QuiteTimeSingle

interface LayoutChangedListener{
    fun itemRemoved()
}

class QuiteTimeLayoutProvider (
        private val context: Context,
        private val root: LinearLayout
) : LayoutChangedListener{

    private val quiteTimeRemainingList = arrayListOf<RemainingQuiteTime>()

    private var currentLayoutProviderState = LayoutProviderState.EMPTY
    private var currentLayout: QuiteTimeLayout = QuiteTimeEmpty(context, root, this, quiteTimeRemainingList)

    fun addQuiteTimeRemaining(element: RemainingQuiteTime) {
        quiteTimeRemainingList.add(element)
        currentLayout.addedSingle(element)
        quiteTimeRemainingListChanged()

    }

    fun addAllQuiteTimeRemaining(list: List<RemainingQuiteTime>) {
        quiteTimeRemainingList.addAll(list)
        currentLayout.addedMultiple(list)
        quiteTimeRemainingListChanged()

    }

    override fun itemRemoved() {
        quiteTimeRemainingListChanged()
    }

    private fun quiteTimeRemainingListChanged() {
        currentLayoutProviderState.apply {
            val newState = LayoutProviderState.fromInt(quiteTimeRemainingList.size)

            if(newState != this) {
                currentLayoutProviderState = newState
                layoutProviderStateChanged()
            }
        }
    }

    private fun layoutProviderStateChanged() {
        currentLayout.detachLayout()
        root.removeAllViews()

        currentLayout = when(currentLayoutProviderState) {
            LayoutProviderState.EMPTY -> QuiteTimeEmpty(context, root, this, quiteTimeRemainingList)
            LayoutProviderState.SINGLE -> QuiteTimeSingle(context, root, this, quiteTimeRemainingList)
            LayoutProviderState.MULTIPLE -> QuiteTimeMultiple(context, root, this, quiteTimeRemainingList)
        }
    }

}