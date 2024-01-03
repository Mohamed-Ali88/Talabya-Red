package com.aliProApp.aliPro_Paints.Interface;

import com.aliProApp.aliPro_Paints.Domain.PlaceName;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;

public interface MainPlaces {
    @GET("government-regions")
    Call<PlaceName> getPlaces(@Body PlaceName place_name);
}
