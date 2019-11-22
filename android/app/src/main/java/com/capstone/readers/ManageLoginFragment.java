package com.capstone.readers;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;

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
    private LinearLayout manage_login_notice;
    private TextView manage_login_notice_content;
    private Boolean isOpened;

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
        manage_login_notice = (LinearLayout) fv.findViewById(R.id.manage_login_notice);
        manage_login_notice_content = (TextView) fv.findViewById(R.id.manage_login_notice_content);
        isOpened = false;

        manage_login_notice.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                isOpened = !isOpened;
                if (isOpened){
                    manage_login_notice_content.setVisibility(View.VISIBLE);
                } else {
                    manage_login_notice_content.setVisibility(View.GONE);
                }
            }
        });

        Switch switch_readers = (Switch) fv.findViewById(R.id.switch_readers);
        switch_readers.setChecked(true);
        switch_readers.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                ((MyApp) getActivity().getApplication()).initialize();
                MyToast.s(getContext(), getString(R.string.logout_done));

                getActivity().startActivity(intent);
                getActivity().finish();
            }
        });

        Switch switch_naver = (Switch) fv.findViewById(R.id.switch_naver);
        switch_naver.setChecked(((MyApp) getActivity().getApplication()).isLogin_naver());
        switch_naver.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ((MyApp) getActivity().getApplication()).setLogin_naver(isChecked);
                if(isChecked) {
                    String url = "https://nid.naver.com/nidlogin.login?svctype=262144&url=http://m.naver.com/aside/";
                    callWebview(getString(R.string.naver), url, false, true);
                }
                else {
                    String url = "https://nid.naver.com/nidlogin.logout?returl=https://www.naver.com/";
                    callWebview(getString(R.string.naver), url, true, true);
                }
            }
        });


        Switch switch_daum = (Switch) fv.findViewById(R.id.switch_daum);
        switch_daum.setChecked(((MyApp) getActivity().getApplication()).isLogin_daum());
        switch_daum.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ((MyApp) getActivity().getApplication()).setLogin_daum(isChecked);
                if(isChecked) {
                    String url = "https://logins.daum.net/accounts/loginform.do?status=-401&url=http%3A%2F%2Fwebtoon.daum.net%2F";
                    callWebview(getString(R.string.daum), url, false, true);
                }
                else {
                    String url = "https://logins.daum.net/accounts/logout.do?url=http%3A%2F%2Fwebtoon.daum.net%2F";
                    callWebview(getString(R.string.daum), url, true, true);
                }
            }
        });


        Switch switch_lezhin = (Switch) fv.findViewById(R.id.switch_lezhin);
        switch_lezhin.setChecked(((MyApp) getActivity().getApplication()).isLogin_lezhin());
        switch_lezhin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ((MyApp) getActivity().getApplication()).setLogin_lezhin(isChecked);
                if(isChecked) {
                    String url = "https://www.lezhin.com/ko/login";
                    callWebview(getString(R.string.lezhin), url, false, true);
                }
                else {
                    String url = "https://www.lezhin.com/ko/logout";
                    callWebview(getString(R.string.lezhin), url, true, true);
                }
            }
        });


        Switch switch_mrblue = (Switch) fv.findViewById(R.id.switch_mrblue);
        switch_mrblue.setChecked(((MyApp) getActivity().getApplication()).isLogin_mrblue());
        switch_mrblue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ((MyApp) getActivity().getApplication()).setLogin_mrblue(isChecked);
                if(isChecked) {
                    String url = "https://www.mrblue.com/login?returnUrl=%2F";
                    callWebview(getString(R.string.mrblue), url, false, true);
                }
                else {
                    String url = "https://www.mrblue.com/logout";
                    callWebview(getString(R.string.mrblue), url, true, true);
                }
            }
        });


        Switch switch_bufftoon = (Switch) fv.findViewById(R.id.switch_bufftoon);
        switch_bufftoon.setChecked(((MyApp) getActivity().getApplication()).isLogin_bufftoon());
        switch_bufftoon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ((MyApp) getActivity().getApplication()).setLogin_bufftoon(isChecked);
                if(isChecked) {
                    String url = "https://mlogin.plaync.com/login/signin?return_url=https%3A%2F%2Fbufftoon.plaync.com%2F&remove_cookie=true";
                    callWebview(getString(R.string.bufftoon), url, false, true);
                }
                else {
                    String url = "https://bufftoon.plaync.com/";
                    callWebview(getString(R.string.bufftoon), url, false, false);
                }
            }
        });


        Switch switch_bomtoon = (Switch) fv.findViewById(R.id.switch_bomtoon);
        switch_bomtoon.setChecked(((MyApp) getActivity().getApplication()).isLogin_bomtoon());
        switch_bomtoon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ((MyApp) getActivity().getApplication()).setLogin_bomtoon(isChecked);
                if(isChecked) {
                    String url = "https://www.bomtoon.com/login?redirect=/present";
                    callWebview(getString(R.string.bomtoon), url, false, true);
                }
                else {
                    String url = "https://www.bomtoon.com/login/logout";
                    callWebview(getString(R.string.bomtoon), url, true, true);
                }
            }
        });


        Switch switch_bbuding = (Switch) fv.findViewById(R.id.switch_bbuding);
        switch_bbuding.setChecked(((MyApp) getActivity().getApplication()).isLogin_bbuding());
        switch_bbuding.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ((MyApp) getActivity().getApplication()).setLogin_bbuding(false);
                if(isChecked) {
                    buttonView.setChecked(false);
                    MyToast.l(getActivity(), "해당 사이트는 현재 점검 중입니다.");
                }
                else {
                    MyToast.l(getActivity(), "해당 사이트는 현재 점검 중입니다.");
                }
            }
        });


        Switch switch_kakaopage = (Switch) fv.findViewById(R.id.switch_kakaopage);
        switch_kakaopage.setChecked(((MyApp) getActivity().getApplication()).isLogin_kakaopage());
        switch_kakaopage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ((MyApp) getActivity().getApplication()).setLogin_kakaopage(isChecked);
                if(isChecked){
                    String url = "https://accounts.kakao.com/login?continue=https%3A%2F%2Fkauth.kakao.com%2Foauth%2Fauthorize%3Fredirect_uri%3Dkakaojs%26response_type%3Dcode%26state%3Dxdql7gkewjc5pe5r0jb3i%26proxy%3DeasyXDM_Kakao_58xx5ofrynv_provider%26ka%3Dsdk%252F1.32.1%2520os%252Fjavascript%2520lang%252Fko-KR%2520device%252FWin32%2520origin%252Fhttps%25253A%25252F%25252Fpage.kakao.com%26origin%3Dhttps%253A%252F%252Fpage.kakao.com%26client_id%3D49bbb48c5fdb0199e5da1b89de359484";
                    callWebview(getString(R.string.kakaopage), url, false, true);
                }
                else {
                    String url = "https://page.kakao.com/main";
                    callWebview(getString(R.string.kakaopage), url, false, false);
                }
            }
        });


        Switch switch_comica = (Switch) fv.findViewById(R.id.switch_comica);
        switch_comica.setChecked(((MyApp) getActivity().getApplication()).isLogin_comica());
        switch_comica.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ((MyApp) getActivity().getApplication()).setLogin_comica(isChecked);
                if(isChecked){
                    String url = "https://www.comica.com/";
                    callWebview(getString(R.string.comica), url, false, true);
                }
                else {
                    String url = "https://www.comica.com/";
                    callWebview(getString(R.string.comica), url, false, false);
                }
            }
        });


        Switch switch_comicgt = (Switch) fv.findViewById(R.id.switch_comicgt);
        switch_comicgt.setChecked(((MyApp) getActivity().getApplication()).isLogin_comicgt());
        switch_comicgt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ((MyApp) getActivity().getApplication()).setLogin_comicgt(isChecked);
                if(isChecked){
                    String url = "http://www.comicgt.com/Base/Login?Param=&callbackURL=http%3A%2F%2Fwww.comicgt.com%2F";
                    callWebview(getString(R.string.comicgt), url, false, true);
                }
                else {
                    String url = "http://www.comicgt.com/Base/Logout";
                    callWebview(getString(R.string.comicgt), url, true, true);
                }
            }
        });


        Switch switch_ktoon = (Switch) fv.findViewById(R.id.switch_ktoon);
        switch_ktoon.setChecked(((MyApp) getActivity().getApplication()).isLogin_ktoon());
        switch_ktoon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ((MyApp) getActivity().getApplication()).setLogin_ktoon(isChecked);
                if(isChecked){
                    String url = "https://www.myktoon.com/mw/user/ktoon/login.kt";
                    callWebview(getString(R.string.ktoon), url, false, true);
                }
                else {
                    String url = "https://www.myktoon.com/web/homescreen/logout.kt";
                    callWebview(getString(R.string.ktoon), url, true, true);
                }
            }
        });


        Switch switch_toptoon = (Switch) fv.findViewById(R.id.switch_toptoon);
        switch_toptoon.setChecked(((MyApp) getActivity().getApplication()).isLogin_toptoon());
        switch_toptoon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ((MyApp) getActivity().getApplication()).setLogin_toptoon(isChecked);
                if(isChecked){
                    String url = "https://toptoon.com/login";
                    callWebview(getString(R.string.toptoon), url, false, true);
                }
                else {
                    String url = "https://toptoon.com/";
                    callWebview(getString(R.string.toptoon), url, false, false);
                }
            }
        });


        Switch switch_toomics = (Switch) fv.findViewById(R.id.switch_toomics);
        switch_toomics.setChecked(((MyApp) getActivity().getApplication()).isLogin_naver());
        switch_toomics.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ((MyApp) getActivity().getApplication()).setLogin_toomics(isChecked);
                if(isChecked){
                    String url = "https://www.toomics.com/";
                    callWebview(getString(R.string.toomics), url, false, true);
                }
                else {
                    String url = "https://www.toomics.com/auth/logout";
                    callWebview(getString(R.string.toomics), url, true, true);
                }
            }
        });


        Switch switch_foxtoon = (Switch) fv.findViewById(R.id.switch_foxtoon);
        switch_foxtoon.setChecked(((MyApp) getActivity().getApplication()).isLogin_foxtoon());
        switch_foxtoon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ((MyApp) getActivity().getApplication()).setLogin_foxtoon(isChecked);
                if(isChecked){
                    String url = "https://www.foxtoon.com/login";
                    callWebview(getString(R.string.foxtoon), url, false, true);
                }
                else {
                    String url = "https://www.foxtoon.com/logout";
                    callWebview(getString(R.string.foxtoon), url, true, true);
                }
            }
        });


        Switch switch_peanutoon = (Switch) fv.findViewById(R.id.switch_peanutoon);
        switch_peanutoon.setChecked(((MyApp) getActivity().getApplication()).isLogin_peanutoon());
        switch_peanutoon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ((MyApp) getActivity().getApplication()).setLogin_peanutoon(isChecked);
                if(isChecked){
                    String url = "https://www.peanutoon.com/ko/login?p_sign_route=0&topPage=1";
                    callWebview(getString(R.string.peanutoon), url, false, true);
                }
                else {
                    String url = "https://www.peanutoon.com/ko/logout";
                    callWebview(getString(R.string.peanutoon), url, true, true);
                }
            }
        });


        return fv;
    }

    public void callWebview(String name, String url, Boolean close, Boolean automatic) {
        Intent intent = new Intent(getActivity(), LoginWebviewActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("url", url);
        intent.putExtra("close", close);
        intent.putExtra("automatic", automatic);
        startActivity(intent);
    }
}
