package com.example.customprogressbar.remainingQuiteTime.quiteTimeLayouts

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import com.example.customprogressbar.remainingQuiteTime.models.RemainingQuiteTime

class QuiteTimeEmpty(
        context: Context,
        root: LinearLayout
) : QuiteTimeLayout(context, root) {

    init {
        root.visibility = View.GONE
    }

    override fun detachLayout() {
        //ignore
    }

    override fun add(remainingQuiteTime: RemainingQuiteTime) {
        //ignore
    }

    override fun addAll(remainingQuiteTime: List<RemainingQuiteTime>) {
        //ignore
    }

}