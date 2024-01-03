package com.aliProApp.aliPro_Paints.Interface;

import com.aliProApp.aliPro_Paints.Domain.mmm;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface cartPrductPost {
    @POST("MakeOrder")
    Call<mmm> creatOrder (@Body mmm mm);
}
