package com.example.mobile_voyagar_skeleton;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.mobile_voyagar_skeleton.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'mobile_voyagar_skeleton' library on application startup.
    static {
        System.loadLibrary("mobile_voyagar_skeleton");
    }

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Example of a call to a native method
        TextView tv = binding.sampleText;
        tv.setText(stringFromJNI());
    }

    /**
     * A native method that is implemented by the 'mobile_voyagar_skeleton' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}