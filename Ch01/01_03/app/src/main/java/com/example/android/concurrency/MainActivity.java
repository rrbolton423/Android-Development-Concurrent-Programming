package com.example.android.concurrency;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "CodeRunner";

    // View object references
    private ScrollView mScroll;
    private TextView mLog;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the logging components
        mScroll = (ScrollView) findViewById(R.id.scrollLog);
        mLog = (TextView) findViewById(R.id.tvLog);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);

        mLog.setText(R.string.lorem_ipsum);
    }

    //  Run some code, called from the onClick event in the layout file
    public void runCode(View v) {

        // Log message
        log("Running code");

        // Display progress bar
        displayProgressBar(true);

        // Create a Runnable task
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                // Log start
                Log.i(TAG, "run: starting thread for 4 seconds");

                // Sleep the thread for 4 seconds
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Log end
                Log.i(TAG, "run: ending thread");
            }
        };
//        Handler handler = new Handler();
//        handler.postDelayed(runnable, 3000);

        // Create a new Thread, passing the runnable object
        Thread thread = new Thread(runnable);

        // Create an run the new Thread in the background
        thread.start();

    }

    //  Clear the output, called from the onClick event in the layout file
    public void clearOutput(View v) {
        mLog.setText("");
        scrollTextToEnd();
    }

    //  Log output to logcat and the screen
    private void log(String message) {
        Log.i(TAG, message);
        mLog.append(message + "\n");
        scrollTextToEnd();
    }

    private void scrollTextToEnd() {
        mScroll.post(new Runnable() {
            @Override
            public void run() {
                mScroll.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    @SuppressWarnings("unused")
    private void displayProgressBar(boolean display) {
        if (display) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }
}