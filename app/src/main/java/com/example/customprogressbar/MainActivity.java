package com.example.customprogressbar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final QuiteTimeStats quiteTimeStats = findViewById(R.id.quite_time_stats_layout);
        quiteTimeStats.setTodayDayOfWeek(DayOfWeek.MONDAY);
        quiteTimeStats.setTodayMaxProgress(120);
        quiteTimeStats.setPreviousWeekQuiteTime(Arrays.asList(new QuiteTimeModel(120, 60, DayOfWeek.SUNDAY),
                new QuiteTimeModel(120, 30, DayOfWeek.SATURDAY),
                new QuiteTimeModel(120, 90, DayOfWeek.FRIDAY)));

        final Random rand = new Random();
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quiteTimeStats.updateProgress(Math.abs(rand.nextInt()) % 120);
            }
        });
    }
}
