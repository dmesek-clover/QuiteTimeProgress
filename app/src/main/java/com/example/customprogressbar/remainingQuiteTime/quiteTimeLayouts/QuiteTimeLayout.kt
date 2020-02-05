package com.example.customprogressbar.remainingQuiteTime.quiteTimeLayouts

import android.content.Context
import android.widget.LinearLayout
import com.example.customprogressbar.remainingQuiteTime.models.RemainingQuiteTime

abstract class QuiteTimeLayout(
        private val context: Context,
        private val root: LinearLayout
) {

    abstract fun detachLayout()
    abstract fun add(remainingQuiteTime: RemainingQuiteTime)
    abstract fun addAll(remainingQuiteTime: List<RemainingQuiteTime>)
}