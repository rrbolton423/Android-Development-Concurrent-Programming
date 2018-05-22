package com.example.android.concurrency;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.android.concurrency.model.DataItem;
import com.example.android.concurrency.services.MyWebService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "CodeRunner";

    // View object references
    private ScrollView mScroll;
    private TextView mLog;
    private ProgressBar mProgressBar;

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

    //  Run some code, called from the onClick event in the layout file
    public void runCode(View v) {

        // Create an instance of the MyWebService interface
        MyWebService myWebService =
                MyWebService.retrofit.create(MyWebService.class);

        // Declare an instance of the call object that the service is going
        // to return, and get a reference to that object from the dataItems()
        // method of my web service
        Call<List<DataItem>> call = myWebService.dataItems();

        // Make the call asynchronously, sent out in th background
        call.enqueue(new Callback<List<DataItem>>() {

            // Called if a response was received
            @Override
            public void onResponse(Call<List<DataItem>> call, Response<List<DataItem>> response) {

                // If the response is successful...
                if (response.isSuccessful()) {

                    // Loop through the response's body
                    for (DataItem item: response.body()) {

                        // Display each DataItem object
                        log(item.toString());
                    }
                }
            }

            // Called if a response was NOT received
            @Override
            public void onFailure(Call<List<DataItem>> call, Throwable t) {

            }
        });
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