package com.example.chenhao.criminalintent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import java.util.Date;

/**
 * Created by chenhao on 4/5/2015.
 */
public class SelectDialog extends DialogFragment {

    private static final String DIALOG_TIME = "time";
    private static final String DIALOG_DATE = "date";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 1;
    private static final String EXTRA_DATE = "com.example.chenhao.criminalIntent.crime_date_changed";

    private Button mSelectDate;
    private Button mSelectTime;
    private Date mDate;


    public static SelectDialog newInstance(Date date){

        Bundle args = new Bundle();
        args.putSerializable(EXTRA_DATE,date);

        SelectDialog selectDialog = new SelectDialog();
        selectDialog.setArguments(args);

        return selectDialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        mDate = (Date) getArguments().getSerializable(EXTRA_DATE);

        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_select,null);

        mSelectDate = (Button) v.findViewById(R.id.select_change_date);
        mSelectTime = (Button) v.findViewById(R.id.select_change_time);

        mSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                //CrimeFragment targetFragment = (CrimeFragment) fragmentManager.findFragmentById(R.id.fragmentContainer);
                DatePickerFragment dialog = DatePickerFragment.newInstance(mDate);
                dialog.setTargetFragment(SelectDialog.this, REQUEST_DATE);
                dialog.show(fragmentManager,DIALOG_DATE);
            }
        });

        mSelectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                //CrimeFragment targetFragment = (CrimeFragment) fragmentManager.findFragmentById(R.id.fragmentContainer);
                TimePickerFragment dialog = TimePickerFragment.newInstance(mDate);
                dialog.setTargetFragment(SelectDialog.this, REQUEST_TIME);
                dialog.show(fragmentManager,DIALOG_TIME);
            }
        });


        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .create();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK){
            return;
        }
        //update the mDate value when there is a change.
        mDate = (Date) data.getSerializableExtra(EXTRA_DATE);
        getArguments().putSerializable(EXTRA_DATE,mDate);

        if (requestCode == REQUEST_DATE || requestCode == REQUEST_TIME){
            sendResult(Activity.RESULT_OK,data);
        }
    }

    private void sendResult(int resultCode,Intent intent){
        if (getTargetFragment() == null){
            return;
        }
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(),resultCode,intent);
    }
}
