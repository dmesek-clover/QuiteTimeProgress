package com.example.customprogressbar

import android.graphics.Rect
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.customprogressbar.quiteTimeStats.models.DayOfWeek
import com.example.customprogressbar.quiteTimeStats.models.QuiteTime
import com.example.customprogressbar.remainingQuiteTime.models.QuiteTimeUser
import com.example.customprogressbar.remainingQuiteTime.models.RemainingQuiteTime
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.quite_time_stats.view.*
import kotlin.random.Random

@Suppress("DEPRECATION")
class SomeActivity : AppCompatActivity() {

    var wasAnimated = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.let {
            it.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            it.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
            it.setBackgroundDrawableResource(R.drawable.half_circle)
        }

        scrollView2.apply {
            viewTreeObserver.addOnScrollChangedListener {
                val scrollBounds = Rect()
                getHitRect(scrollBounds)
                if (ll_progress_bar_holder.getLocalVisibleRect(scrollBounds) && !wasAnimated) {
                    wasAnimated = true
                    Handler().postDelayed({ quite_time_stats_layout.animateWeekProgress() }, 300)
                }
            }
        }



        quite_time_stats_layout.setTodayDayOfWeek(DayOfWeek.WEDNESDAY)
        quite_time_stats_layout.setTodayMaxProgress(120)
        quite_time_stats_layout.setPreviousWeekQuiteTime(listOf(
                QuiteTime(120, 100, null),
                QuiteTime(120, 60, null),
                QuiteTime(120, 90, null),
                QuiteTime(120, 60, null),
                QuiteTime(120, 120, null),
                QuiteTime(120, 80, null)))


        button2.setOnClickListener {
            quite_time_stats_layout.updateTodayProgress(Random.nextInt(0, 120))
        }


        val rqt = RemainingQuiteTime(
                arrayListOf(QuiteTimeUser("User20", resources.getDrawable(R.drawable.dummy7))),
                15)
        ll_quite_time.addQuiteTimeRemaining(rqt)
        //
        ll_quite_time.addAllQuiteTimeRemaining(listOf(
                RemainingQuiteTime(
                        arrayListOf(
                                QuiteTimeUser("User1 big userrr eee", resources.getDrawable(R.drawable.dummy)),
                                QuiteTimeUser("User2", resources.getDrawable(R.drawable.dummy2)),
                                QuiteTimeUser("User6", resources.getDrawable(R.drawable.dummy3)),
                                QuiteTimeUser("User6", resources.getDrawable(R.drawable.dummy4)),
                                QuiteTimeUser("User6", resources.getDrawable(R.drawable.dummy5)),
                                QuiteTimeUser("User6", resources.getDrawable(R.drawable.dummy6))), 1000)

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

}