package com.example.customprogressbar.quiteTimeStats;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

class ProgressAnimator{

    private final List<ProgressBar> progressBars;
    private final List<QuiteTimeModel> quiteTimeInfo;
    private final List<DayOfWeekProgress> progressAnimators = new ArrayList<>();

    public ProgressAnimator(List<ProgressBar> progressBars, List<QuiteTimeModel> quiteTimeInfo) {
        this.progressBars = progressBars;
        this.quiteTimeInfo = quiteTimeInfo;
        initializeProgressAnimators();
    }

    //start the chain reaction
    public void startAnimation() {
        if(!progressAnimators.isEmpty()) {
            progressAnimators.get(0).startAnimation();
        }
    }

    private void initializeProgressAnimators() {
        int progressBarIndex = 0;
        for(ProgressBar progressBar: progressBars) {
            int progress = quiteTimeInfo.get(progressBarIndex).getUsedAmount();
            progressAnimators.add(new DayOfWeekProgress(progressBar, progress));
            progressBarIndex++;
        }

        for(int animatorIndex = 0; animatorIndex < progressAnimators.size(); animatorIndex++) {
            try {
                progressAnimators.get(animatorIndex).setNextAnimator(progressAnimators.get(animatorIndex+1));
            }catch(IndexOutOfBoundsException ignore){ }
        }
    }


    private class DayOfWeekProgress implements CustomAnimationListener{

        private static final int animationDuration = 2000; //ms

        private final ProgressBar dayOfWeekProgressBar;
        private CustomAnimationListener nextProgressBar;
        private ObjectAnimator objectAnimator;
        private int progress;

        DayOfWeekProgress(ProgressBar dayOfWeekProgressBar, int progress) {
            this.dayOfWeekProgressBar = dayOfWeekProgressBar;
            initializeObjectAnimator(progress);
        }

        void setNextAnimator(CustomAnimationListener nextProgressBar) {
            this.nextProgressBar = nextProgressBar;
        }

        void startAnimation() {
            objectAnimator.start();
        }

        @Override
        public void onCustomStart() {
            objectAnimator.start();
        }

        //used by ObjectAnimator
        private void setProgress(int progress) {
            this.progress = progress;
            dayOfWeekProgressBar.setProgress(progress);
        }

        private int getProgress() {
            return progress;
        }

        private void initializeObjectAnimator(int progress) {
            objectAnimator = ObjectAnimator.ofInt(this, "progress", progress);
            objectAnimator.setDuration(animationDuration);
            objectAnimator.setInterpolator(new DecelerateInterpolator());
            objectAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    //nothing for now
                }

                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    if(nextProgressBar != null) {
                        nextProgressBar.onCustomStart();
                    }
                }
            });
        }
    }

}

interface CustomAnimationListener {
    void onCustomStart();
}
