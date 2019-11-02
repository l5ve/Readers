package com.capstone.readers;

import com.capstone.readers.item.JoinData;
import com.capstone.readers.item.JoinResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ServiceApi {
    @POST("/user/login")
    Call<LoginResponse> userLogin(@Body LoginData data);

    @POST("/user/join")
    Call<SigninResponse> userJoin(@Body SigninData data);
}
