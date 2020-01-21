package com.example.customprogressbar.remainingQuiteTime;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class QuiteTimeRemaining {
    private final List<QuiteTimeUser> quiteTimeUsers;
    private int secondsRemaining;

    public QuiteTimeRemaining(List<QuiteTimeUser> quiteTimeUsers, int secondsRemaining) {
        this.quiteTimeUsers = quiteTimeUsers;
        this.secondsRemaining = secondsRemaining;
    }

    public QuiteTimeRemaining(List<QuiteTimeUser> quiteTimeUsers) {
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuiteTimeRemaining that = (QuiteTimeRemaining) o;
        return Objects.equals(getQuiteTimeUsers(), that.getQuiteTimeUsers());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getQuiteTimeUsers());
    }

    public String getFormattedTimeRemaining() {
        int hours = secondsRemaining / 3600;
        int minutes = (secondsRemaining % 3600) / 60;
        int seconds = secondsRemaining % 60;

        String formattedTime;
        if (hours == 0 && minutes == 0) {
            formattedTime =
                    String.format(Locale.ENGLISH, "%ds", seconds);
        } else if (hours == 0) {
            formattedTime =
                    String.format(Locale.ENGLISH, "%dm %ds", minutes, seconds);
        } else {
            formattedTime = formattedTime =
                    String.format(Locale.ENGLISH, "%dh %dm %ds", hours, minutes, seconds);
        }
        return formattedTime;
    }

    public List<QuiteTimeUser> getQuiteTimeUsers() {
        return quiteTimeUsers;
    }
}
