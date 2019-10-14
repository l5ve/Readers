package com.capstone.readers;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.tabs.TabLayout;

import static android.content.Context.MODE_PRIVATE;

/** 3번째 메뉴인 마이페이지 화면을 나타내는 프래그먼트
 *
 */
public class Menu3Fragment extends Fragment {
    private String id;
    private String pwd;
    private SharedPreferences appData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fv = inflater.inflate(R.layout.fragment_menu3, container, false);


        return fv;
    }

    // 설정값을 불러오는 함수
    private void load() {
        // SharedPreferences 객체.get타입( 저장된 이름, 기본값 )
        // 저장된 이름이 존재하지 않을 시 기본값
        id = appData.getString("ID", "");
        pwd = appData.getString("PWD", "");
    }
}