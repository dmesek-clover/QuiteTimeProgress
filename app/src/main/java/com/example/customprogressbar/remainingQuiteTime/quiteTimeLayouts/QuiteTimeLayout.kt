package com.example.customprogressbar.remainingQuiteTime.quiteTimeLayouts

import android.content.Context
import android.widget.LinearLayout
import com.example.customprogressbar.remainingQuiteTime.LayoutChangedListener
import com.example.customprogressbar.remainingQuiteTime.models.RemainingQuiteTime

abstract class QuiteTimeLayout(
        protected val context: Context,
        protected val root: LinearLayout,
        protected val layoutListener: LayoutChangedListener,
        protected val remainingQuiteTimeList: ArrayList<RemainingQuiteTime>
) {

    abstract fun detachLayout()
    abstract fun addedSingle(remainingQuiteTime: RemainingQuiteTime)
    abstract fun addedMultiple(remainingQuiteTime: List<RemainingQuiteTime>)
}