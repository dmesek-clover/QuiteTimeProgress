package com.example.customprogressbar;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.customprogressbar.quiteTimeStats.models.DayOfWeek;
import com.example.customprogressbar.quiteTimeStats.models.QuiteTime;
import com.example.customprogressbar.quiteTimeStats.QuiteTimeStats;

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
                new QuiteTime(120, 100, null),
                new QuiteTime(120, 60, null),
                new QuiteTime(120, 90, null),
                new QuiteTime(120, 60,null ),
                new QuiteTime(120, 120, null),
                new QuiteTime(120, 50, null),
                new QuiteTime(120, 80, null)));


        final Random rand = new Random();
        findViewById(R.id.button2).setOnClickListener(view -> quiteTimeStats.updateTodayProgress(Math.abs(rand.nextInt()) % 120));
        findViewById(R.id.button).setOnClickListener(view -> reset());

//        final LinearLayout quiteTimeRV = findViewById(R.id.ll_quite_time);
//        final QTLayoutProvider quiteTimeRemainingAdapter = new QTLayoutProvider(quiteTimeRV, this);
//
//        quiteTimeRemainingAdapter.addQuiteTimeRemaining(new RemainingQuiteTime(
//                Arrays.asList(new QuiteTimeUser("User20", getResources().getDrawable(R.drawable.circle))),
//                6000));
//
//        quiteTimeRemainingAdapter.addAllQuiteTimeRemaining(Arrays.asList(
//                new RemainingQuiteTime(
//                        Arrays.asList(new QuiteTimeUser("User1 big userrr eee", getResources().getDrawable(R.drawable.circle)),
//                                        new QuiteTimeUser("User2", getResources().getDrawable(R.drawable.circle)),
//                                new QuiteTimeUser("User6", getResources().getDrawable(R.drawable.circle)),
//                                new QuiteTimeUser("User6", getResources().getDrawable(R.drawable.circle)),
//                                new QuiteTimeUser("User6", getResources().getDrawable(R.drawable.circle)),
//                                new QuiteTimeUser("User6", getResources().getDrawable(R.drawable.circle))), 10),
//
//
//                new RemainingQuiteTime(
//                        Arrays.asList(new QuiteTimeUser("User4", getResources().getDrawable(R.drawable.circle))),
//                        15),
//                new RemainingQuiteTime(
//                        Arrays.asList(new QuiteTimeUser("User5", getResources().getDrawable(R.drawable.circle))),
//                        20)
//
//                )
//
//        );



    }

    private void reset() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}
