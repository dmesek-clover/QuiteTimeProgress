package com.example.customprogressbar.remainingQuiteTime;

import android.os.CountDownTimer;

import java.util.ArrayList;
import java.util.List;

public class QuiteTimeTimer {

    private static final int millisInSecond = 1000;

    private CountDownTimer timer;
    private final List<QuiteTimeTimerListener> timerListeners = new ArrayList<>();

     public QuiteTimeTimer() {
         timer = new CountDownTimer(Long.MAX_VALUE, millisInSecond) {
             @Override
             public void onTick(long l) {
                 Integer removePosition = null;
                 for (QuiteTimeTimerListener timerListener : timerListeners) {
                     if(removePosition == null) {
                         removePosition = timerListener.onTick();
                     } else {
                         timerListener.onTick();
                     }
                 }

                 if(removePosition != null) {
                     //unsubscribed, so that the timer can stop ticking if no one is listening
                     unsubscribeFromTimer(timerListeners.get(removePosition));
                 }
             }

             @Override
             public void onFinish() {
                 //ignore
             }
         };
     }

    public void toggleSubscription(QuiteTimeTimerListener quiteTimeTimerListener) {
        if(timerListeners.contains(quiteTimeTimerListener)) {
            unsubscribeFromTimer(quiteTimeTimerListener);
        } else {
            subscribeToTimer(quiteTimeTimerListener);
        }
    }


     private void subscribeToTimer(QuiteTimeTimerListener quiteTimeTimerListener) {
         if(timerListeners.isEmpty()) {
             timer.start();
         }
         timerListeners.add(quiteTimeTimerListener);
     }

     private void unsubscribeFromTimer(QuiteTimeTimerListener quiteTimeTimerListener) {
         timerListeners.remove(quiteTimeTimerListener);
         if(timerListeners.isEmpty()) {
             timer.cancel();
         }
     }



}
