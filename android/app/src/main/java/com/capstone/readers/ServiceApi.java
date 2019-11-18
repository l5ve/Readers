package com.capstone.readers;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

import com.capstone.readers.MypageMemo.MemoCard;
import com.capstone.readers.item.*;
import com.capstone.readers.Block.BlockCard;

import java.util.ArrayList;

public interface ServiceApi {
    public final static String API_URL = "http://ec2-52-78-23-232.ap-northeast-2.compute.amazonaws.com/";

    /* 로그인 */
    @POST("/users/login")
    Call<LoginResponse> userLogin(@Body LoginData data);

    /* 회원가입 */
    @POST("/users/join")
    Call<JoinResponse> userJoin(@Body JoinData data);


    /* 웹툰 리스트 메뉴-요일별/장르별/완결 */
    @POST("/toon/daylist")
    Call<ArrayList<ToonResponse>> getDayToon(@Body getDayToonData data);

    @POST("/toon/genrelist")
    Call<ArrayList<ToonResponse>> getGenreToon(@Body getGenreToonData data);

    @POST("/toon/endlist")
    Call<ArrayList<ToonResponse>> getEndToon(@Body getEndToonData data);


    /* 작품 상세 페이지 */
    @POST("toon/detailpage")
    Call<ArrayList<DetailPageResponse>> getDetailPageData(@Body UserToonData data);

    @POST("toon/detailgenrelist")
    Call<ArrayList<ToonGenreResponse>> getDetailGenres(@Body ToonIdData data);


    /* 구독 */
    @POST("subscribe/add")
    Call<ResponseBody> subscribe(@Body UserToonData data);

    @POST("subscribe/delete")
    Call <ResponseBody> unsubscribe(@Body UserToonData data);


    /* 숨김 */
    @POST("block/add")
    Call<ResponseBody> block(@Body UserToonData data);

    @POST("block/delete")
    Call<ResponseBody> unblock(@Body UserToonData data);


    /* 메모 */
    @POST("/memo/save")
    Call<ResponseBody> saveMemo(@Body MemoSaveData data);

    @POST("/memo/delete")
    Call<ResponseBody> deleteMemo(@Body UserToonData data);

    @POST("/memo/list")
    Call<ArrayList<MemoCard>> getMemoList(@Body MypageMemoData data);


    /* 숨김(차단) */
    @POST("/block/add")
    Call<ResponseBody> addBlock(@Body UserToonData data);

    @POST("/block/delete")
    Call<ResponseBody> deleteBlock(@Body UserToonData data);

    @POST("/block/list")
    Call<ArrayList<BlockCard>> getBlockList(@Body UserIdData data);


    /* 마이페이지 구독/책갈피/메모 수 */
    @POST("/users/num")
    Call<ArrayList<MypageResponse>> getMypageData(@Body UserIdData data);

}
