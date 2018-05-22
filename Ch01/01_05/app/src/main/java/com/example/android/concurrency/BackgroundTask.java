package com.example.android.concurrency;

import android.util.Log;

// Create a background task class that can be passed with a Thread
// or a ThreadExecutor
public class BackgroundTask implements Runnable {

    // Tag for logging
    public static final String TAG = "CodeRunner";

    // integer value to keep track of each thread
    private int threadNumber;

    // Generate constructor accepting a threadNumber argument
    public BackgroundTask(int threadNumber) {
        this.threadNumber = threadNumber;
    }

    @Override
    public void run() {

        /*
        This code tracks when a particular thread starts and finishes
         */

        // Log thread name and number
        Log.i(TAG, Thread.currentThread().getName() +
                " start, thread number = " + threadNumber);

        // Sleep the thread for 5 seconds
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Log thread name and number
        Log.i(TAG, Thread.currentThread().getName() +
                " end, thread number = " + threadNumber);
    }
}
