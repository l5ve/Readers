package com.capstone.readers;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/** 1-4 각 연재처를 나타내는 프래그먼트
 *
 */
public class Menu1Fragment4 extends Fragment {
    FragmentManager fragmentManager = getParentFragment().getChildFragmentManager();

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
        ImageButton btn1 = (ImageButton) fv.findViewById(R.id.platform1);

        btn1.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View view) {
                String url = "https://m.comic.naver.com";
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.button_container, WebviewFragment.newInstance(url));
                fragmentTransaction.commit();
            }
        });

        return fv;
    }

}
