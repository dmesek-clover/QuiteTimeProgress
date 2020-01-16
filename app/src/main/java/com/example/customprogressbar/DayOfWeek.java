package com.example.customprogressbar;

public enum DayOfWeek {
    MONDAY("M"), TUESDAY("T"), WEDNESDAY("W"),
    THURSDAY("T"), FRIDAY("F") ,SATURDAY("S"),
    SUNDAY("S");

    private final String description;

    DayOfWeek(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
