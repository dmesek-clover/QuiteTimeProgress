package com.example.customprogressbar.remainingQuiteTime;

import android.text.Html;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;

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
