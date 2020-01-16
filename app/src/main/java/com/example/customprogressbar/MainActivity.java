package com.example.customprogressbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final QuiteTimeStats quiteTimeStats = findViewById(R.id.quite_time_stats_layout);
        quiteTimeStats.setTodayDayOfWeek(DayOfWeek.TUESDAY);
        quiteTimeStats.setTodayMaxProgress(120);
        quiteTimeStats.setPreviousWeekQuiteTime(Arrays.asList(
                new QuiteTimeModel(120, 100),
                new QuiteTimeModel(120, 60),
                new QuiteTimeModel(120, 90),
                new QuiteTimeModel(120, 60),
                new QuiteTimeModel(120, 120),
                new QuiteTimeModel(120, 50),
                new QuiteTimeModel(120, 80)));

        quiteTimeStats.updateTodayProgress(90);

        final Random rand = new Random();
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quiteTimeStats.updateTodayProgress(Math.abs(rand.nextInt()) % 120);
            }
        });
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
            }
        });
    }

    private void reset() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}
