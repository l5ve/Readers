package com.capstone.readers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

/** 1-3 홈화면의 완결 웹툰 정렬 화면을 나타내는 프래그먼트
 *
 */
public class Menu1Fragment3 extends Fragment {
    public static Menu1Fragment3 newInstance(){
        return new Menu1Fragment3();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fv = inflater.inflate(R.layout.fragment3_menu1, container, false);

        return fv;
    }
}
