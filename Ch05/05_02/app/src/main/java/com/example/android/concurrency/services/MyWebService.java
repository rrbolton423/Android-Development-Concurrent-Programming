package com.example.android.concurrency.services;

import com.example.android.concurrency.model.DataItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface MyWebService {

    // Path to the base url of the web service
    String BASE_URL = "http://560057.youcanlearnit.net/";

    // Path to the specific feed of the web service
    String FEED = "services/json/itemsfeed.php";

    // Create a Retrofit object
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL) // Passing the base URL
            .addConverterFactory(GsonConverterFactory.create()) // Converter factory
            .build(); // Build

    // the method dataItems() retrieves the feed using a GET request,
    // returning a Call object, that wraps a list that contains instances
    // of the DataItem class
    @GET(FEED)
    Call<List<DataItem>> dataItems();

}
