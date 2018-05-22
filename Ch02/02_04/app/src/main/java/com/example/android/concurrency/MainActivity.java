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

    // Declare AsyncTask as a field
    private MyTask mTask;

    // Variable to keep track of the task's state
    private boolean mTaskRunning;

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

        // If the Task is running and the Task is not null...
        if (mTaskRunning && mTask != null) {

            // Cancel the Task
            mTask.cancel(true);

            // Set mTaskRunning to false
            mTaskRunning = false;


        } else { // If the Task isn't running and the Task is null...

            // Create the Task
            mTask = new MyTask();

            // Execute the Task
            mTask.execute("Red", "Green", "Blue");

            // Set mTaskRunning to true
            mTaskRunning = true;
        }

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

    class MyTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            for (String value :
                    strings) {

                // Check if the task is cancelled before it
                // is completed
                if (isCancelled()) {

                    // Publish progress "Cancelled"
                    publishProgress("Cancelled");

                    // Break out of the loop
                    break;
                }

                Log.i(TAG, "doInBackground: " + value);
                publishProgress(value);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "thread all done!";
        }

        @Override
        protected void onProgressUpdate(String... values) {
            log(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            log(s);
        }

        // Called when an AsyncTask is cancelled and no result
        // is returned from doInBackground(). This method runs
        // on the Main UI Thread
        @Override
        protected void onCancelled() {

            // Display cancel message
            log("Task cancelled");
        }

        // Called when an AsyncTask is cancelled and
        // receives the result if a result is returned from
        // doInBackground(). This method runs on the Main UI Thread
        @Override
        protected void onCancelled(String s) {

            // Display cancel message and result value
            log("Cancelled with result " + s);
        }
    }

}