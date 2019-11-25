package com.capstone.readers;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

import com.capstone.readers.EpisodeCard.EpisodeCard;
import com.capstone.readers.MypageBookmark.BookmarkCard;
import com.capstone.readers.MypageMemo.MemoCard;
import com.capstone.readers.Recommend.RecommendCard;
import com.capstone.readers.item.GenreWeightData;
import com.capstone.readers.Search.SearchCard;
import com.capstone.readers.Toon.ToonCard;
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

    @POST("/users/genre")
    Call<ResponseBody> setTaste(@Body UserTasteData data);


    /* 웹툰 리스트 메뉴-요일별/장르별/완결 */
    @POST("/toon/daylist")
    Call<ArrayList<ToonResponse>> getDayToon(@Body getDayToonData data);

    @POST("/toon/genrelist")
    Call<ArrayList<ToonResponse>> getGenreToon(@Body getGenreToonData data);

    @POST("/toon/endlist")
    Call<ArrayList<ToonResponse>> getEndToon(@Body getEndToonData data);

    /* 검색 */
    @POST("/toon/search")
    Call<ArrayList<SearchCard>> getSearchResult(@Body SearchData data);

    /* 작품 상세 페이지 */
    @POST("toon/detailpage")
    Call<ArrayList<DetailPageResponse>> getDetailPageData(@Body UserToonData data);

    @POST("toon/detailgenrelist")
    Call<ArrayList<ToonGenreResponse>> getDetailGenres(@Body ToonIdData data);

    /* 에피소드 리스트 */
    @POST("toon/episodelist")
    Call<ArrayList<EpisodeCard>> getEpisodeList(@Body UserToonData data);


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


    /* 책갈피 */
    @POST("/bookmark/add")
    Call<ResponseBody> addBookmark(@Body UserToonEpiData data);

    @POST("/bookmark/delete")
    Call<ResponseBody> deleteBookmark(@Body UserToonEpiData data);


    /* 추천 페이지 */
    @POST("/recommend/genrecount")
    Call<ArrayList<GenreWeightData>> getGenreWeight(@Body UserIdData data);

    @POST("/recommend/recommend")
    Call<ArrayList<RecommendCard>> getRecommendations(@Body UserIdData data);


    /* 마이페이지 */
    @POST("/subscribe/daylist")
    Call<ArrayList<ToonCard>> getSubscribeDayList(@Body getDayToonData data);

    @POST("/subscribe/endlist")
    Call<ArrayList<ToonCard>> getSubscribeEndList(@Body getEndToonData data);

    @POST("/bookmark/list")
    Call<ArrayList<BookmarkCard>> getBookmarkList(@Body MypageData data);

    @POST("/memo/list")
    Call<ArrayList<MemoCard>> getMemoList(@Body MypageData data);


    /* 마이페이지 구독/책갈피/메모 수 */
//    @POST("/users/num")
//    Call<ArrayList<MypageResponse>> getMypageData(@Body UserIdData data);

    /* 숨김(차단) */
    @POST("/block/list")
    Call<ArrayList<BlockCard>> getBlockList(@Body UserIdData data);

}
