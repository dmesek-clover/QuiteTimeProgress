package com.example.customprogressbar;

public class QuiteTimeModel {
    private final int totalAmount;
    private int usedAmount;
    private final DayOfWeek dayOfWeek;

    QuiteTimeModel(int totalAmount, int usedAmount, DayOfWeek dayOfWeek) {
        this.totalAmount = totalAmount;
        this.usedAmount = usedAmount;
        this.dayOfWeek = dayOfWeek;
    }

    int getTotalAmount() {
        return totalAmount;
    }

    int getUsedAmount() {
        return usedAmount;
    }

    DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void updateUsedAmount(int usedAmount) {
        this.usedAmount = usedAmount;
    }
}
