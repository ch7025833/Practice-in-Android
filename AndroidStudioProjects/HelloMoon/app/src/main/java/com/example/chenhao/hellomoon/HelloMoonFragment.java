package com.example.chenhao.hellomoon;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

/**
 * Created by chenhao on 4/5/2015.
 */
public class HelloMoonFragment extends Fragment {

    private String mPlayButtonText = null;
    private AudioPlayer mPlayer = new AudioPlayer();
    private Button mPlayButton;
    private Button mStopButton;
    private SurfaceView mSurfaceView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_hello_moon,container,false);

        //for video display
        //mSurfaceView = (SurfaceView) v.findViewById(R.id.hellomoon_surfaceview);
        //mPlayer = new AudioPlayer(mSurfaceView);

        mPlayButton = (Button) v.findViewById(R.id.hellomoon_playButton);
        //if rotation and retain the fragment,show different playbutton text.
        if (mPlayButtonText == null){
            mPlayButtonText = "Play";
            mPlayButton.setText(mPlayButtonText);
        }else{
            mPlayButton.setText(mPlayButtonText);
        }
        mPlayButton.setOnClickListener(new View.OnClickListener() {

        /*
            @Override
            public void onClick(View v) {
                mPlayer.playVideo(getActivity());
        */

            @Override
            public void onClick(View v) {

                if (mPlayer.isOver()){
                    mPlayButtonText = "Play";
                    mPlayButton.setText(mPlayButtonText);
                    return;
                }
                if (mPlayButton.getText().equals("Play")){
                    mPlayer.play(getActivity());
                    mPlayButtonText = "Pause";
                    mPlayButton.setText(mPlayButtonText);
                }else if (mPlayButton.getText().equals("Pause")){
                    mPlayer.pause();
                    mPlayButtonText = "Resume";
                    mPlayButton.setText(mPlayButtonText);
                }else if (mPlayButton.getText().equals("Resume")){
                    mPlayer.resume();
                    mPlayButtonText = "Pause";
                    mPlayButton.setText(mPlayButtonText);
                }
            }});

        mStopButton = (Button) v.findViewById(R.id.hellomoon_stopButton);
        mStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayer.stop();
                mPlayButtonText = "Play";
                mPlayButton.setText(mPlayButtonText);
            }
        });
        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPlayer.stop();
    }
}
