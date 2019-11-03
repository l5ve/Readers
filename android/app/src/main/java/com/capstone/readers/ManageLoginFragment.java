package com.capstone.readers;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
        View fv = inflater.inflate(R.layout.activity_managelogin, container, false);

        manage_login_container = (LinearLayout) fv.findViewById(R.id.manage_login_container);
        manage_login_scroll = (ScrollView) fv.findViewById(R.id.manage_login_scroll);
        manage_login_scroll.setVerticalScrollBarEnabled(true);

        Button naver_login = (Button) fv.findViewById(R.id.naver_login);
        naver_login.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://nid.naver.com/nidlogin.login?svctype=262144&url=http://m.naver.com/aside/";
                Intent intent = new Intent(getActivity(), WebviewActivity.class);
                intent.putExtra("name", R.string.naver);
                intent.putExtra("url", url);
                intent.putExtra("close", false);
                startActivity(intent);
            }
        });

        Button naver_logout = (Button) fv.findViewById(R.id.naver_logout);
        naver_logout.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://nid.naver.com/nidlogin.logout?returl=https://www.naver.com/";
                Intent intent = new Intent(getActivity(), WebviewActivity.class);
                intent.putExtra("name", R.string.naver);
                intent.putExtra("url", url);
                intent.putExtra("close", true);
                startActivity(intent);
            }
        });

        return fv;
    }
}
