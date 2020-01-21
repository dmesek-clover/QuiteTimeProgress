package com.example.customprogressbar.remainingQuiteTime;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customprogressbar.R;

import java.util.ArrayList;
import java.util.List;

public class QuiteTimeRemainingAdapter extends RecyclerView.Adapter<QuiteTimeRemainingAdapter.ViewHolder>{

    private static final int rowHeight = 110;
    private static final int millisInSecond = 1000;

    private final Context context;
    private List<QuiteTimeRemaining> quiteTimeRemainingList = new ArrayList<>();
    private final QuiteTimeTimer quiteTimeTimer = new QuiteTimeTimer();

    public QuiteTimeRemainingAdapter(Context context) {
        this.context = context;
    }

    public void addQuiteTimeRemaining(QuiteTimeRemaining quiteTimeRemaining) {
        quiteTimeRemainingList.add(quiteTimeRemaining);
        notifyDataSetChanged();
    }

    public void addAllQuiteTimeRemaining(List<QuiteTimeRemaining> quiteTimeRemainingList) {
        this.quiteTimeRemainingList.addAll(quiteTimeRemainingList);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.quite_time_remaining_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final QuiteTimeRemainingAdapter.ViewHolder holder, int position) {
        final QuiteTimeRemaining quiteTimeRemaining = quiteTimeRemainingList.get(position);

        setupUserGridView(holder, quiteTimeRemaining);

        QuiteTimeTimerListener quiteTimeTimerListener = new QuiteTimeTimerListener() {
            @Override
            public Integer onTick() {
                Integer shouldRemoveIndex = null;
                quiteTimeRemaining.decrementSecondsRemainig();
                holder.remainingQuiteTime.setText(quiteTimeRemaining.getFormattedTimeRemaining());
                if(quiteTimeRemaining.isFinished()) {
                    int quitePosition = quiteTimeRemainingList.indexOf(quiteTimeRemaining);
                    quiteTimeRemainingList.remove(quiteTimeRemaining);
                    notifyItemRemoved(quitePosition);
                    shouldRemoveIndex = quitePosition;
                }
                return shouldRemoveIndex;
            }
        };

        quiteTimeTimer.toggleSubscription(quiteTimeTimerListener);

        holder.startStopButton.setOnClickListener(v -> {
            quiteTimeTimer.toggleSubscription(quiteTimeTimerListener);
        });
    }


    @Override
    public int getItemCount() {
        return quiteTimeRemainingList.size();
    }

    private void setupUserGridView(ViewHolder holder, QuiteTimeRemaining quiteTimeRemaining) {
        QuiteTimeUserAdapter quiteTimeUserAdapter = new QuiteTimeUserAdapter(context, quiteTimeRemaining.getQuiteTimeUsers());
        holder.quiteTimeUsers.setAdapter(quiteTimeUserAdapter);
        holder.quiteTimeUsers.setNestedScrollingEnabled(false);
        setDynamicHeight(holder.quiteTimeUsers, quiteTimeRemaining.getQuiteTimeUsers());
    }

    private void setDynamicHeight(GridView gridView, List<QuiteTimeUser> quiteTimeUserList) {
        int totalHeight = 0;
        for (int i = 0; i < quiteTimeUserList.size(); i += 2) {
            totalHeight += rowHeight;
        }
        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight;
        gridView.setLayoutParams(params);
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private final GridView quiteTimeUsers;
        private final TextView remainingQuiteTime;
        private final ImageView startStopButton;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            quiteTimeUsers = itemView.findViewById(R.id.gv_quite_time_users);
            remainingQuiteTime = itemView.findViewById(R.id.tv_remaining_quite_time);
            startStopButton = itemView.findViewById(R.id.btn_stop_quite_time);

        }
    }

}