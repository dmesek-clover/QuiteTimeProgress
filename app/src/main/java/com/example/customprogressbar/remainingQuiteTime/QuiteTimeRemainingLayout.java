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
import java.util.Arrays;
import java.util.List;

public class QuiteTimeRemainingLayout {

    private final LayoutInflater layoutInflater;
    private final Context context;
    private final LinearLayout root;
    private List<RemainingQuiteTime> quiteTimeRemainingList = new ArrayList<>();
    private final QuiteTimeTimer quiteTimeTimer = new QuiteTimeTimer();

    public QuiteTimeRemainingLayout(Context context, LinearLayout root) {
        this.context = context;
        this.root = root;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void addQuiteTimeRemaining(RemainingQuiteTime quiteTimeRemaining) {
        quiteTimeRemainingList.add(quiteTimeRemaining);
        updateData(Arrays.asList(quiteTimeRemaining));
    }

    public void addAllQuiteTimeRemaining(List<RemainingQuiteTime> quiteTimeRemainingList) {
        this.quiteTimeRemainingList.addAll(quiteTimeRemainingList);
        updateData(quiteTimeRemainingList);
    }

    private void updateData(List<RemainingQuiteTime> newQuiteTime) {
        for(RemainingQuiteTime quiteTimeRemaining: newQuiteTime) {
            View view = layoutInflater.inflate(R.layout.quite_time_remaining_item, null);
            populateView(view, quiteTimeRemaining);
            attachToLinearLayout(view);
        }
    }

    private void populateView(View view, RemainingQuiteTime quiteTimeRemaining) {
        final GridLayout gridUsers = view.findViewById(R.id.gv_quite_time_users);
        final TextView remainingQuiteTime = view.findViewById(R.id.tv_remaining_quite_time);
        final ImageView pauseStartButton = view.findViewById(R.id.btn_stop_quite_time);

        QuiteTimeTimerListener timerListener = createTimerListener(remainingQuiteTime, quiteTimeRemaining);
        quiteTimeTimer.toggleSubscription(timerListener);

        QuiteTimeUserLayout quiteTimeUserAdapter = new QuiteTimeUserLayout(context, gridUsers, quiteTimeRemaining.getQuiteTimeUsers());
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