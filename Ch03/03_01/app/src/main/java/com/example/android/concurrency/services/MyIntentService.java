package com.example.android.concurrency.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyIntentService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.example.android.concurrency.action.FOO";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.example.android.concurrency.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.example.android.concurrency.extra.PARAM2";
    public static final String TAG = "CodeRunner";

    // Create a constructor, passing the name of the Service
    // to the super() method so the System can identify it (REQUIRED!)
    public MyIntentService() {
        super("MyIntentService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {

        /*
        This code launches the Service
         */

        // Create an Intent to the IntentService
        Intent intent = new Intent(context, MyIntentService.class);

        // Identify what action is being requested
        intent.setAction(ACTION_FOO);

        // Add extras data to the intent
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);

        // Start the Service
        context.startService(intent);
    }

    // onHandleIntent() is called automatically by the framework.
    // Within this method, it's the job of the developer to look at the
    // intent object that's passed in, and figure out whats being requested
    @Override
    protected void onHandleIntent(Intent intent) {

        // If the Intent isn't null...
        if (intent != null) {

            // Get the action of the Intent
            final String action = intent.getAction();

            // If it's the action we're looking for...
            if (ACTION_FOO.equals(action)) {

                // Extract the parameters from the intent object
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);

                // Pass the parameters to the method that handles the action
                handleActionFoo(param1, param2);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {

        // Log Service started message
        Log.i(TAG, "handleActionFoo: service started!");

        // Sleep the Thread for 3 seconds
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Log Service finished message
        Log.i(TAG, "handleActionFoo: service finished!");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Log lifecycle method
        Log.i(TAG, "onCreate: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // Log lifecycle method
        Log.i(TAG, "onDestroy: ");
    }
}
