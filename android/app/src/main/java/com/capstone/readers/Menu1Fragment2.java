package com.capstone.readers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.capstone.readers.lib.MyLog;
import com.google.android.material.tabs.TabLayout;

/** 1-2 홈화면의 장르별 정렬을 나타내는 프래그먼트
 *
 */
public class Menu1Fragment2 extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;

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

        // Initialize the tablayout and viewpager
        tabLayout = (TabLayout) fv.findViewById(R.id.webtoon_genre_tab);
        viewPager = (ViewPager) fv.findViewById(R.id.webtoon_genre_pager);

        // Create daytabpageradapter adapter
        GenreTabPagerAdapter pagerAdapter = new GenreTabPagerAdapter(getChildFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        // ViewPager에서 페이지의 상태가 변경될 때 페이지 변경 이벤트를
        // TabLayout에 전달하여 탭의 선택 상태를 동기화해주는 역할
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        // 탭의 선택 상태가 변경될 때 호출되는 리스너
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                MyLog.d("선택된 장르 프래그먼트: " +pos);
                viewPager.setCurrentItem(tab.getPosition());
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

    View.OnClickListener movePageListener = new View.OnClickListener(){
        @Override
        public void onClick(View v)
        {
            int tag = (int) v.getTag();
            viewPager.setCurrentItem(tag);
        }
    };

    public class GenreTabPagerAdapter extends FragmentStatePagerAdapter {
        private int tabCount;

        public GenreTabPagerAdapter(FragmentManager fm, int tabCount){
            super(fm);
            this.tabCount = tabCount;
        }

        @Override
        public Fragment getItem(int position) {

            // Returning the current tabs
            switch (position) {
                case 0:
                    Sentimental sentimental = new Sentimental();
                    return sentimental;
                case 1:
                    Humor humor = new Humor();
                    return humor;
                case 2:
                    Drama drama = new Drama();
                    return drama;
                case 3:
                    Love love = new Love();
                    return love;
                case 4:
                    Thriller thriller = new Thriller();
                    return thriller;
                case 5:
                    Story story = new Story();
                    return story;
                case 6:
                    Sports sports = new Sports();
                    return sports;
                case 7:
                    Omnibus omnibus = new Omnibus();
                    return omnibus;
                case 8:
                    Action action = new Action();
                    return action;
                case 9:
                    Daily_life daily_life = new Daily_life();
                    return daily_life;
                case 10:
                    Episode episode = new Episode();
                    return episode;
                case 11:
                    Fantasy fantasy = new Fantasy();
                    return fantasy;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return tabCount;
        }
    }
}
