package com.example.customprogressbar.remainingQuiteTime;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.customprogressbar.R;
import com.example.customprogressbar.remainingQuiteTime.models.RemainingQuiteTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class QuiteTimeMultipleRemainingLayout implements QuiteTimeLayout {

    private final LayoutInflater layoutInflater;
    private final Context context;
    private final LinearLayout root;
    private List<RemainingQuiteTime> quiteTimeRemainingList;
    private final QuiteTimeTimer quiteTimeTimer = new QuiteTimeTimer();

    public QuiteTimeMultipleRemainingLayout(Context context, LinearLayout root, List<RemainingQuiteTime> quiteTimeRemainingList) {
        this.context = context;
        this.root = root;
        this.quiteTimeRemainingList = quiteTimeRemainingList;
        this.layoutInflater = LayoutInflater.from(context);

        populateData(quiteTimeRemainingList);
    }

    public void addAllQuiteTimeRemaining(List<RemainingQuiteTime> quiteTimeRemainingList) {
        this.quiteTimeRemainingList.addAll(quiteTimeRemainingList);
        populateData(quiteTimeRemainingList);
    }

    public void addQuiteTimeRemaining(RemainingQuiteTime quiteTimeRemaining) {
        addAllQuiteTimeRemaining(Collections.singletonList(quiteTimeRemaining));
    }

    public void stop() {
        quiteTimeRemainingList.clear();
        quiteTimeTimer.removeAllSubscribers();
    }

    @Override
    public int getSize() {
        return quiteTimeRemainingList.size();
    }

    private void populateData(List<RemainingQuiteTime> newQuiteTime) {
        buildMultipleUsers(newQuiteTime);
    }

    private void buildMultipleUsers(List<RemainingQuiteTime> newQuiteTime) {
        for(RemainingQuiteTime quiteTimeRemaining: newQuiteTime) {
            View view = layoutInflater.inflate(R.layout.quite_time_multiple_remaining_item, null);
            populateView(view, quiteTimeRemaining);
            attachToLinearLayout(view);
        }
    }

    private void populateView(View view, RemainingQuiteTime quiteTimeRemaining) {
        final GridLayout gridUsers = view.findViewById(R.id.gv_quite_time_users);
        final TextView remainingQuiteTime = view.findViewById(R.id.tv_remaining_quite_time);
        final ImageView stopButton = view.findViewById(R.id.btn_stop_quite_time);

        QuiteTimeTimerListener timerListener = createTimerListener(remainingQuiteTime, quiteTimeRemaining);
        quiteTimeTimer.subscribeToTimer(timerListener);

        new QuiteTimeUserLayout(context, gridUsers, quiteTimeRemaining.getQuiteTimeUsers());

        stopButton.setOnClickListener(v -> {
            quiteTimeRemainingList.remove(quiteTimeRemaining);
            quiteTimeTimer.unsubscribeFromTimer(timerListener);
            root.removeView(view);
        });


    }

    private void attachToLinearLayout(View view) {
            root.addView(view);
    }

    private QuiteTimeTimerListener createTimerListener(TextView remainingQuiteTime, RemainingQuiteTime quiteTimeRemaining) {
        return () -> {
            Integer shouldRemoveIndex = null;

            quiteTimeRemaining.decrementSecondsRemainig();
            remainingQuiteTime.setText(quiteTimeRemaining.getFormattedTimeRemaining());
            if(quiteTimeRemaining.isFinished()) {
                int quitePosition = quiteTimeRemainingList.indexOf(quiteTimeRemaining);
                quiteTimeRemainingList.remove(quiteTimeRemaining);
                root.removeViewAt(quitePosition);
                shouldRemoveIndex = quitePosition;
            }

            return shouldRemoveIndex;
        };
    }


}