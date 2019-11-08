package com.capstone.readers;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.capstone.readers.lib.MyToast;

public class ManageLoginFragment extends Fragment {
    public static final int REQUEST_CODE_WEBVIEW = 101;

    private WebView mWebView; // 웹뷰 선언
    private WebSettings mWebSettings; //웹뷰세팅
    private LinearLayout manage_login_container;
    private ScrollView manage_login_scroll;

    public static ManageLoginFragment newInstance(){
        return new ManageLoginFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fv = inflater.inflate(R.layout.fragment_managelogin, container, false);

        manage_login_container = (LinearLayout) fv.findViewById(R.id.manage_login_container);
        manage_login_scroll = (ScrollView) fv.findViewById(R.id.manage_login_scroll);
        manage_login_scroll.setVerticalScrollBarEnabled(true);

        Button naver_login = (Button) fv.findViewById(R.id.naver_login);
        naver_login.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://nid.naver.com/nidlogin.login?svctype=262144&url=http://m.naver.com/aside/";
                callWebview(getString(R.string.naver), url, false, true);
            }
        });

        Button naver_logout = (Button) fv.findViewById(R.id.naver_logout);
        naver_logout.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://nid.naver.com/nidlogin.logout?returl=https://www.naver.com/";
                callWebview(getString(R.string.naver), url, true, true);
            }
        });

        Button daum_login = (Button) fv.findViewById(R.id.daum_login);
        daum_login.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://logins.daum.net/accounts/loginform.do?status=-401&url=http%3A%2F%2Fwebtoon.daum.net%2F";
                callWebview(getString(R.string.daum), url, false, true);
            }
        });

        Button daum_logout = (Button) fv.findViewById(R.id.daum_logout);
        daum_logout.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://logins.daum.net/accounts/logout.do?url=http%3A%2F%2Fwebtoon.daum.net%2F";
                callWebview(getString(R.string.daum), url, true, true);
            }
        });

        Button lezhin_login = (Button) fv.findViewById(R.id.lezhin_login);
        lezhin_login.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.lezhin.com/ko/login";
                callWebview(getString(R.string.lezhin), url, false, true);
            }
        });

        Button lezhin_logout = (Button) fv.findViewById(R.id.lezhin_logout);
        lezhin_logout.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.lezhin.com/ko/logout";
                callWebview(getString(R.string.lezhin), url, true, true);
            }
        });

        Button mrblue_login = (Button) fv.findViewById(R.id.mrblue_login);
        mrblue_login.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.mrblue.com/login?returnUrl=%2F";
                callWebview(getString(R.string.mrblue), url, false, true);
            }
        });

        Button mrblue_logout = (Button) fv.findViewById(R.id.mrblue_logout);
        mrblue_logout.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.mrblue.com/logout";
                callWebview(getString(R.string.mrblue), url, true, true);
            }
        });

        Button bufftoon_login = (Button) fv.findViewById(R.id.bufftoon_login);
        bufftoon_login.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                String url = "https://mlogin.plaync.com/login/signin?return_url=https%3A%2F%2Fbufftoon.plaync.com%2F&remove_cookie=true";
                callWebview(getString(R.string.bufftoon), url, false, true);
            }
        });

        Button bufftoon_logout = (Button) fv.findViewById(R.id.bufftoon_logout);
        bufftoon_logout.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                String url = "https://bufftoon.plaync.com/";
                callWebview(getString(R.string.bufftoon), url, false, false);
            }
        });

        Button bomtoon_login = (Button) fv.findViewById(R.id.bomtoon_login);
        bomtoon_login.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.bomtoon.com/login?redirect=/present";
                callWebview(getString(R.string.bomtoon), url, false, true);
            }
        });

        Button bomtoon_logout = (Button) fv.findViewById(R.id.bomtoon_logout);
        bomtoon_logout.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.bomtoon.com/login/logout";
                callWebview(getString(R.string.bomtoon), url, true, true);
            }
        });

        Button bbuding_login = (Button) fv.findViewById(R.id.bbuding_login);
        bbuding_login.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyToast.l(getActivity(), "해당 사이트는 현재 점검 중입니다.");
            }
        });

        Button bbuding_logout = (Button) fv.findViewById(R.id.bbuding_logout);
        bbuding_logout.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyToast.l(getActivity(), "해당 사이트는 현재 점검 중입니다.");
            }
        });

        Button kakaopage_login = (Button) fv.findViewById(R.id.kakaopage_login);
        kakaopage_login.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view){
                String url = "https://accounts.kakao.com/login?continue=https%3A%2F%2Fkauth.kakao.com%2Foauth%2Fauthorize%3Fredirect_uri%3Dkakaojs%26response_type%3Dcode%26state%3Dxdql7gkewjc5pe5r0jb3i%26proxy%3DeasyXDM_Kakao_58xx5ofrynv_provider%26ka%3Dsdk%252F1.32.1%2520os%252Fjavascript%2520lang%252Fko-KR%2520device%252FWin32%2520origin%252Fhttps%25253A%25252F%25252Fpage.kakao.com%26origin%3Dhttps%253A%252F%252Fpage.kakao.com%26client_id%3D49bbb48c5fdb0199e5da1b89de359484";
                callWebview(getString(R.string.kakaopage), url, false, true);
            }
        });

        Button kakaopage_logout = (Button) fv.findViewById(R.id.kakaopage_logout);
        kakaopage_logout.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view){
                String url = "https://page.kakao.com/main";
                callWebview(getString(R.string.kakaopage), url, false, false);
            }
        });

        Button comica_login = (Button) fv.findViewById(R.id.comica_login);
        comica_login.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view){
                String url = "https://www.comica.com/";
                callWebview(getString(R.string.comica), url, false, true);
            }
        });

        Button comica_logout = (Button) fv.findViewById(R.id.comica_logout);
        comica_logout.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view){
                String url = "https://www.comica.com/";
                callWebview(getString(R.string.comica), url, false, false);
            }
        });

        Button comicgt_login = (Button) fv.findViewById(R.id.comicgt_login);
        comicgt_login.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view){
                String url = "http://www.comicgt.com/Base/Login?Param=&callbackURL=http%3A%2F%2Fwww.comicgt.com%2F";
                callWebview(getString(R.string.comicgt), url, false, true);
            }
        });

        Button comicgt_logout = (Button) fv.findViewById(R.id.comicgt_logout);
        comicgt_logout.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view){
                String url = "http://www.comicgt.com/Base/Logout";
                callWebview(getString(R.string.comicgt), url, true, true);
            }
        });

        Button ktoon_login = (Button) fv.findViewById(R.id.ktoon_login);
        ktoon_login.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.myktoon.com/mw/user/ktoon/login.kt";
                callWebview(getString(R.string.ktoon), url, false, true);
            }
        });

        Button ktoon_logout = (Button) fv.findViewById(R.id.ktoon_logout);
        ktoon_logout.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.myktoon.com/web/homescreen/logout.kt";
                callWebview(getString(R.string.ktoon), url, true, true);
            }
        });

        Button toptoon_login = (Button) fv.findViewById(R.id.toptoon_login);
        toptoon_login.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://toptoon.com/login";
                callWebview(getString(R.string.toptoon), url, false, true);
            }
        });

        Button toptoon_logout = (Button) fv.findViewById(R.id.toptoon_logout);
        toptoon_logout.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://toptoon.com/";
                callWebview(getString(R.string.toptoon), url, false, false);
            }
        });

        Button toomics_login = (Button) fv.findViewById(R.id.toomics_login);
        toomics_login.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.toomics.com/";
                callWebview(getString(R.string.toomics), url, false, true);
            }
        });

        Button toomics_logout = (Button) fv.findViewById(R.id.toomics_logout);
        toomics_logout.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.toomics.com/auth/logout";
                callWebview(getString(R.string.toomics), url, true, true);
            }
        });

        Button foxtoon_login = (Button) fv.findViewById(R.id.foxtoon_login);
        foxtoon_login.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.foxtoon.com/login";
                callWebview(getString(R.string.foxtoon), url, false, true);
            }
        });

        Button foxtoon_logout = (Button) fv.findViewById(R.id.foxtoon_logout);
        foxtoon_logout.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.foxtoon.com/logout";
                callWebview(getString(R.string.foxtoon), url, true, true);
            }
        });

        Button peanutoon_login = (Button) fv.findViewById(R.id.peanutoon_login);
        peanutoon_login.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.peanutoon.com/ko/login?p_sign_route=0&topPage=1";
                callWebview(getString(R.string.peanutoon), url, false, true);
            }
        });

        Button peanutoon_logout = (Button) fv.findViewById(R.id.peanutoon_logout);
        peanutoon_logout.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.peanutoon.com/ko/logout";
                callWebview(getString(R.string.peanutoon), url, true, true);
            }
        });

        return fv;
    }

    public void callWebview(String name, String url, Boolean close, Boolean automatic) {
        Intent intent = new Intent(getActivity(), WebviewActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("url", url);
        intent.putExtra("close", close);
        intent.putExtra("automatic", automatic);
        startActivity(intent);
    }
}
