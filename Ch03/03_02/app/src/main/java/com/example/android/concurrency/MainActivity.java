package com.example.android.concurrency;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.android.concurrency.services.MyIntentService;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "CodeRunner";

    // View object references
    private ScrollView mScroll;
    private TextView mLog;
    private ProgressBar mProgressBar;

    // Create a BroadcastReceiver object, and implement the
    // onReceive() method that receives a context and intent
    BroadcastReceiver mReceiver = new BroadcastReceiver() {

        // This code runs on the Main thread, so we have access to the
        // application's UI
        @Override
        public void onReceive(Context context, Intent intent) {

            // Get the message from the Intent received as a Broadcast
            String message = intent.getStringExtra(MyIntentService.MESSAGE_KEY);

            // Display the message that was received
            log(message);
        }
    };

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

    @Override
    protected void onStart() {
        super.onStart();

        // Register the BroadcastReceiver using the LocalBroadcastManager
        // by passing the application context in it's getInstance() method,
        // and in the registerReceiver() method, pass the receiver to register,
        // and an Intent filter to filter on a specific Intent
        LocalBroadcastManager.getInstance(getApplicationContext())
                .registerReceiver(mReceiver, new IntentFilter(MyIntentService.SERVICE_MESSAGE));
    }

    @Override
    protected void onStop() {
        super.onStop();

        // Unregister the BroadcastReceiver
        LocalBroadcastManager.getInstance(getApplicationContext())
                .unregisterReceiver(mReceiver);
    }

    //  Run some code, called from the onClick event in the layout file
    public void runCode(View v) {
        log("Running code");
        MyIntentService.startActionFoo(this, "Value 1", "Value 2");
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