package com.example.chenhao.criminalintent;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Camera;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Created by chenhao on 4/12/2015.
 */
public class CrimeCameraFragment extends Fragment {

    private static final String TAG = "CrimeCameraFragment";
    public static final String EXTRA_PHOTO_FILENAME = "com.example.chenhao.criminalintent.photo_filename";

    private android.hardware.Camera mCamera;
    private SurfaceView mSurfaceView;
    private View mProgressContainer;
    private android.hardware.Camera.ShutterCallback mShutterCallback = new android.hardware.Camera.ShutterCallback() {
        @Override
        public void onShutter() {
            mProgressContainer.setVisibility(View.VISIBLE);
        }
    };

    private android.hardware.Camera.PictureCallback mJpegCallback = new android.hardware.Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, android.hardware.Camera camera) {
            //create the file name.
            String filename = UUID.randomUUID().toString() + ".jpg";
            //save the jpeg file to disk.
            FileOutputStream os = null;
            boolean success = true;
            try {
                os = getActivity().openFileOutput(filename,Context.MODE_PRIVATE);
                os.write(data);
            } catch (Exception e) {
                Log.e(TAG,"Error writing to file" + filename,e);
                success = false;
            } finally {
                if (os != null){
                    try {
                        os.close();
                    } catch (IOException e) {
                        Log.e(TAG,"Error closing file" + filename,e);
                        success = false;
                    }
                }
            }
            if (success){
                Intent intent = new Intent();
                intent.putExtra(EXTRA_PHOTO_FILENAME,filename);
                getActivity().setResult(Activity.RESULT_OK,intent);
            }else {
                getActivity().setResult(Activity.RESULT_CANCELED);
            }
            getActivity().finish();
        }
    };

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_crime_camera,null);

        Button takePictureButton = (Button) v.findViewById(R.id.crime_camera_takePictureButton);
        takePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCamera != null){
                    mCamera.takePicture(mShutterCallback,null,mJpegCallback);
                }
            }
        });

        //make the progress layout invisible at first.
        mProgressContainer = v.findViewById(R.id.crime_camera_progressContainer);
        mProgressContainer.setVisibility(View.INVISIBLE);

        mSurfaceView = (SurfaceView) v.findViewById(R.id.crime_camera_surfaceView);
        SurfaceHolder holder = mSurfaceView.getHolder();
        //setType() and SURFACE_TYPE_BUFFERS are both deprecated
        //but are required for Camera preview to work on pre-3.0 device

        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                //tell the camera to use this surface to preview.
                try {
                    if (mCamera != null){
                        mCamera.setPreviewDisplay(holder);
                    }
                }catch (IOException e){
                    Log.e(TAG,"Error setting up preview display",e);
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                if (mCamera == null){return;}

                //the surface has changed size;update the camera preview
                android.hardware.Camera.Parameters parameters = mCamera.getParameters();
                android.hardware.Camera.Size s = getBestSupportSize(parameters.getSupportedPreviewSizes(),width,height);
                parameters.setPreviewSize(s.width,s.height);
                s = getBestSupportSize(parameters.getSupportedPictureSizes(),width,height);
                parameters.setPictureSize(s.width,s.height);
                mCamera.setParameters(parameters);
                try {
                    mCamera.startPreview();
                }catch (Exception e){
                    Log.e(TAG,"Could not start preview",e);
                    mCamera.release();
                    mCamera = null;
                }
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
            //no longer display,stop the preview.
                if(mCamera != null){
                    mCamera.stopPreview();
                }
            }
        });
        return v;
    }

    @SuppressWarnings("deprecation")
    private android.hardware.Camera.Size getBestSupportSize(List<android.hardware.Camera.Size> sizes,int width,int height){

        android.hardware.Camera.Size bestSize = sizes.get(0);
        int largestArea = bestSize.width * bestSize.height;
        for (android.hardware.Camera.Size size : sizes){
            int area = size.width * size.height;
            if (area > largestArea){
                bestSize = size;
                largestArea = area;
            }
        }
        return bestSize;
    }

    @SuppressWarnings("deprecation")
    @TargetApi(9)
    @Override
    public void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD){
            mCamera = android.hardware.Camera.open(0);
        }else{
            mCamera = android.hardware.Camera.open();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if (mCamera != null){
            mCamera.release();
            mCamera = null;
        }
    }

}
