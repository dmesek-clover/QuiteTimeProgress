package com.example.customprogressbar.remainingQuiteTime.quiteTimeLayouts

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.customprogressbar.remainingQuiteTime.LayoutChangedListener
import com.example.customprogressbar.remainingQuiteTime.QuiteTimeLayoutProvider
import com.example.customprogressbar.remainingQuiteTime.QuiteTimeTimer
import com.example.customprogressbar.remainingQuiteTime.models.RemainingQuiteTime

class QuiteTimeEmpty(
        context: AppCompatActivity,
        root: LinearLayout,
        layoutProvider: LayoutChangedListener,
        remainingQuiteTimeList: ArrayList<RemainingQuiteTime>
) : QuiteTimeLayout(context, root, layoutProvider, remainingQuiteTimeList) {

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