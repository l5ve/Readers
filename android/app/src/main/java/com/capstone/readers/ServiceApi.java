package com.capstone.readers;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

import com.capstone.readers.item.JoinResponse;
import com.capstone.readers.item.JoinData;
import com.capstone.readers.item.LoginResponse;
import com.capstone.readers.item.LoginData;

public interface ServiceApi {
    public final static String API_URL = "http://ec2-52-78-23-232.ap-northeast-2.compute.amazonaws.com/";

    @POST("/user/login")
    Call<LoginResponse> userLogin(@Body LoginData data);

    @POST("/user/join")
    Call<JoinResponse> userJoin(@Body JoinData data);
}
