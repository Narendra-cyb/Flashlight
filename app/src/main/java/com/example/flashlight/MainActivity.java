package com.example.flashlight;

import androidx.annotation.ColorInt;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton flashlightButton;
    private boolean isFlashlightOn = false;
    private CameraManager cameraManager;
    private String cameraId;
    private ImageView flashlightImageView;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        flashlightButton = findViewById(R.id.flashlightButton);
        flashlightImageView = findViewById(R.id.flashlightImageView);

        sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        isFlashlightOn = sharedPreferences.getBoolean("isFlashlightOn", false);

        // Initialize the Camera Manager
        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            // Get the camera ID of the first available camera
            cameraId = cameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

        flashlightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (isFlashlightOn) {
                        // Turn off the flashlight
                        cameraManager.setTorchMode(cameraId, false);
                        flashlightButton.setBackgroundColor(Color.GREEN);
                        flashlightImageView.setImageResource(R.drawable.flashlight_off);
                        isFlashlightOn = false;
                    } else {
                        // Turn on the flashlight
                        cameraManager.setTorchMode(cameraId, true);
                        flashlightImageView.setImageResource(R.drawable.flashlight_on);
                        isFlashlightOn = true;
                    }
                    sharedPreferences.edit().putBoolean("isFlashlightOn", isFlashlightOn).apply();
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
            }
        });
        if (isFlashlightOn) {
            flashlightImageView.setImageResource(R.drawable.flashlight_on);
        } else {

            flashlightButton.setBackgroundColor(Color.GREEN);
            flashlightImageView.setImageResource(R.drawable.flashlight_off);
        }
    }
}


//Note: The code above uses the Camera2 API and requires API level 23 or




