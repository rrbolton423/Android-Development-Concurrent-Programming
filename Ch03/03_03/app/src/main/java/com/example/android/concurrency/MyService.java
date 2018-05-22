package com.example.android.concurrency;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

// Extend Service
public class MyService extends Service {

    // Create TAG for logging
    private static final String TAG = "CodeRunner";

    // Create a field that returns a Binder object
    private final Binder mBinder = new ServiceBinder();

    // Required Constructor
    public MyService() {
        Log.i(TAG, "MyService: service created");
    }

    // onBind() method returns an implementation of an interface called
    // "IBinder". The job of the developer is to construct that object
    // so it can be returned.
    @Override
    public IBinder onBind(Intent intent) {

        // Log lifecycle methods
        Log.i(TAG, "onBind");

        // Return the Binder object
        return mBinder;
    }

    // Create a ServiceBinder class extending Binder
    class ServiceBinder extends Binder {

        // Create a method that returns an instance of this Service class
        MyService getService() {

            // Return the Service
            return MyService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Log lifecycle methods
        Log.i(TAG, "onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // Log lifecycle methods
        Log.i(TAG, "onDestroy");
    }

    @Override
    public boolean onUnbind(Intent intent) {

        // Log lifecycle methods
        Log.i(TAG, "onUnbind: service"  );

        // Return the call to super()
        return super.onUnbind(intent);
    }
}
