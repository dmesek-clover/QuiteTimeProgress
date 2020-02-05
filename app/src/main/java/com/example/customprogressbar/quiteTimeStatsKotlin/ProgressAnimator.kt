package com.example.customprogressbar.quiteTimeStatsKotlin

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.view.animation.DecelerateInterpolator
import android.widget.ProgressBar
import com.example.customprogressbar.quiteTimeStatsKotlin.models.QuiteTime
import java.util.*

internal class ProgressAnimator(
        private val progressBars: List<ProgressBar>,
        private val quiteTimeInfo: List<QuiteTime>) {

    private val animationDuration = 2000 //ms
    private val progressAnimators = ArrayList<DayOfWeekProgress>()

    init {
        initializeProgressAnimators()
    }

    //start the chain reaction
    fun startAnimation() {
        if (!progressAnimators.isEmpty()) {
            progressAnimators[0].startAnimation()
        }
    }

    private fun initializeProgressAnimators() {
        for ((progressBarIndex, progressBar) in progressBars.withIndex()) {
            val progress = quiteTimeInfo[progressBarIndex].usedAmount
            progressAnimators.add(DayOfWeekProgress(progressBar, progress))
        }

        for (animatorIndex in progressAnimators.indices) {
            try {
                progressAnimators[animatorIndex].setNextAnimator(progressAnimators[animatorIndex + 1])
            } catch (ignore: IndexOutOfBoundsException) {
            }

        }
    }


    private inner class DayOfWeekProgress(
            private val dayOfWeekProgressBar: ProgressBar,
            initialProgress: Int) : CustomAnimationListener {

        private var nextProgressBar: CustomAnimationListener? = null
        private var objectAnimator: ObjectAnimator = initializeObjectAnimator(initialProgress)
        //used by ObjectAnimator
        private var progress: Int = 0
            set(progress) {
                field = progress
                dayOfWeekProgressBar.progress = progress
            }


        internal fun setNextAnimator(nextProgressBar: CustomAnimationListener) {
            this.nextProgressBar = nextProgressBar
        }

        internal fun startAnimation() {
            objectAnimator.start()
        }

        override fun onCustomStart() {
            objectAnimator.start()
        }

        private fun initializeObjectAnimator(progress: Int): ObjectAnimator {
            val objectAnimator = ObjectAnimator.ofInt(this, "progress", progress)
            objectAnimator.apply {
                duration = animationDuration.toLong()
                interpolator = DecelerateInterpolator()

                addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        //nothing for now
                    }

                    override fun onAnimationStart(animation: Animator) {
                        super.onAnimationStart(animation)
                        if (nextProgressBar != null) {
                            nextProgressBar!!.onCustomStart()
                        }
                    }
                })
            }
            return objectAnimator
        }

    }

}

internal interface CustomAnimationListener {
    fun onCustomStart()
}
