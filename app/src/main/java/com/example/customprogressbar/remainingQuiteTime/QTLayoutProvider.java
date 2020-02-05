package com.example.customprogressbar.remainingQuiteTime;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.example.customprogressbar.remainingQuiteTime.models.RemainingQuiteTime;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class QTLayoutProvider implements QuiteTimeLayoutProvider {

    private enum ProviderState {
        SINGLE, MULTIPLE, EMPTY
    }

    private final LinearLayout root;
    private final Context context;

    private List<RemainingQuiteTime> remainingQuiteTimeList = new LinkedList<>();
    private ProviderState currentState = ProviderState.EMPTY;
    private QuiteTimeLayout currentLayout;

    public QTLayoutProvider(LinearLayout root, Context context) {
        this.root = root;
        this.context = context;

        currentLayout = new QuiteTimeMultipleRemainingLayout(context, root, remainingQuiteTimeList);
    }

    public void addAllQuiteTimeRemaining(List<RemainingQuiteTime> quiteTimeRemainingList) {
        remainingQuiteTimeList.addAll(quiteTimeRemainingList);
        if(didStateChange()) {
            changeState();
            changeLayout();
        }
    }

    public void addQuiteTimeRemaining(RemainingQuiteTime quiteTimeRemaining) {
        addAllQuiteTimeRemaining(Collections.singletonList(quiteTimeRemaining));
    }


    @Override
    public void itemsChanged(List<RemainingQuiteTime> quiteTimeRemainingList) {
        this.remainingQuiteTimeList = quiteTimeRemainingList;
        if(didStateChange()) {
            changeState();
            changeLayout();
        }
    }

    private void changeState() {
        if(remainingQuiteTimeList.isEmpty()) {
            currentState = ProviderState.EMPTY;
        } else if(remainingQuiteTimeList.size() == 1) {
            currentState = ProviderState.SINGLE;
        } else {
            currentState = ProviderState.MULTIPLE;
        }
    }

    private void changeLayout() {
        currentLayout.stop();
        switch (currentState) {
            case EMPTY: {
                root.setVisibility(View.GONE);
                break;
            }
//            case SINGLE: {
//                root.setVisibility(View.VISIBLE);
//                break;
//            }
            case MULTIPLE: {
                root.setVisibility(View.VISIBLE);
                currentLayout = new QuiteTimeMultipleRemainingLayout(context, root, remainingQuiteTimeList);
                break;
            }
        }
    }

    private boolean didStateChange() {
        if(currentState == ProviderState.EMPTY && remainingQuiteTimeList.size() == 0 ||
            //currentState == ProviderState.SINGLE && remainingQuiteTimeList.size() == 1 ||
            currentState == ProviderState.MULTIPLE && remainingQuiteTimeList.size() > 1) {
            return false;
        }
        return true;
    }
}
