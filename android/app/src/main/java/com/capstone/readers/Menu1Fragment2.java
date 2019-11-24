package com.capstone.readers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.capstone.readers.Toon.ToonFragment;
import com.capstone.readers.lib.MyLog;
import com.google.android.material.tabs.TabLayout;

/** 1-2 홈화면의 장르별 정렬을 나타내는 프래그먼트
 *
 */
public class Menu1Fragment2 extends Fragment {
    private TabLayout tabLayout;
    private String[] genre_list;

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

        genre_list = ((MyApp) getActivity().getApplication()).getGenre_list();

        // Initialize the tablayout
        tabLayout = (TabLayout) fv.findViewById(R.id.webtoon_genre_tab);

        // 기본 화면으로 월요일 웹툰
        Fragment fg = ToonFragment.newInstance();
        setChildFragment(ToonFragment.newInstance(), "감성");

        // 탭의 선택 상태가 변경될 때 호출되는 리스너
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {int pos = tab.getPosition();
                Fragment fg;
                fg = ToonFragment.newInstance();
                setChildFragment(fg, genre_list[pos]);
                MyLog.d("Menu1Fragment2", "선택된 탭 " +pos);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab){

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab){

            }
        });

        return fv;
    }

    private void setChildFragment(Fragment child, String genre) {
        FragmentTransaction childFt = getChildFragmentManager().beginTransaction();

        ((MyApp) getActivity().getApplication()).setGenreTab();
        ((MyApp) getActivity().getApplication()).setGenre(genre);

        if(!child.isAdded()) {
            childFt.replace(R.id.frag1_genre_container, child);
            childFt.addToBackStack(null);
            childFt.commit();
        }
    }
}
