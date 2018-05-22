package com.example.android.concurrency.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

public class MyIntentService extends IntentService {
    private static final String ACTION_FOO = "com.example.android.concurrency.action.FOO";

    private static final String EXTRA_PARAM1 = "com.example.android.concurrency.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.example.android.concurrency.extra.PARAM2";
    public static final String TAG = "CodeRunner";
    public static final String SERVICE_MESSAGE = "ServiceMessage";
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
        sendMessage("handleActionFoo: service started");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sendMessage("handleActionFoo: service finished");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sendMessage("onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sendMessage("onDestroy");
    }

    private void sendMessage(String message) {
        Intent intent = new Intent(SERVICE_MESSAGE);
        intent.putExtra(MESSAGE_KEY, message);
        LocalBroadcastManager.getInstance(getApplicationContext())
                .sendBroadcast(intent);
    }
}
