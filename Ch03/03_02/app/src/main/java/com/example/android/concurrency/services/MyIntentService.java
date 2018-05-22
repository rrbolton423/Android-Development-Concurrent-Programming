package com.example.android.concurrency.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

public class MyIntentService extends IntentService {
    private static final String ACTION_FOO = "com.example.android.concurrency.action.FOO";

    private static final String EXTRA_PARAM1 = "com.example.android.concurrency.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.example.android.concurrency.extra.PARAM2";
    public static final String TAG = "CodeRunner";

    // Create Intent action constant
    public static final String SERVICE_MESSAGE = "ServiceMessage";

    // Create Intent extra constant
    public static final String MESSAGE_KEY = "message";

    public MyIntentService() {
        super("MyIntentService");
    }

    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, MyIntentService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFoo(param1, param2);
            }
        }
    }

    private void handleActionFoo(String param1, String param2) {

        // Send message to back to Activity's UI
        sendMessage("handleActionFoo: service started");

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Send message to back to Activity's UI
        sendMessage( "handleActionFoo: service finished");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Send message to back to Activity's UI
        sendMessage( "onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // Send message to back to Activity's UI
        sendMessage( "onDestroy");
    }

    // This method sends a message from the Service back to
    // the UI on the Main Thread
    private void sendMessage(String message) {

        // Create an Intent object, specifying a custom action
        Intent intent = new Intent(SERVICE_MESSAGE);

        // Add information to the Intent
        intent.putExtra(MESSAGE_KEY, message);

        // Send the Intent as a Broadcast message
        // Get an instance of the LocalBroadcastManager, passing
        // the application context, and the Intent to send with the Broadcast
        LocalBroadcastManager.getInstance(getApplicationContext())
                .sendBroadcast(intent);
    }
}
