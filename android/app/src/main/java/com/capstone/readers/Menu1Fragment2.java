package com.capstone.readers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

/** 1-2 홈화면의 장르별 정렬을 나타내는 프래그먼트
 *
 */
public class Menu1Fragment2 extends Fragment {
    public static Menu1Fragment2 newInstance(){
        return new Menu1Fragment2();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fv = inflater.inflate(R.layout.fragment2_menu1, container, false);

        return fv;
    }
}
