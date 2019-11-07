package com.capstone.readers;

import android.app.Application;
import android.os.StrictMode;

/**
 * 앱 전역에서 사용할 수 있는 클래스
 * Application 클래스를 상속하고 있으며,
 * 사용자 정보인 MemberInfoItem을 저장하고 반환하는 역할
 * 이를 통해 앱의 어디서나 사용자 정보에 접근할 수 있다.
 */
public class MyApp extends Application {
    private String user_id;
    private String user_pw; // 암호화된 비밀번호
    private boolean savedData;
    private String user_name;
    private int subs_num;
    private int bookmark_num;
    private int memo_num;

    @Override
    public void onCreate() {
        super.onCreate();

        // File UriExposedException 문제를 해결하기 위한 코드
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        subs_num = 0;
        bookmark_num = 0;
        memo_num = 0;
    }

    public void setUser_id(String user_id){
        this.user_id = user_id;
    }

    public String getUser_id(){
        return user_id;
    }

    public void setUser_pw(String user_pw){
        this.user_pw = user_pw;
    }

    public String getUser_pw(){
        return user_pw;
    }

    public void setSavedData(boolean savedData) {
        this.savedData = savedData;
    }

    public boolean getSavedData(){
        return savedData;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_name(String user_name) {
        return user_name;
    }

    public void setSubs_num(int subs_num) {
        this.subs_num = subs_num;
    }

    public int getSubs_num() {
        return subs_num;
    }

    public void setBookmark_num(int bookmark_num) {
        this.bookmark_num = bookmark_num;
    }

    public int getBookmark_num() {
        return bookmark_num;
    }

    public void setMemo_num(int memo_num) {
        this.memo_num = memo_num;
    }

    public int getMemo_num() {
        return memo_num;
    }
}