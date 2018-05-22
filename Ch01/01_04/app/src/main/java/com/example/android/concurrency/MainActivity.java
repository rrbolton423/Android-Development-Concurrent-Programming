package com.example.android.concurrency;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "CodeRunner";

    // Create Message key
    private static final String MESSAGE_KEY = "message_key";

    // View object references
    private ScrollView mScroll;
    private TextView mLog;
    private ProgressBar mProgressBar;

    // Create a Handler object as a field of the MainActivity
    // so it persists for the lifetime of the activity
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the logging components
        mScroll = (ScrollView) findViewById(R.id.scrollLog);
        mLog = (TextView) findViewById(R.id.tvLog);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);

        mLog.setText(R.string.lorem_ipsum);

        // Instantiate the Handler object
        // Pass in a looper object that's associated with a
        // particular thread.
        // To get a reference to the looper for the main thread...
        // This is a way to listen for and react to messages sent from anywhere
        mHandler = new Handler(Looper.getMainLooper()) {

            // Method will be called whenever the handler receives a message
            // Now we have a way to listen and react to messages sent
            // from anywhere
            @Override
            public void handleMessage(Message msg) {

                // Receive an interpret the message
                Bundle bundle = msg.getData();

                // Get the String inside the Bundle
                String message = bundle.getString(MESSAGE_KEY);

                // Display data in the Activity's TextView
                log(message);

                // Stop showing the progress bar
                displayProgressBar(false);
            }
        };
    }

    //  Run some code, called from the onClick event in the layout file
    public void runCode(View v) {

        log("Running code");
        displayProgressBar(true);

        // This runnable code will be ran in a background thread
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "run: starting thread for 4 seconds");
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // Create a Message object
                Message message = new Message();

                // Create a Bundle object
                Bundle bundle = new Bundle();

                // Put data into Bundle object
                bundle.putString(MESSAGE_KEY, "thread is complete");

                // Add bundle to message object
                message.setData(bundle);

                // Send the message from the background Thread
                // to the handler object that listens to messages
                // on the Main Thread
                mHandler.sendMessage(message);
            }
        };

        // Create a Thread object
        Thread thread = new Thread(runnable);

        // Start the Thread
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