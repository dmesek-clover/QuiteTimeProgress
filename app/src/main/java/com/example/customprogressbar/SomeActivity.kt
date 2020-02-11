package com.example.customprogressbar

import android.os.Bundle
import android.os.PersistableBundle
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.customprogressbar.quiteTimeStats.QuiteTimeStats
import com.example.customprogressbar.quiteTimeStats.models.DayOfWeek
import com.example.customprogressbar.quiteTimeStats.models.QuiteTime
import com.example.customprogressbar.remainingQuiteTime.QuiteTimeLayoutProvider
import com.example.customprogressbar.remainingQuiteTime.models.QuiteTimeUser
import com.example.customprogressbar.remainingQuiteTime.models.RemainingQuiteTime
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

class SomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        window.setBackgroundDrawableResource(R.drawable.half_circle)

        val quiteTimeStats = findViewById<QuiteTimeStats>(R.id.quite_time_stats_layout)
        quiteTimeStats.setTodayDayOfWeek(DayOfWeek.TUESDAY)
        quiteTimeStats.setTodayMaxProgress(120)
        quiteTimeStats.setPreviousWeekQuiteTime(listOf(
                QuiteTime(120, 100, null),
                QuiteTime(120, 60, null),
                QuiteTime(120, 90, null),
                QuiteTime(120, 60, null),
                QuiteTime(120, 120, null),
                QuiteTime(120, 50, null),
                QuiteTime(120, 80, null)))


        button2.setOnClickListener {
            quiteTimeStats.updateTodayProgress(Random.nextInt(0,120))
        }

        val quiteTimeRV = findViewById(R.id.ll_quite_time) as LinearLayout
        val quiteTimeRemainingAdapter = QuiteTimeLayoutProvider(this, quiteTimeRV)

        val rqt = RemainingQuiteTime(
                arrayListOf(QuiteTimeUser("User20", getResources().getDrawable(R.drawable.dummy7))),
                6000)
        quiteTimeRemainingAdapter.addQuiteTimeRemaining(rqt)
        //
        quiteTimeRemainingAdapter.addAllQuiteTimeRemaining(Arrays.asList(
                RemainingQuiteTime(
                        arrayListOf(
                                QuiteTimeUser("User1 big userrr eee", getResources().getDrawable(R.drawable.dummy)),
                                QuiteTimeUser("User1 ", getResources().getDrawable(R.drawable.dummy)),
                                QuiteTimeUser("User1 ", getResources().getDrawable(R.drawable.dummy)),
                                QuiteTimeUser("User1 ", getResources().getDrawable(R.drawable.dummy)),
                                QuiteTimeUser("User2", getResources().getDrawable(R.drawable.dummy2)),
                                QuiteTimeUser("User6", getResources().getDrawable(R.drawable.dummy3)),
                                QuiteTimeUser("User6", getResources().getDrawable(R.drawable.dummy4)),
                                QuiteTimeUser("User6", getResources().getDrawable(R.drawable.dummy5)),
                                QuiteTimeUser("User6", getResources().getDrawable(R.drawable.dummy6))), 1000)

                //                new RemainingQuiteTime(
                //                        Arrays.asList(new QuiteTimeUser("User4", getResources().getDrawable(R.drawable.circle))),
                //                        15),
                //                new RemainingQuiteTime(
                //                        Arrays.asList(new QuiteTimeUser("User5", getResources().getDrawable(R.drawable.circle))),
                //                        20)
                //
                //                )

        ))
        //        EndQuiteTimeDialog dialog = new EndQuiteTimeDialog(Arrays.asList(
        //                                new QuiteTimeUser("User1 big userrr eee", getResources().getDrawable(R.drawable.circle)),
        //                                new QuiteTimeUser("User2", getResources().getDrawable(R.drawable.circle))));
        //
        //        dialog.show(getSupportFragmentManager(), "EndQuiteTimeDialog");

    }


    private fun reset() {
        val intent = getIntent()
        finish()
        startActivity(intent)
    }
}