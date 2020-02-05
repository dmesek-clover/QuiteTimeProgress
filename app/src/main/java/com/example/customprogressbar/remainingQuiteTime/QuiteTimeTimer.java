package com.example.customprogressbar.remainingQuiteTime;

import android.os.CountDownTimer;

import java.util.LinkedList;
import java.util.List;

class QuiteTimeTimer {

    private static final int millisInSecond = 1000;

    private CountDownTimer timer;
    private List<QuiteTimeTimerListener> timerListeners = new LinkedList<>();
    private boolean removeAll = false;

     QuiteTimeTimer() {
         timer = new CountDownTimer(Long.MAX_VALUE, millisInSecond) {
             @Override
             public void onTick(long l) {
                 Integer removePosition = null;
                 for (QuiteTimeTimerListener timerListener : timerListeners) {
                     if(removeAll) {
                         deleteAllSubscribers();
                         removeAll = false;
                         break;
                     }
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

     void removeAllSubscribers() {
         removeAll = true;
     }


     void subscribeToTimer(QuiteTimeTimerListener quiteTimeTimerListener) {
         if(timerListeners.isEmpty()) {
             timer.start();
         }
         timerListeners.add(quiteTimeTimerListener);
     }

    void unsubscribeFromTimer(QuiteTimeTimerListener quiteTimeTimerListener) {
         timerListeners.remove(quiteTimeTimerListener);
         if(timerListeners.isEmpty()) {
             timer.cancel();
         }
     }

     private void deleteAllSubscribers() {
//         timer.cancel();
         timerListeners = new LinkedList<>();
     }



}
