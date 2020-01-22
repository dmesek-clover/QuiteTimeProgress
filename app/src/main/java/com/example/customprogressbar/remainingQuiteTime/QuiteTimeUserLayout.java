package com.example.customprogressbar.remainingQuiteTime;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.customprogressbar.R;
import com.example.customprogressbar.remainingQuiteTime.models.QuiteTimeUser;

import java.util.List;

public class QuiteTimeUserLayout {

    private final LayoutInflater layoutInflater;
    private final GridLayout root;
    private final List<QuiteTimeUser> quiteTimeUsers;

    public QuiteTimeUserLayout(Context context, GridLayout root, List<QuiteTimeUser> quiteTimeUsers) {
        this.root = root;
        this.layoutInflater = LayoutInflater.from(context);
        this.quiteTimeUsers = quiteTimeUsers;

        updateData();
    }

    private void updateData() {
        root.removeAllViews();
        for(QuiteTimeUser quiteTimeUser: quiteTimeUsers) {
            View view = layoutInflater.inflate(R.layout.quite_time_user_item, null);
            populateView(view, quiteTimeUser);
            attachToLinearLayout(view);
        }
    }

    private void populateView(View view, QuiteTimeUser quiteTimeUser) {
        final TextView userName = view.findViewById(R.id.tv_quite_time_user_name);
        final ImageView userIcon = view.findViewById(R.id.iv_quite_time_user_icon);

        userIcon.setImageDrawable(quiteTimeUser.getIcon());
        userName.setText(quiteTimeUser.getName());
    }

    private void attachToLinearLayout(View view) {
        root.addView(view);
    }




}
