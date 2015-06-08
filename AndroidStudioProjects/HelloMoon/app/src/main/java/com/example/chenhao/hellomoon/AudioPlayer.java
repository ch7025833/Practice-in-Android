package com.example.chenhao.hellomoon;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * Created by chenhao on 4/5/2015.
 */
public class AudioPlayer {

    private MediaPlayer mPlayer = null;
    private int mCurrentPosition;
    private boolean isOver = false;
    private SurfaceView mSurfaceView;

    public AudioPlayer(SurfaceView surfaceView){
        mSurfaceView = surfaceView;
    }

    public AudioPlayer(){}

    public void stop(){
        if (mPlayer != null){
            mPlayer.release();//destroy the instance
            mPlayer = null;
        }
    }

    public void play(Context c){
        //prevent user create multiple mediaplayer like press Play button twice.
        stop();

        //context is used to let mediaplayer identify the audio file's resource ID.

        mPlayer = MediaPlayer.create(c,R.raw.one_small_step);

        //mPlayer.setDisplay(mSurfaceView.getHolder());
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stop();
                isOver = true;
            }
        });
        mPlayer.start();
    }

    public void playVideo(Context c)  {
        //stop();

        //Uri resourceURI = Uri.parse("android.resource://" + "com.example.chenhao.hellomoon/raw/apollo_17_stroll");
        mPlayer = MediaPlayer.create(c,R.raw.one);
        mPlayer.setDisplay(mSurfaceView.getHolder());
        try {
            mPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        mPlayer.start();
    }

    public void pause(){
        if (mPlayer != null){
            mPlayer.pause();
            mCurrentPosition = mPlayer.getCurrentPosition();
        }
    }

    public void resume(){
        if (mPlayer != null){
            mPlayer.seekTo(mCurrentPosition);
            mPlayer.start();
        }
    }

    public boolean isOver(){
        return isOver;
    }
}
