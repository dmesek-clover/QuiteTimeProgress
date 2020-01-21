package com.example.customprogressbar.quiteTimeStats;

public class QuiteTimeModel {
    private final int totalAmount;
    private final int usedAmount;
    private DayOfWeek dayOfWeek;

    QuiteTimeModel(int totalAmount, int usedAmount, DayOfWeek dayOfWeek) {
        this.totalAmount = totalAmount;
        this.usedAmount = usedAmount;
        this.dayOfWeek = dayOfWeek;
    }

    public QuiteTimeModel(int totalAmount, int usedAmount) {
        this(totalAmount, usedAmount, null);
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

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
}
