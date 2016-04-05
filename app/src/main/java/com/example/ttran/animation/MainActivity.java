package com.example.ttran.animation;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.PlaybackParams;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.Lock;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    private SeekBar volumeSeekbar = null;
    private AudioManager audioManager = null;
    Timer mTimer;
    ImageAnimator animator;
    ImageView imageView;
    Handler mHandler;

    public MainActivity(){

    }
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    public class ShowTime extends Thread{
        int res [] = {R.drawable.pic1, R.drawable.pic2,R.drawable.pic3,R.drawable.pic4,R.drawable.pic5,R.drawable.pic6,R.drawable.pic7};
        Thread t;
        int mCount = 0;
        private ImageView img;
        boolean mVisible = true;
        public ShowTime(){
            img = (ImageView) findViewById(R.id.pic);
        }
        public void run() {
            Looper.prepare();
//            for (int i=0; i < 12; i++){
            while (mediaPlayer.isPlaying()){
                 try {
                    execute();

                    Thread.sleep(7000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            Looper.loop();
            Thread.currentThread().notify();
        }

        public void start(){
            if (t == null){
                t = new Thread(this);
            }
            t.start();
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
            //ImageView pic = img;

            if (img != null){
                if (mVisible){
                    fade(img);
                    mVisible = false;
                } else {
                    fade(img);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mCount >= res.length){
                                mCount = 0;
                            }
                            imageView.setImageResource(res[mCount]);
                            imageView.refreshDrawableState();
                        }
                    });
                    mVisible = true;
                    mCount++;
                }
                Log.i("Count = ", String.valueOf(mCount));

            }
        }
    }


    public void playShow(){
        imageView = (ImageView) findViewById(R.id.pic);
        ShowTime showTime = new ShowTime();
        showTime.start();
        ImageAnimator animator = new ImageAnimator(imageView);
        animator.start();
    }

    public void playImgAnimator(View v) throws InterruptedException {
        imageView = (ImageView) findViewById(R.id.pic);
            ShowTime showTime = new ShowTime();

            showTime.start();
//        ImageAnimator animator = new ImageAnimator(imageView);
//        animator.start();

    }

    public void playMusic(View v) {
        mediaPlayer = MediaPlayer.create(this, R.raw.flashdance);
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }

        playShow();

    }

    public void stopMusic(View v) {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }

    }

    public void raiseVolume(View v) {
        if(volumeSeekbar != null){
            int progress = volumeSeekbar.getProgress();
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,progress,0);
        }

       mediaPlayer.setVolume(1.0f, 1.0f);
    }

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        initControls();
        }

    private void initControls(){
        try
        {
            volumeSeekbar = (SeekBar)findViewById(R.id.seekBar1);
            audioManager = (AudioManager) getSystemService(this.AUDIO_SERVICE);
            volumeSeekbar.setMax(audioManager
                    .getStreamMaxVolume(AudioManager.STREAM_MUSIC));
            volumeSeekbar.setProgress(audioManager
                    .getStreamVolume(AudioManager.STREAM_MUSIC));


            volumeSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
            {
                @Override
                public void onStopTrackingTouch(SeekBar arg0)
                {
                }

                @Override
                public void onStartTrackingTouch(SeekBar arg0)
                {
                }

                @Override
                public void onProgressChanged(SeekBar arg0, int progress, boolean arg2)
                {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                            progress, 0);
                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.ttran.animation/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.ttran.animation/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
        stopMusic(new View(this));
    }
}
