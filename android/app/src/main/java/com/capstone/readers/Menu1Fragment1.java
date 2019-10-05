package com.capstone.readers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

/** 1-1 홈화면의 요일별 정렬 화면을 나타내는 프래그먼트
 *
 */
public class Menu1Fragment1 extends Fragment {
    public static Menu1Fragment1 newInstance(){
        return new Menu1Fragment1();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fv = inflater.inflate(R.layout.fragment1_menu1, container, false);

        return fv;
    }
}
