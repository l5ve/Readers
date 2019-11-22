package com.capstone.readers;

import android.app.Application;
import android.os.StrictMode;
import com.capstone.readers.ToonCard.ToonCard;
import android.content.SharedPreferences;

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

    public final int genre_choice_score = 100;
    public final int hide_score = 10;
    public final int subscribe_score = 10;
    public final int bookmark_score = 1;

    private double[] genre_weight;

    private ToonCard detail_page_info;

    @Override
    public void onCreate() {
        super.onCreate();

        // File UriExposedException 문제를 해결하기 위한 코드
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        appData = getSharedPreferences("appData", MODE_PRIVATE);

//        subs_num = 0;
//        bookmark_num = 0;
//        memo_num = 0;

        genre_weight = new double[13];
        for (int i = 0; i < 13; i++) {
            genre_weight[i] = 0;
        }
    }

    public void initialize(){
        savedData = false;
        login_naver = false;
        login_daum = false;
        login_lezhin = false;
        login_mrblue = false;
        login_bufftoon = false;
        login_bomtoon = false;
        login_bbuding = false;
        login_kakaopage = false;
        login_comica = false;
        login_comicgt = false;
        login_ktoon = false;
        login_toptoon = false;
        login_toomics = false;
        login_foxtoon = false;
        login_peanutoon = false;
        SharedPreferences.Editor editor = appData.edit();
        editor.putBoolean("SAVE_LOGIN_DATA", false);
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
        return login_naver;
    }

    public boolean isLogin_bbuding() {
        return login_bbuding;
    }

    public boolean isLogin_bomtoon() {
        return login_bomtoon;
    }

    public boolean isLogin_bufftoon() {
        return login_bufftoon;
    }

    public boolean isLogin_daum() {
        return login_daum;
    }

    public boolean isLogin_comica() {
        return login_comica;
    }

    public boolean isLogin_comicgt() {
        return login_comicgt;
    }

    public boolean isLogin_foxtoon() {
        return login_foxtoon;
    }

    public boolean isLogin_kakaopage() {
        return login_kakaopage;
    }

    public boolean isLogin_lezhin() {
        return login_lezhin;
    }

    public boolean isLogin_ktoon() {
        return login_ktoon;
    }

    public boolean isLogin_mrblue() {
        return login_mrblue;
    }

    public boolean isLogin_peanutoon() {
        return login_peanutoon;
    }

    public boolean isLogin_toomics() {
        return login_toomics;
    }

    public boolean isLogin_toptoon() {
        return login_toptoon;
    }

    public void setLogin_naver(boolean login_naver) {
        this.login_naver = login_naver;
    }

    public void setLogin_daum(boolean login_daum) {
        this.login_daum = login_daum;
    }

    public void setLogin_lezhin(boolean login_lezhin) {
        this.login_lezhin = login_lezhin;
    }

    public void setLogin_mrblue(boolean login_mrblue) {
        this.login_mrblue = login_mrblue;
    }

    public void setLogin_bufftoon(boolean login_bufftoon) {
        this.login_bufftoon = login_bufftoon;
    }

    public void setLogin_bomtoon(boolean login_bomtoon) {
        this.login_bomtoon = login_bomtoon;
    }

    public void setLogin_bbuding(boolean login_bbuding) {
        this.login_bbuding = login_bbuding;
    }

    public void setLogin_kakaopage(boolean login_kakaopage) {
        this.login_kakaopage = login_kakaopage;
    }

    public void setLogin_comica(boolean login_comica) {
        this.login_comica = login_comica;
    }

    public void setLogin_comicgt(boolean login_comicgt) {
        this.login_comicgt = login_comicgt;
    }

    public void setLogin_ktoon(boolean login_ktoon) {
        this.login_ktoon = login_ktoon;
    }

    public void setLogin_toptoon(boolean login_toptoon) {
        this.login_toptoon = login_toptoon;
    }

    public void setLogin_toomics(boolean login_toomics) {
        this.login_toomics = login_toomics;
    }

    public void setLogin_foxtoon(boolean login_foxtoon) {
        this.login_foxtoon = login_foxtoon;
    }

    public void setLogin_peanutoon(boolean login_peanutoon) {
        this.login_peanutoon = login_peanutoon;
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

    public void setGenre_weight(int i, double weight) {
        genre_weight[i] += weight;
    }
}