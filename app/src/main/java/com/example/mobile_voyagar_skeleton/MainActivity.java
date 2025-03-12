package com.example.mobile_voyagar_skeleton;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback {
    private static final String TAG = "VoyagAR-Main";

    private SurfaceView surfaceView;
    private RenderThread renderThread;

    // Load the native library - update to match your CMake output library name
    static {
        System.loadLibrary("voyagAR");
    }

    // Native methods
    public native void nativeInit();
    public native void nativeSurfaceCreated(Surface surface);
    public native void nativeSurfaceChanged(int width, int height);
    public native void nativeSurfaceDestroyed();
    public native void nativeRender();
    public native void nativeQuit();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Create a SurfaceView
        surfaceView = new SurfaceView(this);
        setContentView(surfaceView);

        // Add surface holder callback
        surfaceView.getHolder().addCallback(this);

        // Initialize native code
        nativeInit();

        Log.i(TAG, "SDL Activity created");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (renderThread != null) {
            renderThread.setRunning(true);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (renderThread != null) {
            renderThread.setRunning(false);
        }
    }

    @Override
    protected void onDestroy() {
        if (renderThread != null) {
            renderThread.setRunning(false);
            try {
                renderThread.join();
            } catch (InterruptedException e) {
                Log.e(TAG, "Error stopping render thread: " + e.getMessage());
            }
            renderThread = null;
        }

        nativeQuit();
        super.onDestroy();
    }

    // SurfaceHolder.Callback methods
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.i(TAG, "Surface created");
        nativeSurfaceCreated(holder.getSurface());

        // Start the render thread
        renderThread = new RenderThread();
        renderThread.setRunning(true);
        renderThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.i(TAG, "Surface changed: " + width + "x" + height);
        nativeSurfaceChanged(width, height);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.i(TAG, "Surface destroyed");

        // Stop the render thread
        if (renderThread != null) {
            renderThread.setRunning(false);
            try {
                renderThread.join();
            } catch (InterruptedException e) {
                Log.e(TAG, "Error stopping render thread: " + e.getMessage());
            }
            renderThread = null;
        }

        nativeSurfaceDestroyed();
    }

    // Render thread class
    private class RenderThread extends Thread {
        private volatile boolean running = false;
        private static final long TARGET_FPS = 60;
        private static final long FRAME_TIME_MS = 1000 / TARGET_FPS;

        public void setRunning(boolean running) {
            this.running = running;
        }

        @Override
        public void run() {
            long startTime;
            long sleepTime;

            while (running) {
                startTime = System.currentTimeMillis();

                // Call native render method
                nativeRender();

                // Sleep to maintain target FPS
                sleepTime = FRAME_TIME_MS - (System.currentTimeMillis() - startTime);
                if (sleepTime > 0) {
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        // Ignore
                    }
                }
            }
        }
    }
}