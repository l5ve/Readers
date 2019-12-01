package com.capstone.readers;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.StrictMode;

import androidx.recyclerview.widget.RecyclerView;

import com.capstone.readers.MypageMemo.MemoCard;
import com.capstone.readers.Toon.ToonCard;

import java.util.List;

/**
 * 앱 전역에서 사용할 수 있는 클래스
 * Application 클래스를 상속
 */
public class MyApp extends Application {
    private SharedPreferences appData;
    private String user_id;
    private String user_pw; // 암호화된 비밀번호
    private boolean savedData;
    private String user_name;

    private boolean login_naver;
    private boolean login_daum;
    private boolean login_lezhin;
    private boolean login_mrblue;
    private boolean login_bufftoon;
    private boolean login_bomtoon;
    private boolean login_bbuding;
    private boolean login_kakaopage;
    private boolean login_comica;
    private boolean login_comicgt;
    private boolean login_ktoon;
    private boolean login_toptoon;
    private boolean login_toomics;
    private boolean login_foxtoon;
    private boolean login_peanutoon;

    private boolean DayTab;
    private boolean GenreTab;
    private boolean EndTab;
    private String episodeUrl;
    private String mypagesubscribetab;

    private String day;
    private String genre;

    private boolean fromHomeTab;
    private boolean fromMypage;
    private boolean fromMemo;
    private RecyclerView.Adapter globalAdapter;
    private int pos;
    private List<ToonCard> mDataset;
    private boolean existedBefore;
    private int mypagePos;
    private List<ToonCard> mypageDataset;
    private int memoPos;
    private List<MemoCard> memoDataset;

    private boolean[] genre_selected;
    private String[] genre_list;

    private ToonCard detail_page_info;
    private String search_keyword;

    @Override
    public void onCreate() {
        super.onCreate();

        // File UriExposedException 문제를 해결하기 위한 코드
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        appData = getSharedPreferences("appData", MODE_PRIVATE);

        genre_selected = new boolean[13];
        for (int i = 0; i < 13; i++) {
            genre_selected[i] = false;
        }

        genre_list = new String[13];
        genre_list[0] = "감성";
        genre_list[1] = "개그";
        genre_list[2] = "드라마";
        genre_list[3] = "로맨스";
        genre_list[4] = "스릴러";
        genre_list[5] = "스토리";
        genre_list[6] = "스포츠";
        genre_list[7] = "시대극";
        genre_list[8] = "옴니버스";
        genre_list[9] = "액션";
        genre_list[10] = "일상";
        genre_list[11] = "에피소드";
        genre_list[12] = "판타지";
    }

    public void initialize(){
        savedData = false;
        SharedPreferences.Editor editor = appData.edit();
        editor.putBoolean("SAVE_LOGIN_DATA", false);
        editor.putBoolean("login_naver", false);
        editor.putBoolean("login_daum", false);
        editor.putBoolean("login_lezhin", false);
        editor.putBoolean("login_mrblue", false);
        editor.putBoolean("login_bufftoon", false);
        editor.putBoolean("login_bomtoon", false);
        editor.putBoolean("login_bbuding", false);
        editor.putBoolean("login_kakaopage", false);
        editor.putBoolean("login_comica", false);
        editor.putBoolean("login_ktoon", false);
        editor.putBoolean("login_toptoon", false);
        editor.putBoolean("login_toomics", false);
        editor.putBoolean("login_foxtoon", false);
        editor.putBoolean("login_peanutoon", false);
        editor.apply();
    }

    public void setUser_id(String user_id){
        this.user_id = user_id;
        SharedPreferences.Editor editor = appData.edit();
        editor.putString("ID", user_id);
        editor.apply();
    }

    public String getUser_id(){
        return appData.getString("ID", user_id);
    }

    public void setUser_pw(String user_pw){
        this.user_pw = user_pw;
        SharedPreferences.Editor editor = appData.edit();
        editor.putString("PWD", user_pw);
        editor.apply();
    }

    public String getUser_pw(){
        return appData.getString("PWD", user_pw);
    }

    public void setSavedData(boolean savedData) {
        this.savedData = savedData;
        SharedPreferences.Editor editor = appData.edit();
        editor.putBoolean("SAVE_LOGIN_DATA", savedData);
        editor.apply();
    }

    public boolean getSavedData(){
        return appData.getBoolean("SAVE_LOGIN_DATA", savedData);
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
        SharedPreferences.Editor editor = appData.edit();
        editor.putString("NAME", user_name);
        editor.apply();
    }

    public String getUser_name() {
        return appData.getString("NAME", user_name);
    }

    public boolean isLogin_naver() {
        return appData.getBoolean("login_naver", false);
    }

    public boolean isLogin_bbuding() {
        return appData.getBoolean("login_bbuding", false);
    }

    public boolean isLogin_bomtoon() {
        return appData.getBoolean("login_bomtoon", false);
    }

    public boolean isLogin_bufftoon() {
        return appData.getBoolean("login_bufftoon", false);
    }

    public boolean isLogin_daum() {
        return appData.getBoolean("login_daum", false);
    }

    public boolean isLogin_comica() {
        return appData.getBoolean("login_comica", false);
    }

    public boolean isLogin_comicgt() {
        return appData.getBoolean("login_comicgt", false);
    }

    public boolean isLogin_foxtoon() {
        return appData.getBoolean("login_foxtoon", false);
    }

    public boolean isLogin_kakaopage() {
        return appData.getBoolean("login_kakaopage", false);
    }

    public boolean isLogin_lezhin() {
        return appData.getBoolean("login_lezhin", false);
    }

    public boolean isLogin_ktoon() {
        return appData.getBoolean("login_ktoon", false);
    }

    public boolean isLogin_mrblue() {
        return appData.getBoolean("login_mrblue", false);
    }

    public boolean isLogin_peanutoon() {
        return appData.getBoolean("login_peanutoon", false);
    }

    public boolean isLogin_toomics() {
        return appData.getBoolean("login_toomics", false);
    }

    public boolean isLogin_toptoon() {
        return appData.getBoolean("login_toptoon", false);
    }

    public void setLogin_naver(boolean login_naver) {
        SharedPreferences.Editor editor = appData.edit();
        editor.putBoolean("login_naver", login_naver);
        editor.apply();
    }

    public void setLogin_daum(boolean login_daum) {
        SharedPreferences.Editor editor = appData.edit();
        editor.putBoolean("login_daum", login_daum);
        editor.apply();
    }

    public void setLogin_lezhin(boolean login_lezhin) {
        SharedPreferences.Editor editor = appData.edit();
        editor.putBoolean("login_lezhin", login_lezhin);
        editor.apply();
    }

    public void setLogin_mrblue(boolean login_mrblue) {
        SharedPreferences.Editor editor = appData.edit();
        editor.putBoolean("login_mrblue", login_mrblue);
        editor.apply();
    }

    public void setLogin_bufftoon(boolean login_bufftoon) {
        SharedPreferences.Editor editor = appData.edit();
        editor.putBoolean("login_bufftoon", login_bufftoon);
        editor.apply();
    }

    public void setLogin_bomtoon(boolean login_bomtoon) {
        SharedPreferences.Editor editor = appData.edit();
        editor.putBoolean("login_bomtoon", login_bomtoon);
        editor.apply();
    }

    public void setLogin_bbuding(boolean login_bbuding) {
        SharedPreferences.Editor editor = appData.edit();
        editor.putBoolean("login_bbuding", login_bbuding);
        editor.apply();
    }

    public void setLogin_kakaopage(boolean login_kakaopage) {
        SharedPreferences.Editor editor = appData.edit();
        editor.putBoolean("login_kakaopage", login_kakaopage);
        editor.apply();
    }

    public void setLogin_comica(boolean login_comica) {
        SharedPreferences.Editor editor = appData.edit();
        editor.putBoolean("login_comica", login_comica);
        editor.apply();
    }

    public void setLogin_comicgt(boolean login_comicgt) {
        SharedPreferences.Editor editor = appData.edit();
        editor.putBoolean("login_comicgt", login_comicgt);
        editor.apply();
    }

    public void setLogin_ktoon(boolean login_ktoon) {
        SharedPreferences.Editor editor = appData.edit();
        editor.putBoolean("login_ktoon", login_ktoon);
        editor.apply();
    }

    public void setLogin_toptoon(boolean login_toptoon) {
        SharedPreferences.Editor editor = appData.edit();
        editor.putBoolean("login_toptoon", login_toptoon);
        editor.apply();
    }

    public void setLogin_toomics(boolean login_toomics) {
        SharedPreferences.Editor editor = appData.edit();
        editor.putBoolean("login_toomics", login_toomics);
        editor.apply();
    }

    public void setLogin_foxtoon(boolean login_foxtoon) {
        SharedPreferences.Editor editor = appData.edit();
        editor.putBoolean("login_foxtoon", login_foxtoon);
        editor.apply();
    }

    public void setLogin_peanutoon(boolean login_peanutoon) {
        SharedPreferences.Editor editor = appData.edit();
        editor.putBoolean("login_peanutoon", login_peanutoon);
        editor.apply();
    }

    public void setDayTab() {
        DayTab = true;
        GenreTab = false;
        genre = null;
        EndTab = false;
    }

    public boolean getDayTab() {
        return DayTab;
    }

    public void setGenreTab() {
        DayTab = false;
        day = null;
        GenreTab = true;
        EndTab = false;
    }

    public boolean getGenreTab() {
        return GenreTab;
    }

    public void setEndTab() {
        DayTab = false;
        day = null;
        GenreTab = false;
        genre = null;
        EndTab = true;
    }

    public boolean getEndTab() {
        return EndTab;
    }

    public void setEpisodeUrl(String url) {
        this.episodeUrl = url;
    }

    public String getEpisodeUrl(){
        return this.episodeUrl;
    }

    public void setMypagesubscribetab(String value) {
        this.mypagesubscribetab = value;
    }

    public String getMypagesubscribetab() {
        return this.mypagesubscribetab;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDay() {
        return day;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getGenre() {
        return genre;
    }

    public void setDetail_page_info(ToonCard info) {
        this.detail_page_info = info;
    }

    public ToonCard getDetail_page_info() {
        return this.detail_page_info;
    }

    public void setGenre_selected(int i, boolean value) {
        genre_selected[i] = value;
    }

    public boolean[] getGenre_selected() {
        return genre_selected;
    }

    public String[] getGenre_list() {
        return genre_list;
    }

    public String getSearch_keyword() {
        return search_keyword;
    }

    public void setSearch_keyword(String search_keyword) {
        this.search_keyword = search_keyword;
    }

    public void setGlobalAdapter(RecyclerView.Adapter adapter) {
        this.globalAdapter = adapter;
    }

    public RecyclerView.Adapter getGlobalAdapter() {
        return globalAdapter;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public int getPos() {
        return pos;
    }

    public List<ToonCard> getmDataset() {
        return mDataset;
    }

    public void setmDataset(List<ToonCard> data) {
        mDataset = data;
    }

    public void setExistedBefore(boolean value) {
        this.existedBefore = value;
    }

    public boolean getExistedBefore() {
        return existedBefore;
    }

    public void setMypageDataset(List<ToonCard> data) {
        mypageDataset = data;
    }

    public List<ToonCard> getMypageDataset() {
        return mypageDataset;
    }

    public void setMypagePos(int position) {
        this.mypagePos = position;
    }

    public int getMypagePos() {
        return mypagePos;
    }

    public void setFromHomeTab(boolean value) {
        this.fromHomeTab = value;
        this.fromMypage = !value;
        if (mypageDataset != null)
            mypageDataset.clear();
        this.fromMemo = !value;
        if (memoDataset != null)
            memoDataset.clear();
    }

    public boolean getFromHomeTab() {
        return fromHomeTab;
    }

    public void setFromMypage(boolean value) {
        this.fromMypage = value;
        this.fromHomeTab = !value;
        if (mDataset != null)
            mDataset.clear();
        this.fromMemo = !value;
        if (memoDataset != null)
            memoDataset.clear();
    }

    public boolean getFromMypage() {
        return fromMypage;
    }

    public void setFromOthers() {
        this.fromHomeTab = false;
        if (mDataset != null)
            mDataset.clear();
        this.fromMypage = false;
        if (mypageDataset != null)
            mypageDataset.clear();
        this.fromMemo = false;
        if (memoDataset != null)
            memoDataset.clear();
    }

    public void setFromMemo(boolean value) {
        this.fromMemo = value;

        this.fromHomeTab = !value;
        if (mDataset != null)
            mDataset.clear();
        this.fromMypage = !value;
        if (mypageDataset != null)
            mypageDataset.clear();
    }

    public boolean getFromMemo() {
        return fromMemo;
    }

    public void setMemoPos(int pos) {
        memoPos = pos;
    }

    public int getMemoPos() {
        return memoPos;
    }

    public void setMemoDataset(List<MemoCard> data) {
        memoDataset = data;
    }

    public List<MemoCard> getMemoDataset() {
        return memoDataset;
    }
}