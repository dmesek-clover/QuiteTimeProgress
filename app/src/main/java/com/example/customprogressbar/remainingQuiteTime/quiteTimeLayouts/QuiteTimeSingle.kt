package com.example.customprogressbar.remainingQuiteTime.quiteTimeLayouts

import android.content.Context
import android.widget.LinearLayout
import com.example.customprogressbar.remainingQuiteTime.LayoutChangedListener
import com.example.customprogressbar.remainingQuiteTime.models.RemainingQuiteTime

class QuiteTimeSingle(
        context: Context,
        root: LinearLayout,
        layoutProvider: LayoutChangedListener,
        remainingQuiteTimeList: ArrayList<RemainingQuiteTime>
) : QuiteTimeLayout(context, root, layoutProvider, remainingQuiteTimeList) {

    override fun detachLayout() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addedSingle(remainingQuiteTime: RemainingQuiteTime) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addedMultiple(remainingQuiteTime: List<RemainingQuiteTime>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}