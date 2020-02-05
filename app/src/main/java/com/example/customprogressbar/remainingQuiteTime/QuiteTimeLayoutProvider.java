package com.example.customprogressbar.remainingQuiteTime;

import com.example.customprogressbar.remainingQuiteTime.models.RemainingQuiteTime;

import java.util.List;

public interface QuiteTimeLayoutProvider {
    void itemsChanged(List<RemainingQuiteTime> quiteTimeRemainingList);
}
