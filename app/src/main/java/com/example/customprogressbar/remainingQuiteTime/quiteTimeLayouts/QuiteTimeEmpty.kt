package com.example.customprogressbar.remainingQuiteTime.quiteTimeLayouts

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import com.example.customprogressbar.remainingQuiteTime.LayoutChangedListener
import com.example.customprogressbar.remainingQuiteTime.models.RemainingQuiteTime

class QuiteTimeEmpty(
        context: Context,
        root: LinearLayout,
        layoutListener: LayoutChangedListener,
        remainingQuiteTimeList: ArrayList<RemainingQuiteTime>
) : QuiteTimeLayout(context, root, layoutListener, remainingQuiteTimeList) {

    init {
        //implementation for now
        root.visibility = View.GONE
    }

    override fun detachLayout() {
        //ignore
    }

    override fun addedSingle(remainingQuiteTime: RemainingQuiteTime) {
        //ignore
    }

    override fun addedMultiple(remainingQuiteTime: List<RemainingQuiteTime>) {
        //ignore
    }

}