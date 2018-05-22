package com.example.android.concurrency;

import android.os.AsyncTask;
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

        // Create an instance of my AsyncTask
        MyTask task = new MyTask();

        // Execute the Task, and pass in the values it requires
        task.execute("Red", "Green", "Blue");

        // Create a second instance of my AsyncTask
        MyTask task2 = new MyTask();

        // Execute the second Task, and pass in the values it requires
        task2.execute("Pink", "Orange", "Purple");
    }

    //  Clear the output, called from the onClick event in the layout file
    public void clearOutput(View v) {
        mLog.setText("");
        scrollTextToEnd();
    }

    //  Log output to logcat and the screen
    @SuppressWarnings("unused")
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

    // Param 1: Value passed to the task
    // Param 2: Type of progress value
    // Param 3: Value returned from the task
    class MyTask extends AsyncTask<String, String, String> {

        // Override / Implement the doInBackground() method
        @Override
        protected String doInBackground(String... strings) {

            /*
            This code executes in a background Thread
             */

            // Loop through the String values passed into the Task
            for (String value: strings) {

                // Log the values
                Log.i(TAG, "doInBackground: " + value);

                // Sleep the Thread for 1 second
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }
    }
}