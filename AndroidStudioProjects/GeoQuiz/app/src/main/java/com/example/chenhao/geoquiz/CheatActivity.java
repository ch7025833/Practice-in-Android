package com.example.chenhao.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by chenhao on 4/2/2015.
 */
public class CheatActivity extends Activity {

    private static final String TAG = "CheatActivity";
    public static final String EXTRA_CHEAT_ANSWER = "com.example.chenhao.geoquiz.cheat_answer";
    public static final String EXTRA_IF_CHEAT = "com.example.chenhao.geoquiz.if_cheat";

    private boolean mAnswer;
    private boolean mifCheated;

    private TextView mAnswerTextView;
    private TextView mAPITextView;
    private Button mShowAnswerButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (savedInstanceState != null){
            //when rotated,retrieve the cheated flag by EXTRA_IF_CHEAT,if not,default is false.
            mifCheated = savedInstanceState.getBoolean(EXTRA_IF_CHEAT,false);
        }
        setContentView(R.layout.activity_cheat);

        setCheatResult(mifCheated);

        mAnswer = getIntent().getBooleanExtra(EXTRA_CHEAT_ANSWER,false);

        mAnswerTextView = (TextView) findViewById(R.id.answerTextView);
        mShowAnswerButton = (Button) findViewById(R.id.showAnswerButton);
        mAPITextView = (TextView) findViewById(R.id.APILevelTextView);

        StringBuilder API = new StringBuilder();
        API.append("API level ");
        API.append(Build.VERSION.SDK_INT);
        mAPITextView.setText(API.toString());

        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAnswer){
                    mAnswerTextView.setText(R.string.true_button);
                }else{
                    mAnswerTextView.setText(R.string.false_button);
                }
                setCheatResult(true);
            }
        });
    }

    private void setCheatResult(boolean ifCheated){
        mifCheated = ifCheated;
        Intent data = new Intent();
        data.putExtra(EXTRA_IF_CHEAT,ifCheated);
        setResult(RESULT_OK,data);//result code can be OK,CANCELED,FIRST_USER,these are pre-defined constants,default is RESULT.CANCELED(the parent activity should use startActivityForResult,otherwise,no result code)
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //save ifCheated flag.
        super.onSaveInstanceState(outState);
        Log.i(TAG,"CheatActivity onSaveInstanceState() called");
        outState.putBoolean(EXTRA_IF_CHEAT,mifCheated);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("TAG","CheatActivity onPause called");
    }
}
