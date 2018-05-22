package com.example.android.concurrency;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

/*
The loader architecture represents yet another way of managing asynchronous
operations in Android. The loader does a lot of its work in a background
thread and it lets the user interface remain responsive
 */

public class MainActivity extends AppCompatActivity
implements LoaderManager.LoaderCallbacks<Cursor>{
    // Have the Activity implement the LoaderCallbacks interface
    // When iI retrieve data, I'm going to be returning a cursor object,
    // and I'll be able to read the data through the cursor

    private static final String TAG = "CodeRunner";
    private static final int REQUEST_CODE_PERMISSION = 1001;

    // A projection is a declaration of a set of columns that you'll
    // be retrieving from the data source
    private static final String[] PROJECTION = {

        // To identify those columns I'll use constants
        Contacts._ID,
        Contacts.LOOKUP_KEY,
        Contacts.DISPLAY_NAME_PRIMARY
    };

    // List of columns I want to look at just for display purposes
    private static final String[] FROM_COLUMNS = {

            // Im only going to display 1 column,
            // The one containing the display name of the contact
            Contacts.DISPLAY_NAME_PRIMARY
    };

    // integer array of resource Id's to display the data in
    // Indicate which text View to display the data in from the column
    // Contacts.DISPLAY_NAME_PRIMARY
    private static final int[] TO_VIEWS = {

            // TextView for data display
            // This is the Id of a TextView in a particular layout file
            // of the Android SDK that I'll be using to display the data
            android.R.id.text1
    };

    // When the data is returned, I'll be using a CursorAdapter to bind
    // the data to the ListView that's already apart of this application
    // Declare CursorAdapter
    private CursorAdapter mCursorAdapter;

    // View object references
    private ListView mListView;
    private boolean mPermissionGranted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the logging components
        mListView = (ListView) findViewById(R.id.list_view);

        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS);
        if (permission == PackageManager.PERMISSION_GRANTED) {
            mPermissionGranted = true;
            Toast.makeText(this, "Permission already granted", Toast.LENGTH_SHORT).show();
            loadContactsData();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                this.requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},
                        REQUEST_CODE_PERMISSION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_PERMISSION &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
            mPermissionGranted = true;
            loadContactsData();
        }
    }

    // Load the data with a Loader object
    private void loadContactsData() {

        // If permission is granted...
        if (mPermissionGranted) {

            // Initialize the CursorAdapter, passing the context...
            mCursorAdapter = new SimpleCursorAdapter(this,
                    android.R.layout.simple_list_item_1, // Layout file for each list item
                    null, // Cursor with data
                    FROM_COLUMNS, // Columns I'm retrieving the data from
                    TO_VIEWS, // View's Im putting the data into
                    0) { // Flags
            };

            // Bind the data to the ListView
            mListView.setAdapter(mCursorAdapter);

            // Initialize the Loader object
            getSupportLoaderManager().initLoader(0, null, this);
        }

    }

    // When the data comes back, the onCreateLoader() method will be called
    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {

        // Return an instance of the CursorLoader class
        return new CursorLoader(
                this, // Pass the context
                Contacts.CONTENT_URI, // The URI designating the data source
                PROJECTION, // The list of columns I'm retrieving
                null, // No selection, I'm retrieving everything
                null, // No selectionArgs, I'm retrieving everything
                Contacts.DISPLAY_NAME_PRIMARY // Sort order by display name
        );
    }

    // When the data comes back in the form of a cursor...
    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {

        // Update the data in the ListView
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}