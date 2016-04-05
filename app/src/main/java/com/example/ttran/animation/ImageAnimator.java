package com.example.ttran.animation;

import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import android.view.View;
import android.widget.ImageView;
import android.net.Uri;
import android.app.Activity;

/**
 * Created by ttran on 1/30/2016.
 */
public class ImageAnimator extends Thread{
    int res [] = {R.drawable.pic1, R.drawable.pic2,R.drawable.pic3,R.drawable.pic4,R.drawable.pic5,R.drawable.pic6,R.drawable.pic7};
    private int mCount = 0;
    private boolean mVisible = true;
    Timer mTimer;
    Handler mHandler;
    ImageView mImgView;
    private Thread t;

    public ImageAnimator(ImageView imageView) {
        mImgView = imageView;
//        mTimer = new Timer();
//        mTimer.schedule(new PerformTask(), 1000, 2000);
//        start();


    }

//    public void run(){
//        Looper.prepare();
//
//        for (int i=0; i < 24; i++){
//            try {
//                Log.i("Info: Thread is running", String.valueOf(i));
//                ImageView pic = this.mImgView;
//
//                if (pic != null){
//                    if (mCount >= res.length){
//                        mCount = 0;
//                    }
//                    if (mVisible){
//                        fade(mImgView);
//                    } else {
//                        fade(mImgView);
//
//                        MainActivity.runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                mImgView.setImageResource(res[mCount]);
//                                mImgView.refreshDrawableState();
//                            }
//                        });
//                        mCount++;
//                    }
//                }
//                Thread.sleep(6000);
//
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        Looper.loop();
//
//    }

    public void start(){
        if (t == null){
            t = new Thread(this);
            t.start();
        }
    }

    public void fade(ImageView pic) {
        // ImageView pic = (ImageView) findViewById(R.id.pic);
        if (mVisible) {
            pic.animate().rotation(500f).setDuration(5000);
            pic.animate().alpha(0f).setDuration(5000);
            pic.animate().scaleX(0.1f).scaleY(0.1f).setDuration(5000);
            pic.animate().translationXBy(-1000f).translationYBy(-1000f).setDuration(5000);
            mVisible = false;
        } else {
            pic.animate().alpha(1f).setDuration(5000);
            pic.animate().rotation(0f).setDuration(5000);
            pic.animate().scaleX(1.0f).scaleY(1.0f).setDuration(5000);
            pic.animate().translationXBy(1000f).translationYBy(1000f).setDuration(5000);
            mVisible = true;
        }
    }

    public void execute()  {
        ImageView pic = mImgView;

        if (pic != null){
            if (mCount >= res.length){
                mCount = 0;
            }
            if (mVisible){
                fade(pic);
            } else {
                fade(pic);
                pic.setImageResource(res[mCount]);
                pic.refreshDrawableState();
                mCount++;
            }
        }
    }
}
