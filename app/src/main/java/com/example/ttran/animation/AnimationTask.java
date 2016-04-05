package com.example.ttran.animation;

import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ttran on 1/30/2016.
 */
public class AnimationTask{
    Timer timer;
    ImageView imageView;
    //ImageAnimator animator;
    public AnimationTask(int seconds, ImageView view) {
        imageView = view;
        timer = new Timer();
        timer.schedule(new PerformTask(),seconds*1000);
    }
    class PerformTask extends TimerTask{

        @Override
        public void run() {
//            if (MainActivity.animator == null)
//                MainActivity.animator = new ImageAnimator(imageView);
//            MainActivity.animator.start();
        }
    }
}
