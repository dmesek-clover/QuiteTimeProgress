package com.example.customprogressbar.remainingQuiteTime;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.customprogressbar.R;

import java.util.List;

public class QuiteTimeUserAdapter extends BaseAdapter {

    private final List<QuiteTimeUser> quiteTimeUserList;
    private final LayoutInflater layoutInflater;

    public QuiteTimeUserAdapter(Context context, List<QuiteTimeUser> quiteTimeUserList) {
        this.quiteTimeUserList = quiteTimeUserList;
        this.layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return quiteTimeUserList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        final QuiteTimeUser quiteTimeUser = quiteTimeUserList.get(position);
        if(view == null) {
            view = layoutInflater.inflate(R.layout.quite_time_user_item, null);
        }

        final ImageView userIcon = view.findViewById(R.id.iv_quite_time_user_icon);
        final TextView userName = view.findViewById(R.id.tv_quite_time_user_name);
        userIcon.setImageDrawable(quiteTimeUser.getIcon());
        userName.setText(quiteTimeUser.getName());

        return view;
    }

}
