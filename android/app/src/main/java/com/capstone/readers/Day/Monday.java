package com.capstone.readers.Day;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.capstone.readers.R;

public class Monday extends Fragment {
    private SharedPreferences appData;
    private String nametest;

    public static Monday newInstance(){
        return new Monday();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fv = inflater.inflate(R.layout.monday, container, false);




        return fv;
    }

    // 설정값을 불러오는 함수
    private void load() {
        // SharedPreferences 객체.get타입( 저장된 이름, 기본값
        // 저장된 이름이 존재하지 않을 시 기본값
        nametest = appData.getString("NAME", "");
    }
}
