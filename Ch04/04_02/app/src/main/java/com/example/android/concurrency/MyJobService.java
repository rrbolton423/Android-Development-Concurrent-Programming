package com.example.android.concurrency;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

// This kind of Service extends "JobService"
public class MyJobService extends JobService {

    // Tag for logging
    private static final String TAG = "CodeRunner";

    // Required Constructor
    public MyJobService() {
    }

    // onStartJob() will be called by the scheduler in order to execute
    // the task. This is where you put your code that indicates what you
    // want to do in this job
    @Override
    public boolean onStartJob(JobParameters jobParameters) {

        // Log the start and this specific job id
        Log.i(TAG, "onStartJob: " + jobParameters.getJobId());

        // When the job is complete, call the method jobFinished().
        // It takes 2 arguments, the JobParameters object, and a boolean
        // value (needsReschedule) for if you want to retry the job if
        // something went wrong.
        jobFinished(jobParameters, false);

        // Returning false means you completed the job in the current thread.
        // If instead you're using the current thread to launch a separate
        // thread or an AsyncTask, return a value of true to indicate the
        // job isn't complete yet.
        return false;
    }

    // The onStopJob() method is only called by the scheduler if the job
    // has to be stopped for some reason, before you call jobFinished().
    @Override
    public boolean onStopJob(JobParameters jobParameters) {

        // Log the stop and this specific job id
        Log.i(TAG, "onStopJob: " + jobParameters.getJobId());

        // Return false
        return false;
    }

}
