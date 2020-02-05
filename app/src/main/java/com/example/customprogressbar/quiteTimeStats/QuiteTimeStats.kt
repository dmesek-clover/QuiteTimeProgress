package com.example.customprogressbar.quiteTimeStats

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.animation.DecelerateInterpolator
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

import com.example.customprogressbar.R
import com.example.customprogressbar.quiteTimeStats.models.DayOfWeek
import com.example.customprogressbar.quiteTimeStats.models.QuiteTime

import java.util.ArrayList

class QuiteTimeStats(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {

    companion object {

        private val daysOfWeek = listOf(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY,
                DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY)

        private const val NUMBER_OF_DAYS_IN_WEEK = 7
        private const val DEFAULT_MAX = 120
        private const val DEFAULT_PROGRESS = 0
        private var animationDuration = 2000 //ms changed after first animation to 700ms
    }

    private var previousWeekQuiteTime: List<QuiteTime>? = null
    private var todayQuiteTimeProgress: Int = 0

    //used by the ObjectAnimator
    private var todayIncrementProgress: Int = 0
        set(todayIncrementProgress) {
            field = todayIncrementProgress
            updateTodayData()
        }

    private var todayDayOfWeek: DayOfWeek? = null
    private var todayMaxProgress = DEFAULT_MAX

    private var todayProgressBar: ProgressBar? = null
    private var todayProgressText: TextView? = null
    private var todayMaxProgressText: TextView? = null
    private var previousWeekProgressBars: List<ConstraintLayout>? = null

    init {
        inflate(getContext(), R.layout.quite_time_stats, this)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        val firstProgressBar = findViewById<ConstraintLayout>(R.id.pb_quite_time_first)
        val secondProgressBar = findViewById<ConstraintLayout>(R.id.pb_quite_time_second)
        val thirdProgressBar = findViewById<ConstraintLayout>(R.id.pb_quite_time_third)
        val fourthProgressBar = findViewById<ConstraintLayout>(R.id.pb_quite_time_fourth)
        val fifthProgressBar = findViewById<ConstraintLayout>(R.id.pb_quite_time_fifth)
        val sixthProgressBar = findViewById<ConstraintLayout>(R.id.pb_quite_time_sixth)
        val seventhProgressBar = findViewById<ConstraintLayout>(R.id.pb_quite_time_seventh)

        todayProgressBar = findViewById(R.id.pb_quite_time_today)

        previousWeekProgressBars = listOf(seventhProgressBar, sixthProgressBar, fifthProgressBar,
                fourthProgressBar, thirdProgressBar, secondProgressBar, firstProgressBar)
        todayProgressText = findViewById(R.id.tv_today_progress)
        todayMaxProgressText = findViewById(R.id.tv_max_progress)
    }

    fun setTodayDayOfWeek(todayDayOfWeek: DayOfWeek) {
        this.todayDayOfWeek = todayDayOfWeek
        initializeTodayData()
    }

    fun setTodayMaxProgress(todayMaxProgress: Int) {
        this.todayMaxProgress = todayMaxProgress
    }

    fun setPreviousWeekQuiteTime(previousWeekQuiteTime: List<QuiteTime>) {
        this.previousWeekQuiteTime = previousWeekQuiteTime
        initializePreviousWeekData()
    }

    fun updateTodayProgress(progress: Int) {
        val objectAnimator = ObjectAnimator.ofInt(this,
                "todayIncrementProgress", todayQuiteTimeProgress, progress)
        objectAnimator.duration = animationDuration.toLong()
        objectAnimator.interpolator = DecelerateInterpolator()
        objectAnimator.start()
        animationDuration = 700
    }


    private fun initializeTodayData() {
        todayProgressBar!!.max = todayMaxProgress
        val placeholder = context.getString(R.string.max_progress_today)
        todayMaxProgressText!!.text = String.format(placeholder, todayMaxProgress)
    }

    //used by ObjectAnimator
    private fun updateTodayData() {
        val difference = this.todayIncrementProgress - todayQuiteTimeProgress
        todayProgressBar!!.incrementProgressBy(difference)

        todayQuiteTimeProgress = this.todayIncrementProgress
        todayProgressText!!.text = formatUsedAmount(todayQuiteTimeProgress)
    }

    private fun formatUsedAmount(usedAmount: Int): String {
        return usedAmount.toString() + "m"
    }

    private fun initializePreviousWeekData() {
        if (previousWeekQuiteTime == null) {
            previousWeekQuiteTime = ArrayList()
        }
        require(previousWeekQuiteTime!!.size <= NUMBER_OF_DAYS_IN_WEEK) { "previousWeekQuiteTime list should contain at most $NUMBER_OF_DAYS_IN_WEEK items." }
        checkNotNull(todayDayOfWeek) { "Please call setTodayDayOfWeek(DayOfWeek dayOfWeek)" + " before calling setPreviousWeekQuiteTime method." }

        setDataForProgressBars()
    }

    private fun setDataForProgressBars() {
        var currentDayOfWeek = todayDayOfWeek
        var quiteTime: QuiteTime
        for (index in 0 until NUMBER_OF_DAYS_IN_WEEK) {
            val previousDayOfWeek = getDayOfWeekBefore(currentDayOfWeek)
            if (index >= previousWeekQuiteTime!!.size) {
                quiteTime = QuiteTime(DEFAULT_MAX, DEFAULT_PROGRESS, previousDayOfWeek)
            } else {
                quiteTime = previousWeekQuiteTime!![index]
                quiteTime.dayOfWeek = previousDayOfWeek
            }
            currentDayOfWeek = quiteTime.dayOfWeek
            setData(previousWeekProgressBars!![index], quiteTime)
        }

        animateWeekProgress()
    }

    private fun getDayOfWeekBefore(lastQuiteTimeDayOfWeek: DayOfWeek?): DayOfWeek {
        val dayIndex = daysOfWeek.indexOf(lastQuiteTimeDayOfWeek)

        return try {
            daysOfWeek[dayIndex - 1]
        } catch (ex: IndexOutOfBoundsException) {
            daysOfWeek[NUMBER_OF_DAYS_IN_WEEK - 1]
        }
    }

    private fun setData(progressBarLayout: ConstraintLayout, quiteTime: QuiteTime) {
        val progressBar = progressBarLayout.findViewById<ProgressBar>(R.id.pb_quite_time_week)
        val label = progressBarLayout.findViewById<TextView>(R.id.tv_day_of_week)

        progressBar.max = quiteTime.totalAmount

        val dayOfWeek = quiteTime.dayOfWeek
        label.text = dayOfWeek?.description
    }

    private fun animateWeekProgress() {
        val progressBars = ArrayList<ProgressBar>()
        for (historyProgressBar in previousWeekProgressBars!!) {
            val progressBar = historyProgressBar.findViewById<ProgressBar>(R.id.pb_quite_time_week)
            progressBars.add(progressBar)
        }

        ProgressAnimator(progressBars, previousWeekQuiteTime!!).startAnimation()
    }

}