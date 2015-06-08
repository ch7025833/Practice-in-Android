package com.example.chenhao.geoquiz;

/**
 * Created by chenhao on 4/1/2015.
 */
public class TrueFalse {

    private int mQuestion;

    private boolean mTrueQuestion;


    public int getQuestion(){
        return mQuestion;
    }

    public void setQuestion(int question){
        mQuestion = question;
    }

    public boolean isTrueQuestion(){
        return mTrueQuestion;
    }

    public void setTrueQuestion(boolean trueQuestion){
        mTrueQuestion = trueQuestion;
    }

    public TrueFalse(int question, boolean trueQuestion){
        //String is stored in R.java as an integer.
        mQuestion  = question;
        mTrueQuestion = trueQuestion;
    }
}
