package com.example.chenhao.geoquiz;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.internal.widget.ActivityChooserModel;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.io.Serializable;
import java.util.HashMap;


public class QuizActivity extends ActionBarActivity {


    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String CHEATER_STATE = "keep track of every question cheated state";
    /*
    private ImageButton mLeftImageButton;
    private ImageButton mRightImageButton;
    */
    private Button mTrueButton;
    private Button mFalseButton;

    private Button mNextButton;
    private Button mPrevButton;

    private Button mCheatButton;

    private TextView mQuestionTextView;


    private TrueFalse[] myQuestionBank = new TrueFalse[]{
            new TrueFalse(R.string.question_africa,false),
            new TrueFalse(R.string.question_america,true),
            new TrueFalse(R.string.question_asia,true),
            new TrueFalse(R.string.question_mideast,false),
            new TrueFalse(R.string.question_oceans,true),
    };

    private boolean[] mCheatingState;//keep track of the cheating state of every question in case of bad guys use the next question to get around the single variable of the question.
    //initialization block for boolean array
    {
        mCheatingState = new boolean[myQuestionBank.length];
        for (int i = 0; i < myQuestionBank.length; i++){
            mCheatingState[i] = false;
        }

    }

    private int mCurrentIndex = 0;


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG,"QuizActivity onSaveInstanceState() called");
        outState.putInt(KEY_INDEX,mCurrentIndex);
        outState.putBooleanArray(CHEATER_STATE, mCheatingState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null){
            return;
        }
        if (requestCode == 0 && resultCode == RESULT_OK){
            mCheatingState[mCurrentIndex] = data.getBooleanExtra(CheatActivity.EXTRA_IF_CHEAT, false);
        }
    }

    @TargetApi(11)//tell Lint not to check the ActionBar below.Use it only if you deal with the if condition of ActionBar.
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d(TAG, "onCreate(Bundle) called");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        if (savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX,0);
            mCheatingState = savedInstanceState.getBooleanArray(CHEATER_STATE);
        }

        //new method above min version.Can use Lint to inspect code to find it.Because build SDK is higher,so compiler maybe OK with it.So use condition to check for it
        //Build.VERSION.SDK_INT is the device SDK.But Lint still reports error about this.Insert annotation
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            android.support.v7.app.ActionBar actionBar = getSupportActionBar();
            actionBar.setSubtitle("Bodies of Water");
        }


        mTrueButton = (Button) findViewById(R.id.true_button);
        mFalseButton = (Button) findViewById(R.id.false_button);

        mNextButton = (Button) findViewById(R.id.next_button);
        mPrevButton = (Button) findViewById(R.id.pre_button);

        mCheatButton = (Button) findViewById(R.id.cheat_button);
        /*
        mLeftImageButton = (ImageButton) findViewById(R.id.imageLeftButton);
        mRightImageButton = (ImageButton) findViewById(R.id.imageRightButton);
        */
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);




        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % myQuestionBank.length;
                updateQuestion();
            }
        });

        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });
        mFalseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                checkAnswer(false);
            }
        });


        mNextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mCurrentIndex = ( mCurrentIndex + 1 ) % myQuestionBank.length;
                updateQuestion();
            }
        });

        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentIndex == 0){
                    mCurrentIndex = myQuestionBank.length;
                }
                mCurrentIndex = (mCurrentIndex - 1) % myQuestionBank.length;
                updateQuestion();
            }
        });

        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start the CheatActivity use Intent.
                Intent i = new Intent(QuizActivity.this, CheatActivity.class);
                i.putExtra(CheatActivity.EXTRA_CHEAT_ANSWER,myQuestionBank[mCurrentIndex].isTrueQuestion());
                startActivityForResult(i,0);//the second argument used for distinguish with multiple child activities started by this activity.Here,the request code is 0.
            }
        });

        /* ImageButton method
        mLeftImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % myQuestionBank.length;
                updateQuestion();
            }
        });

        mRightImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentIndex == 0){
                    mCurrentIndex = myQuestionBank.length;
                }
                mCurrentIndex = (mCurrentIndex - 1 ) % myQuestionBank.length;
                updateQuestion();
            }
        });
        */

        updateQuestion();
    }

    private void updateQuestion(){
        mQuestionTextView.setText(myQuestionBank[mCurrentIndex].getQuestion());
    }

    private void checkAnswer(boolean userPressButton){

        boolean trueAnswer = myQuestionBank[mCurrentIndex].isTrueQuestion();

        int messageStringID = 0;

        if (mCheatingState[mCurrentIndex]) {
            messageStringID = R.string.judgment_toast;
        }else{
            if (trueAnswer == userPressButton) {
                messageStringID = R.string.correct_toast;
            } else {
                messageStringID = R.string.incorrect_toast;
            }
        }

        Toast.makeText(QuizActivity.this,messageStringID,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG,"onStart() called");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG,"onPause() called");
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG,"onResume() called");
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG,"onStop() called");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG,"onDestroy called");
    }

}
