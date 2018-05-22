package com.example.android.concurrency;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
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

    // Add a field that represents the Service itself
    private MyService mService;

    // Create a Service connection field
    private final ServiceConnection mServiceConn = new ServiceConnection() {
        /*
        Implement the 2 required methods...
         */

        // Called when the Service is connected
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

            // Get a reference to the Binder object
            MyService.ServiceBinder serviceBinder =
                    (MyService.ServiceBinder) iBinder;

            // Get a reference to the service and save it to
            // the field that is declared using the Binder object
            mService = serviceBinder.getService();

            // Log connected message
            Log.i(TAG, "onServiceConnected");
        }

        // Called when the Service is disconnected
        @Override
        public void onServiceDisconnected(ComponentName componentName) {

            // If the Service isn't null...
            if (mService != null) {

                // Null the Service
                mService = null;
            }

            // Log disconnected message
            Log.i(TAG, "onServiceDisconnected: ");
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

        // Create an Intent to the Service
        Intent serviceIntent = new Intent(this, MyService.class);

        // Bind to the Service
        bindService(serviceIntent, mServiceConn, Context.BIND_AUTO_CREATE);

        // Log bound message
        Log.i(TAG, "onStart: service bound");
    }

    @Override
    protected void onStop() {
        super.onStop();

        // Unbind from the Service
        unbindService(mServiceConn);

        // Log unbound message
        Log.i(TAG, "onStop: service unbound");
    }

    //  Run some code, called from the onClick event in the layout file
    public void runCode(View v) {

        // Call a method of the Service after it is connected
        log(mService.getValue());
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