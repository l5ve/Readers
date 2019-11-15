package com.capstone.readers;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import com.capstone.readers.item.DetailPageResponse;
import com.capstone.readers.item.ToonResponse;
import com.capstone.readers.item.JoinResponse;
import com.capstone.readers.item.JoinData;
import com.capstone.readers.item.LoginResponse;
import com.capstone.readers.item.LoginData;
import com.capstone.readers.item.MemoData;
import com.capstone.readers.item.MemoResponse;

import java.util.ArrayList;

public interface ServiceApi {
    public final static String API_URL = "http://ec2-52-78-23-232.ap-northeast-2.compute.amazonaws.com/";

    @POST("/users/login")
    Call<LoginResponse> userLogin(@Body LoginData data);

    @POST("/users/join")
    Call<JoinResponse> userJoin(@Body JoinData data);

    @POST("/memo/memo")
    Call<MemoResponse> getMemo(@Body MemoData data);

    @GET("/toon/daylist")
    Call<ArrayList<ToonResponse>> getDayToon(@Query("is_end") String is_end, @Query("toon_weekday") String toon_weekday);

    @GET("/toon/genrelist")
    Call<ArrayList<ToonResponse>> getGenreToon(@Query("genre_name") String genre_name);

    @GET("/toon/endlist")
    Call<ArrayList<ToonResponse>> getEndToon(@Query("is_end") String is_end);

    @POST("/toon/detail")
    Call<DetailPageResponse> getDetailPage(@Body String toon_id);

    @GET("/toon/detailGenres")
    Call<ArrayList<String>> getDetailGenres(@Query("toon_id") String toon_id);

}
