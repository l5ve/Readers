package com.capstone.readers;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

import com.capstone.readers.item.JoinResponse;
import com.capstone.readers.item.JoinData;
import com.capstone.readers.item.LoginResponse;
import com.capstone.readers.item.LoginData;
import com.capstone.readers.item.MemoData;
import com.capstone.readers.item.MemoResponse;

public interface ServiceApi {
    public final static String API_URL = "http://ec2-52-78-23-232.ap-northeast-2.compute.amazonaws.com/";

    @POST("/users/login")
    Call<LoginResponse> userLogin(@Body LoginData data);

    @POST("/users/join")
    Call<JoinResponse> userJoin(@Body JoinData data);

    @POST("/memo/memo")
    Call<MemoResponse> memoGet(@Body MemoData data);
}
