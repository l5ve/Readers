package com.capstone.readers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

/** 3-2 마이페이지 화면의 요일별 구독 웹툰을 나타내는 프래그먼트
 *
 */
public class Menu3Fragment1 extends Fragment {
    public static Menu3Fragment1 newInstance(){
        return new Menu3Fragment1();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fv = inflater.inflate(R.layout.fragment1_menu3, container, false);

        return fv;
    }
}
