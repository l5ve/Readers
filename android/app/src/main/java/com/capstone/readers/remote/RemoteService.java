package com.capstone.readers.remote;

import com.capstone.readers.item.JoinData;
import com.capstone.readers.item.JoinResponse;
import com.capstone.readers.item.LoginData;
import com.capstone.readers.item.LoginResponse;
import com.capstone.readers.item.WebtoonInfoItem;
import com.capstone.readers.item.KeepItem;
import com.capstone.readers.item.MemberInfoItem;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 서버에 ㅍ호출할 메소드를 선언하는 인터페이스
 */
public interface RemoteService {
    String BASE_URL = "http://192.168.0.6:3000";

    // 사용자 정보
    @GET("/member/{phone}")
    Call<MemberInfoItem> selectMemberInfo(@Path("phone") String phone);

    @POST("/user/login")
    Call<LoginResponse> userLogin(@Body LoginData data);

    @POST("/user/join")
    Call<JoinResponse> userJoin(@Body JoinData data);

    @POST("/member/info")
    Call<String> insertMemberInfo(@Body MemberInfoItem memberInfoItem);

    @FormUrlEncoded
    @POST("/member/phone")
    Call<String> insertMemberPhone(@Field("phone") String phone);

    // 웹툰 정보
    @GET("/webtoon/info/{info_seq}")
    Call<WebtoonInfoItem> selectFoodInfo(@Path("info_seq") int WebtoonInfoSeq,
                                         @Query("member_seq") int memberSeq);

    @POST("/webtoon/info")
    Call<String> insertFoodInfo(@Body WebtoonInfoItem webtoonInfoItem);

    @GET("/webtoon/list")
    Call<ArrayList<WebtoonInfoItem>> listWebtoonInfo(@Query("member_seq") int memberSeq);

    // 즐겨찾기
    @POST("/keep/{member_seq}/{info_seq}")
    Call<String> insertKeep(@Path("member_seq") int memberSeq, @Path("info_seq") int infoSeq);

    @DELETE("/keep/{member_seq}/{info_seq}")
    Call<String> deleteKeep(@Path("member_seq") int memberSeq, @Path("info_seq") int infoSeq);

    @GET("/keep/list")
    Call<ArrayList<KeepItem>> listKeep(@Query("member_seq") int memberSeq);
}
