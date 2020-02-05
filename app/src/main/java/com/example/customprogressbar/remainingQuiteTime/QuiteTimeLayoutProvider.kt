package com.example.customprogressbar.remainingQuiteTime

import android.content.Context
import android.widget.LinearLayout
import com.example.customprogressbar.remainingQuiteTime.models.LayoutProviderState
import com.example.customprogressbar.remainingQuiteTime.models.RemainingQuiteTime
import com.example.customprogressbar.remainingQuiteTime.quiteTimeLayouts.QuiteTimeEmpty
import com.example.customprogressbar.remainingQuiteTime.quiteTimeLayouts.QuiteTimeLayout
import com.example.customprogressbar.remainingQuiteTime.quiteTimeLayouts.QuiteTimeMultiple

interface LayoutChangedListener{
    fun itemRemoved()
}

class QuiteTimeLayoutProvider (
        private val context: Context,
        private val root: LinearLayout
) : LayoutChangedListener{

    private val quiteTimeRemainingList = arrayListOf<RemainingQuiteTime>()

    private var currentLayoutProviderState = LayoutProviderState.MULTIPLE
    private var currentLayout: QuiteTimeLayout = QuiteTimeMultiple(context, root, this, quiteTimeRemainingList)

    fun addQuiteTimeRemaining(element: RemainingQuiteTime) {
        quiteTimeRemainingList.add(element)
        //quiteTimeRemainingListChanged(increment = 1)
        currentLayout.addedSingle(element)
    }

    fun addAllQuiteTimeRemaining(list: List<RemainingQuiteTime>) {
        quiteTimeRemainingList.addAll(list)
        //quiteTimeRemainingListChanged(increment = list.size)
        currentLayout.addedMultiple(list)
    }

    override fun itemRemoved() {
        //quiteTimeRemainingListChanged(increment = null)
    }

    private fun quiteTimeRemainingListChanged(increment: Int?) {
        currentLayoutProviderState.apply {
            val newState = if(increment == null) {
                decrementItemCount()
            } else {
                incrementItemCountBy(increment)
            }

            if(newState != this) {
                currentLayoutProviderState = newState
                layoutProviderStateChanged()
            }
        }
    }

    private fun layoutProviderStateChanged() {
        currentLayout.detachLayout()

        currentLayout = when(currentLayoutProviderState) {
            LayoutProviderState.EMPTY -> QuiteTimeEmpty(context, root, this, quiteTimeRemainingList)
            LayoutProviderState.SINGLE -> QuiteTimeMultiple(context, root, this, quiteTimeRemainingList)
            LayoutProviderState.MULTIPLE -> QuiteTimeMultiple(context, root, this, quiteTimeRemainingList)
        }
    }

}