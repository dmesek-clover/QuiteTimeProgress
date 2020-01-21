package com.example.customprogressbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customprogressbar.quiteTimeStats.DayOfWeek;
import com.example.customprogressbar.quiteTimeStats.QuiteTimeModel;
import com.example.customprogressbar.quiteTimeStats.QuiteTimeStats;
import com.example.customprogressbar.remainingQuiteTime.QuiteTimeRemaining;
import com.example.customprogressbar.remainingQuiteTime.QuiteTimeRemainingAdapter;
import com.example.customprogressbar.remainingQuiteTime.QuiteTimeUser;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
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

        //quiteTimeStats.updateTodayProgress(90);

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

        final QuiteTimeRemainingAdapter quiteTimeRemainingAdapter = new QuiteTimeRemainingAdapter(this);
        final RecyclerView quiteTimeRV = findViewById(R.id.rv_quite_time);
        quiteTimeRV.setAdapter(quiteTimeRemainingAdapter);
        quiteTimeRV.setLayoutManager(new LinearLayoutManager(this));
        quiteTimeRV.setNestedScrollingEnabled(false);

        quiteTimeRemainingAdapter.addAllQuiteTimeRemaining(Arrays.asList(
                new QuiteTimeRemaining(
                        Arrays.asList(new QuiteTimeUser("User1 big userrr eee", getResources().getDrawable(R.drawable.circle)),
                                        new QuiteTimeUser("User2", getResources().getDrawable(R.drawable.circle)),
                                new QuiteTimeUser("User6", getResources().getDrawable(R.drawable.circle)),
                                new QuiteTimeUser("User6", getResources().getDrawable(R.drawable.circle)),
                                new QuiteTimeUser("User6", getResources().getDrawable(R.drawable.circle)),
                                new QuiteTimeUser("User6", getResources().getDrawable(R.drawable.circle))), 3),


                new QuiteTimeRemaining(
                        Arrays.asList(new QuiteTimeUser("User4", getResources().getDrawable(R.drawable.circle))),
                        15),
                new QuiteTimeRemaining(
                        Arrays.asList(new QuiteTimeUser("User5", getResources().getDrawable(R.drawable.circle))),
                        20)

                )

        );

        quiteTimeRemainingAdapter.addQuiteTimeRemaining(new QuiteTimeRemaining(
                Arrays.asList(new QuiteTimeUser("User20", getResources().getDrawable(R.drawable.circle))),
                6000));

    }

    private void reset() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}
