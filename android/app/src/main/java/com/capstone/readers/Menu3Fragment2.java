package com.capstone.readers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

/** 3-2 마이페이지 화면의 구독/책갈피/메모 화면을 나타내는 프래그먼트
 *
 */
public class Menu3Fragment2 extends Fragment {
    public static Menu3Fragment2 newInstance(){
        return new Menu3Fragment2();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fv = inflater.inflate(R.layout.fragment2_menu3, container, false);

        return fv;
    }
}
