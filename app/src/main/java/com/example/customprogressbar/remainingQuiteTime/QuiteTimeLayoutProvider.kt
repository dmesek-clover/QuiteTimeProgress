package com.example.customprogressbar.remainingQuiteTime

import android.content.Context
import android.widget.LinearLayout
import com.example.customprogressbar.remainingQuiteTime.models.LayoutProviderState
import com.example.customprogressbar.remainingQuiteTime.models.RemainingQuiteTime


interface LayoutChangedListener{
    fun itemRemovedWith(index: Int)
}

class QuiteTimeLayoutProvider (
        private val context: Context,
        private val root: LinearLayout
) : LayoutChangedListener{

    private val quiteTimeRemainingList = arrayListOf<RemainingQuiteTime>()

    private var currentLayoutProviderState = LayoutProviderState.EMPTY

    fun addQuiteTimeRemaining(element: RemainingQuiteTime) {
        addAllQuiteTimeRemaining(listOf(element))
    }

    fun addAllQuiteTimeRemaining(list: List<RemainingQuiteTime>) {
        quiteTimeRemainingList.addAll(list)
        quiteTimeRemainingListChanged(increment = list.size)
    }

    override fun itemRemovedWith(index: Int) {
        quiteTimeRemainingList.removeAt(index)
        quiteTimeRemainingListChanged(increment = null)
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

    }

}