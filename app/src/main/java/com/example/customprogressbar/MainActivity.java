package com.example.customprogressbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.customprogressbar.quiteTimeStats.models.DayOfWeek;
import com.example.customprogressbar.quiteTimeStats.models.QuiteTime;
import com.example.customprogressbar.quiteTimeStats.QuiteTimeStats;
import com.example.customprogressbar.remainingQuiteTime.models.RemainingQuiteTime;
import com.example.customprogressbar.remainingQuiteTime.QuiteTimeRemainingLayout;
import com.example.customprogressbar.remainingQuiteTime.models.QuiteTimeUser;

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
                new QuiteTime(120, 100),
                new QuiteTime(120, 60),
                new QuiteTime(120, 90),
                new QuiteTime(120, 60),
                new QuiteTime(120, 120),
                new QuiteTime(120, 50),
                new QuiteTime(120, 80)));


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

        final LinearLayout quiteTimeRV = findViewById(R.id.ll_quite_time);
        final QuiteTimeRemainingLayout quiteTimeRemainingAdapter = new QuiteTimeRemainingLayout(this, quiteTimeRV);

//        quiteTimeRV.setAdapter(quiteTimeRemainingAdapter);
//        quiteTimeRV.setLayoutManager(new LinearLayoutManager(this));
//        quiteTimeRV.setNestedScrollingEnabled(false);

        quiteTimeRemainingAdapter.addAllQuiteTimeRemaining(Arrays.asList(
                new RemainingQuiteTime(
                        Arrays.asList(new QuiteTimeUser("User1 big userrr eee", getResources().getDrawable(R.drawable.circle)),
                                        new QuiteTimeUser("User2", getResources().getDrawable(R.drawable.circle)),
                                new QuiteTimeUser("User6", getResources().getDrawable(R.drawable.circle)),
                                new QuiteTimeUser("User6", getResources().getDrawable(R.drawable.circle)),
                                new QuiteTimeUser("User6", getResources().getDrawable(R.drawable.circle)),
                                new QuiteTimeUser("User6", getResources().getDrawable(R.drawable.circle))), 10),


                new RemainingQuiteTime(
                        Arrays.asList(new QuiteTimeUser("User4", getResources().getDrawable(R.drawable.circle))),
                        15),
                new RemainingQuiteTime(
                        Arrays.asList(new QuiteTimeUser("User5", getResources().getDrawable(R.drawable.circle))),
                        20)

                )

        );

        quiteTimeRemainingAdapter.addQuiteTimeRemaining(new RemainingQuiteTime(
                Arrays.asList(new QuiteTimeUser("User20", getResources().getDrawable(R.drawable.circle))),
                6000));

    }

    private void reset() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}
