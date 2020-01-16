package com.example.customprogressbar;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

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
    private static final int animationDuration = 800; //ms

    private List<QuiteTimeModel> previousWeekQuiteTime;
    private int todayQuiteTimeProgress = 0;
    private int todayIncrementProgress = 0;
    private DayOfWeek todayDayOfWeek;
    private int todayMaxProgress = 0;

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
    }

    public void setTodayMaxProgress(int todayMaxProgress) {
        this.todayMaxProgress = todayMaxProgress;
        initializeTodayData();
    }

    public void setPreviousWeekQuiteTime(List<QuiteTimeModel> previousWeekQuiteTime) {
        this.previousWeekQuiteTime = previousWeekQuiteTime;
        initializePreviousWeekData();
    }

    public void updateProgress(final int progress) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(this,
                "todayIncrementProgress", todayQuiteTimeProgress, progress);
        objectAnimator.setDuration(animationDuration);
        objectAnimator.setInterpolator(new DecelerateInterpolator());
        objectAnimator.start();
        invalidate();
    }

    private void initializeTodayData() {
        todayProgressBar.setMax(todayMaxProgress);
        String placeholder = getContext().getString(R.string.max_progress_today);
        todayMaxProgressText.setText(String.format(placeholder, todayMaxProgress));
    }

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
        if(previousWeekQuiteTime == null) {
            previousWeekQuiteTime = new ArrayList<>();
        }
        if(previousWeekQuiteTime.size() > NUMBER_OF_DAYS_IN_WEEK) {
            throw new IllegalArgumentException("previousWeekQuiteTime list should contain at most " + NUMBER_OF_DAYS_IN_WEEK + " items.");
        }
        if(todayDayOfWeek == null || todayMaxProgress == 0) {
            throw new IllegalStateException("Please call setTodayDayOfWeek(DayOfWeek dayOfWeek) and setTodayMaxProgress(int todayMaxProgress)" +
                    " before calling setPreviousWeekQuiteTime.");
        }

        setDataForProgressBars();
    }

    private void setDataForProgressBars() {
        DayOfWeek currentDayOfWeek = todayDayOfWeek;
        QuiteTimeModel quiteTime;
        for(int index = 0; index < NUMBER_OF_DAYS_IN_WEEK; index++) {
            if(index >= previousWeekQuiteTime.size()) {
                quiteTime = createQuiteTimeBefore(currentDayOfWeek);
            } else {
                quiteTime = previousWeekQuiteTime.get(index);
            }
            currentDayOfWeek = quiteTime.getDayOfWeek();
            setData(previousWeekProgressBars.get(index), quiteTime);
        }
    }

    private QuiteTimeModel createQuiteTimeBefore(DayOfWeek lastQuiteTimeDayOfWeek) {
        int dayIndex = daysOfWeek.indexOf(lastQuiteTimeDayOfWeek);
        DayOfWeek previousDayOfWeek;
        try {
            previousDayOfWeek = daysOfWeek.get(dayIndex - 1);
        } catch(IndexOutOfBoundsException ex) {
            previousDayOfWeek = daysOfWeek.get(NUMBER_OF_DAYS_IN_WEEK - 1);
        }
        return new QuiteTimeModel(DEFAULT_MAX, DEFAULT_PROGRESS, previousDayOfWeek);
    }

    private void setData(ConstraintLayout progressBarLayout, QuiteTimeModel quiteTimeModel) {
        final ProgressBar progressBar = progressBarLayout.findViewById(R.id.pb_quite_time_week);
        final TextView label = progressBarLayout.findViewById(R.id.tv_day_of_week);

        progressBar.setMax(quiteTimeModel.getTotalAmount());
        progressBar.setProgress(quiteTimeModel.getUsedAmount());

        DayOfWeek dayOfWeek = quiteTimeModel.getDayOfWeek();
        label.setText(dayOfWeek != null ? dayOfWeek.getDescription() : "");
    }

    private void setTodayIncrementProgress(int todayIncrementProgress) {
        this.todayIncrementProgress = todayIncrementProgress;
        updateTodayData();
    }

    private int getTodayIncrementProgress() {
        return todayIncrementProgress;
    }


}

