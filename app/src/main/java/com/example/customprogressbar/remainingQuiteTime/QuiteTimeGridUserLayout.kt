package com.example.customprogressbar.remainingQuiteTime

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView

import com.example.customprogressbar.R
import com.example.customprogressbar.remainingQuiteTime.models.QuiteTimeUser

internal class QuiteTimeGridUserLayout(
        context: Context,
        private val root: GridLayout,
        private val quiteTimeUsers: List<QuiteTimeUser>,
        private val layoutId: Int
) {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    init {
        updateData()
    }

    @SuppressLint("InflateParams")
    private fun updateData() {
        root.removeAllViews()
        for (quiteTimeUser in quiteTimeUsers) {
            val view = layoutInflater.inflate(layoutId, null)
            populateView(view, quiteTimeUser)
            attachToLinearLayout(view)
        }
    }

    private fun populateView(view: View, quiteTimeUser: QuiteTimeUser) {
        val userName = view.findViewById<TextView>(R.id.tv_quite_time_user_name)
        val userIcon = view.findViewById<ImageView>(R.id.iv_quite_time_user_icon)

        userIcon.setImageDrawable(quiteTimeUser.icon)
        userName.text = quiteTimeUser.name
    }

    private fun attachToLinearLayout(view: View) {
        root.addView(view)
    }


}