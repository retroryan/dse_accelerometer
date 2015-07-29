package com.miralak.basicaccelerometer.api;

import com.miralak.basicaccelerometer.model.Acceleration;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.POST;

public interface RestAPI {

    @POST("/acceleration")
    public Response sendAccelerationValues(@Body Acceleration acceleration);
}
