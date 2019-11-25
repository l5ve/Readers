package com.capstone.readers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.fragment.app.Fragment;

/** 1-4 각 연재처를 나타내는 프래그먼트
 *
 */
public class Menu1Fragment4 extends Fragment {
    private WebView mWebView; // 웹뷰 선언
    private WebSettings mWebSettings; //웹뷰세팅
    private LinearLayout button_container;
    private ScrollView platform_scroll;

    public static Menu1Fragment4 newInstance(){
        return new Menu1Fragment4();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fv = inflater.inflate(R.layout.fragment4_menu1, container, false);
        mWebView = (WebView) fv.findViewById(R.id.platform_webview);
        button_container = (LinearLayout) fv.findViewById(R.id.button_container);
        platform_scroll = (ScrollView) fv.findViewById(R.id.platform_scroll);
        platform_scroll.setVerticalScrollBarEnabled(true);

        ImageButton btn1 = (ImageButton) fv.findViewById(R.id.platform01);
        btn1.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://m.comic.naver.com";
                makeWebview(url);
            }
        });

        ImageButton btn2 = (ImageButton) fv.findViewById(R.id.platform02);
        btn2.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://webtoon.daum.net";
                makeWebview(url);
            }
        });

        ImageButton btn3 = (ImageButton) fv.findViewById(R.id.platform03);
        btn3.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.lezhin.com/ko";
                makeWebview(url);
            }
        });

        ImageButton btn4 = (ImageButton) fv.findViewById(R.id.platform04);
        btn4.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://m.mrblue.com";
                makeWebview(url);
            }
        });

        ImageButton btn5 = (ImageButton) fv.findViewById(R.id.platform05);
        btn5.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://bufftoon.plaync.com";
                makeWebview(url);
            }
        });

        ImageButton btn6 = (ImageButton) fv.findViewById(R.id.platform06);
        btn6.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.bomtoon.com";
                makeWebview(url);
            }
        });

        ImageButton btn7 = (ImageButton) fv.findViewById(R.id.platform07);
        btn7.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://bbuding.com";
                makeWebview(url);
            }
        });

        ImageButton btn8 = (ImageButton) fv.findViewById(R.id.platform08);
        btn8.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://page.kakao.com";
                makeWebview(url);
            }
        });

        ImageButton btn9 = (ImageButton) fv.findViewById(R.id.platform09);
        btn9.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://m.comica.com";
                makeWebview(url);
            }
        });

        ImageButton btn10 = (ImageButton) fv.findViewById(R.id.platform10);
        btn10.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://comicgt.com";
                makeWebview(url);
            }
        });

        ImageButton btn11 = (ImageButton) fv.findViewById(R.id.platform11);
        btn11.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.myktoon.com";
                makeWebview(url);
            }
        });

        ImageButton btn12 = (ImageButton) fv.findViewById(R.id.platform12);
        btn12.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://toptoon.com";
                makeWebview(url);
            }
        });

        ImageButton btn13 = (ImageButton) fv.findViewById(R.id.platform13);
        btn13.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://m.toomics.com";
                makeWebview(url);
            }
        });

        ImageButton btn14 = (ImageButton) fv.findViewById(R.id.platform14);
        btn14.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.foxtoon.com";
                makeWebview(url);
            }
        });

        ImageButton btn15 = (ImageButton) fv.findViewById(R.id.platform15);
        btn15.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.peanutoon.com/ko";
                makeWebview(url);
            }
        });
        return fv;
    }

    public void makeWebview(String url){
        platform_scroll.setVisibility(View.GONE);
        mWebView.setVisibility(View.VISIBLE);
        mWebView.setWebViewClient(new WebViewClient()); // 클릭시 새창 안뜨게
        mWebSettings = mWebView.getSettings(); //세부 세팅 등록
        mWebSettings.setJavaScriptEnabled(true); // 웹페이지 자바스클비트 허용 여부
        mWebSettings.setSupportMultipleWindows(false); // 새창 띄우기 허용 여부
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(false); // 자바스크립트 새창 띄우기(멀티뷰) 허용 여부
        mWebSettings.setUseWideViewPort(true); // 화면 사이즈 맞추기 허용 여부
        mWebSettings.setSupportZoom(true); // 화면 줌 허용 여부
        mWebSettings.setBuiltInZoomControls(true); // 화면 확대 축소 허용 여부
        mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); // 컨텐츠 사이즈 맞추기
        mWebSettings.setCacheMode(WebSettings.LOAD_DEFAULT); // 브라우저 캐시 허용 여부
        mWebSettings.setDomStorageEnabled(true); // 로컬저장소 허용 여부
        mWebView.loadUrl(url); // 웹뷰에 표시할 웹사이트 주소, 웹뷰 시작
    }
}
