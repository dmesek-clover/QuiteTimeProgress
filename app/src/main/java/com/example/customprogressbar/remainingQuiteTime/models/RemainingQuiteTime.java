package com.example.customprogressbar.remainingQuiteTime.models;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class RemainingQuiteTime {
    private static final int secondsInHours = 3600;
    private static final int secondsInMinutes = 60;

    private final List<QuiteTimeUser> quiteTimeUsers;
    private int secondsRemaining;

    public RemainingQuiteTime(List<QuiteTimeUser> quiteTimeUsers, int secondsRemaining) {
        this.quiteTimeUsers = quiteTimeUsers;
        this.secondsRemaining = secondsRemaining;
    }

    public RemainingQuiteTime(List<QuiteTimeUser> quiteTimeUsers) {
        this(quiteTimeUsers, 0);
    }

    public void decrementSecondsRemainig() {
        this.secondsRemaining--;
    }

    public int getSecondsRemaining() {
        return secondsRemaining;
    }

    public boolean isFinished() {
        return secondsRemaining == 0;
    }


    public String getFormattedTimeRemaining() {
        int hours = secondsRemaining / secondsInHours;
        int minutes = (secondsRemaining % secondsInHours) / secondsInMinutes;
        int seconds = secondsRemaining % secondsInMinutes;

        String formattedSeconds = String.format(Locale.ENGLISH, "%ds", seconds);
        String formattedMinutes = String.format(Locale.ENGLISH, "%dm", minutes);
        String formattedHours = String.format(Locale.ENGLISH, "%dh", hours);

        StringBuilder formattedTime = new StringBuilder();

        if(hours != 0 && minutes != 0) {
            formattedTime
                    .append(formattedHours)
                    .append(" ")
                    .append(formattedMinutes)
                    .append(" ");
        } else if(hours == 0 && minutes != 0) {
            formattedTime
                    .append(formattedMinutes)
                    .append(" ");
        }
        formattedTime.append(formattedSeconds);

        return formattedTime.toString();
    }


    public List<QuiteTimeUser> getQuiteTimeUsers() {
        return quiteTimeUsers;
    }
}
