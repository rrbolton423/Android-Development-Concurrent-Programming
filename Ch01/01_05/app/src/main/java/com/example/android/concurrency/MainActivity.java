package com.example.android.concurrency;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "CodeRunner";

    // View object references
    private ScrollView mScroll;
    private TextView mLog;
    private ProgressBar mProgressBar;

    // If you have multiple tasks or instances of a task to execute
    // in the background, you can line them up and execute them
    // simultaneously with the ThreadExecutor

    // Create a ThreadExecutor
    ExecutorService mExecutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the logging components
        mScroll = (ScrollView) findViewById(R.id.scrollLog);
        mLog = (TextView) findViewById(R.id.tvLog);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);

        mLog.setText(R.string.lorem_ipsum);

        // Instantiate the ThreadExecutor...
        // creating a pool of 5 threads
        // Can only execute 5 threads at a time
        mExecutor = Executors.newFixedThreadPool(5);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Shut down the ThreadExecutor
        mExecutor.shutdown();
    }

    //  Run some code, called from the onClick event in the layout file
    public void runCode(View v) {
        // Create executor service

        // Loop 10 times
        for (int i = 0; i < 10; i++) {

            // Create an instance of my runnable task
            // Passing in "i" in the constructor for the thread number
            Runnable worker = new BackgroundTask(i);

            // Have the ThreadExecutor execute each task
            // Will execute only 5 at a time simultaneously
            mExecutor.execute(worker);
        }
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