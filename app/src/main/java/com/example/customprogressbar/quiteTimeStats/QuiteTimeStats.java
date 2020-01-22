package com.example.customprogressbar.quiteTimeStats;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.customprogressbar.R;
import com.example.customprogressbar.quiteTimeStats.models.DayOfWeek;
import com.example.customprogressbar.quiteTimeStats.models.QuiteTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuiteTimeStats extends ConstraintLayout {

    private static final List<DayOfWeek> daysOfWeek =
            Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY,
                    DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);

    private static final int NUMBER_OF_DAYS_IN_WEEK = 7;
    private static final int DEFAULT_MAX = 120;
    private static final int DEFAULT_PROGRESS = 0;
    private static int animationDuration = 2000; //ms changed after first animation to 700ms

    private List<QuiteTime> previousWeekQuiteTime;
    private int todayQuiteTimeProgress;
    private int todayIncrementProgress;
    private DayOfWeek todayDayOfWeek;
    private int todayMaxProgress = DEFAULT_MAX;

    private ProgressBar todayProgressBar;
    private TextView todayProgressText;
    private TextView todayMaxProgressText;
    private List<ConstraintLayout> previousWeekProgressBars;

    public QuiteTimeStats(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(getContext(), R.layout.quite_time_stats, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        final ConstraintLayout firstProgressBar = findViewById(R.id.pb_quite_time_first);
        final ConstraintLayout secondProgressBar = findViewById(R.id.pb_quite_time_second);
        final ConstraintLayout thirdProgressBar = findViewById(R.id.pb_quite_time_third);
        final ConstraintLayout fourthProgressBar = findViewById(R.id.pb_quite_time_fourth);
        final ConstraintLayout fifthProgressBar = findViewById(R.id.pb_quite_time_fifth);
        final ConstraintLayout sixthProgressBar = findViewById(R.id.pb_quite_time_sixth);
        final ConstraintLayout seventhProgressBar = findViewById(R.id.pb_quite_time_seventh);

        todayProgressBar = findViewById(R.id.pb_quite_time_today);
        previousWeekProgressBars = Arrays.asList(seventhProgressBar, sixthProgressBar, fifthProgressBar,
                fourthProgressBar, thirdProgressBar, secondProgressBar, firstProgressBar);
        todayProgressText = findViewById(R.id.tv_today_progress);
        todayMaxProgressText = findViewById(R.id.tv_max_progress);
    }

    public void setTodayDayOfWeek(DayOfWeek todayDayOfWeek) {
        this.todayDayOfWeek = todayDayOfWeek;
        initializeTodayData();
    }

    public void setTodayMaxProgress(int todayMaxProgress) {
        this.todayMaxProgress = todayMaxProgress;
    }

    public void setPreviousWeekQuiteTime(List<QuiteTime> previousWeekQuiteTime) {
        this.previousWeekQuiteTime = previousWeekQuiteTime;
        initializePreviousWeekData();
    }

    public void updateTodayProgress(final int progress) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(this,
                "todayIncrementProgress", todayQuiteTimeProgress, progress);
        objectAnimator.setDuration(animationDuration);
        objectAnimator.setInterpolator(new DecelerateInterpolator());
        objectAnimator.start();
        animationDuration = 700;
    }


    private void initializeTodayData() {
        todayProgressBar.setMax(todayMaxProgress);
        String placeholder = getContext().getString(R.string.max_progress_today);
        todayMaxProgressText.setText(String.format(placeholder, todayMaxProgress));
    }

    //used by ObjectAnimator
    private void updateTodayData() {
        final int difference = todayIncrementProgress - todayQuiteTimeProgress;
        todayProgressBar.incrementProgressBy(difference);

        todayQuiteTimeProgress = todayIncrementProgress;
        todayProgressText.setText(formatUsedAmount(todayQuiteTimeProgress));
    }

    private String formatUsedAmount(int usedAmount) {
        return usedAmount + "m";
    }

    private void initializePreviousWeekData() {
        if (previousWeekQuiteTime == null) {
            previousWeekQuiteTime = new ArrayList<>();
        }
        if (previousWeekQuiteTime.size() > NUMBER_OF_DAYS_IN_WEEK) {
            throw new IllegalArgumentException("previousWeekQuiteTime list should contain at most " + NUMBER_OF_DAYS_IN_WEEK + " items.");
        }
        if (todayDayOfWeek == null) {
            throw new IllegalStateException("Please call setTodayDayOfWeek(DayOfWeek dayOfWeek)" +
                    " before calling setPreviousWeekQuiteTime method.");
        }

        setDataForProgressBars();
    }

    private void setDataForProgressBars() {
        DayOfWeek currentDayOfWeek = todayDayOfWeek;
        QuiteTime quiteTime;
        for (int index = 0; index < NUMBER_OF_DAYS_IN_WEEK; index++) {
            DayOfWeek previousDayOfWeek = getDayOfWeekBefore(currentDayOfWeek);
            if (index >= previousWeekQuiteTime.size()) {
                quiteTime = new QuiteTime(DEFAULT_MAX, DEFAULT_PROGRESS, previousDayOfWeek);
            } else {
                quiteTime = previousWeekQuiteTime.get(index);
                quiteTime.setDayOfWeek(previousDayOfWeek);
            }
            currentDayOfWeek = quiteTime.getDayOfWeek();
            setData(previousWeekProgressBars.get(index), quiteTime);
        }

        animateWeekProgress();
    }

    private DayOfWeek getDayOfWeekBefore(DayOfWeek lastQuiteTimeDayOfWeek) {
        int dayIndex = daysOfWeek.indexOf(lastQuiteTimeDayOfWeek);
        DayOfWeek previousDayOfWeek;
        try {
            previousDayOfWeek = daysOfWeek.get(dayIndex - 1);
        } catch (IndexOutOfBoundsException ex) {
            previousDayOfWeek = daysOfWeek.get(NUMBER_OF_DAYS_IN_WEEK - 1);
        }
        return previousDayOfWeek;
    }

    private void setData(ConstraintLayout progressBarLayout, QuiteTime quiteTime) {
        final ProgressBar progressBar = progressBarLayout.findViewById(R.id.pb_quite_time_week);
        final TextView label = progressBarLayout.findViewById(R.id.tv_day_of_week);

        progressBar.setMax(quiteTime.getTotalAmount());

        DayOfWeek dayOfWeek = quiteTime.getDayOfWeek();
        label.setText(dayOfWeek.getDescription());
    }

    private void animateWeekProgress() {
        List<ProgressBar> progressBars = new ArrayList<>();
        for (ConstraintLayout historyProgressBar : previousWeekProgressBars) {
            final ProgressBar progressBar = historyProgressBar.findViewById(R.id.pb_quite_time_week);
            progressBars.add(progressBar);
        }

        new ProgressAnimator(progressBars, previousWeekQuiteTime).startAnimation();
    }


    //used by the ObjectAnimator
    private void setTodayIncrementProgress(int todayIncrementProgress) {
        this.todayIncrementProgress = todayIncrementProgress;
        updateTodayData();
    }

    private int getTodayIncrementProgress() {
        return todayIncrementProgress;
    }


}

