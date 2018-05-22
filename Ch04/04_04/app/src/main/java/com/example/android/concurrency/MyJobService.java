package com.example.android.concurrency;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

public class MyJobService extends JobService {

    public static final String TAG = "CodeRunner";

    public MyJobService() {
    }

    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        Log.i(TAG, "onStartJob: " + jobParameters.getJobId());
//        jobFinished(jobParameters, false);

        // Create an instance of the Runnable interface
        Runnable r = new Runnable() {
            @Override
            public void run() {

                // Simulate a long running task
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Log job complete message
                Log.i(TAG, "run: job complete");

                // Broadcast a message using the LocalBroadcastManager
                LocalBroadcastManager.getInstance(getApplicationContext())
                        .sendBroadcast(new Intent("ServiceMessage"));

                // Finish the job
                jobFinished(jobParameters, false);
            }
        };

        // Create a new Thread object
        Thread thread = new Thread(r);

        // Start the Thread
        thread.start();

        // Return true if Service on a separate thread
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.i(TAG, "onStopJob: " + jobParameters.getJobId());
        return false;
    }

}
