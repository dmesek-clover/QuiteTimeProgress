package com.example.customprogressbar.quiteTimeStats.models;

public class QuiteTime {
    private final int totalAmount;
    private final int usedAmount;
    private DayOfWeek dayOfWeek;

    public QuiteTime(int totalAmount, int usedAmount, DayOfWeek dayOfWeek) {
        this.totalAmount = totalAmount;
        this.usedAmount = usedAmount;
        this.dayOfWeek = dayOfWeek;
    }

    public QuiteTime(int totalAmount, int usedAmount) {
        this(totalAmount, usedAmount, null);
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public int getUsedAmount() {
        return usedAmount;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
}
