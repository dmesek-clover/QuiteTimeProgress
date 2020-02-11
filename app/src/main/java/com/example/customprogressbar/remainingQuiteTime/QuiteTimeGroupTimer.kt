package com.example.customprogressbar.remainingQuiteTime

import android.app.Activity
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.customprogressbar.remainingQuiteTime.models.LayoutProviderState
import com.example.customprogressbar.remainingQuiteTime.models.RemainingQuiteTime
import com.example.customprogressbar.remainingQuiteTime.quiteTimeLayouts.QuiteTimeEmpty
import com.example.customprogressbar.remainingQuiteTime.quiteTimeLayouts.QuiteTimeLayout
import com.example.customprogressbar.remainingQuiteTime.quiteTimeLayouts.QuiteTimeMultiple
import com.example.customprogressbar.remainingQuiteTime.quiteTimeLayouts.QuiteTimeSingle

interface LayoutChangedListener{
    fun itemRemoved()
}

class QuiteTimeGroupTimer (
        private val context: AppCompatActivity,
        private val root: LinearLayout,
        attrs: AttributeSet?
) : ConstraintLayout(context, attrs), LayoutChangedListener{

    private val quiteTimeRemainingList = arrayListOf<RemainingQuiteTime>()

    private var currentLayoutProviderState = LayoutProviderState.EMPTY
    private var currentLayout: QuiteTimeLayout = QuiteTimeEmpty(context, root, this, quiteTimeRemainingList)

    init {

    }

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