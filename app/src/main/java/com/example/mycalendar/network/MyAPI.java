package com.example.mycalendar.network;

import com.example.mycalendar.model.Response;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MyAPI {
    @GET("ProHussain/public/main/calendar.json")
    Call<Response> getData();
}
