package com.example.customprogressbar.remainingQuiteTime

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.example.customprogressbar.remainingQuiteTime.models.LayoutProviderState
import com.example.customprogressbar.remainingQuiteTime.models.RemainingQuiteTime
import com.example.customprogressbar.remainingQuiteTime.quiteTimeLayouts.QuiteTimeEmpty
import com.example.customprogressbar.remainingQuiteTime.quiteTimeLayouts.QuiteTimeLayout
import com.example.customprogressbar.remainingQuiteTime.quiteTimeLayouts.QuiteTimeMultiple
import com.example.customprogressbar.remainingQuiteTime.quiteTimeLayouts.QuiteTimeSingle

interface LayoutChangedListener {
    fun layoutChanged()
}

class QuiteTimeGroupTimer(
        private val ctx: Context,
        attrs: AttributeSet?
) : LinearLayout(ctx, attrs), LayoutChangedListener {

    private val quiteTimeRemainingList = arrayListOf<RemainingQuiteTime>()

    private var currentLayoutProviderState = LayoutProviderState.EMPTY
    private var currentLayout: QuiteTimeLayout = QuiteTimeEmpty(ctx, this, this, quiteTimeRemainingList)

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

    override fun layoutChanged() {
        quiteTimeRemainingListChanged()
    }

    private fun quiteTimeRemainingListChanged() {
        currentLayoutProviderState.apply {
            val newState = LayoutProviderState.fromInt(quiteTimeRemainingList.size)

            if (newState != this) {
                currentLayoutProviderState = newState
                layoutProviderStateChanged()
            }
        }
    }

    private fun layoutProviderStateChanged() {
        currentLayout.detachLayout()
        removeAllViews()

        currentLayout = when (currentLayoutProviderState) {
            LayoutProviderState.EMPTY -> QuiteTimeEmpty(ctx, this, this, quiteTimeRemainingList)
            LayoutProviderState.SINGLE -> QuiteTimeSingle(ctx, this, this, quiteTimeRemainingList)
            LayoutProviderState.MULTIPLE -> QuiteTimeMultiple(ctx, this, this, quiteTimeRemainingList)
        }
    }

}