package com.capstone.readers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.capstone.readers.Search.SearchFragment;
import com.capstone.readers.lib.MyLog;
import com.google.android.material.tabs.TabLayout;

/** 1번째 메뉴인 홈(웹툰) 화면을 나타내는 프래그먼트
 *
 */
public class Menu1Fragment extends Fragment {
    private EditText search_bar;
    private ImageButton search_btn;
    private TabLayout tabs;
    private String search_keyword;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fv = inflater.inflate(R.layout.fragment_menu1, container, false);

        tabs = (TabLayout) fv.findViewById(R.id.first_tabs);
        search_bar = (EditText) fv.findViewById(R.id.search_bar);
        search_btn = (ImageButton) fv.findViewById(R.id.search_button);

        setChildFragment(Menu1Fragment1.newInstance());

        /* 탭의 상태가 변경될 때 호출되는 리스너 */
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                MyLog.d("Menu1Fragment", "선택된 탭: "+position);

                Fragment fg;
                switch (position) {
                    case 0:
                        fg = Menu1Fragment1.newInstance();
                        setChildFragment(fg);
                        break;
                    case 1:
                        fg = Menu1Fragment2.newInstance();
                        setChildFragment(fg);
                        break;
                    case 2:
                        fg = Menu1Fragment3.newInstance();
                        setChildFragment(fg);
                        break;
                    case 3:
                        fg = Menu1Fragment4.newInstance();
                        setChildFragment(fg);

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_keyword = search_bar.getText().toString().trim();
                ((MyApp) getActivity().getApplication()).setSearch_keyword(search_keyword);
                Fragment fg = SearchFragment.newInstance();
                AppCompatActivity aca = (AppCompatActivity) v.getContext();
                aca.getSupportFragmentManager().beginTransaction().replace(R.id.frag1_container, fg).addToBackStack(null).commit();
            }
        });


        return fv;
    }

    public void setChildFragment(Fragment child) {
        FragmentTransaction childFt = getChildFragmentManager().beginTransaction();

        if(!child.isAdded()) {
            childFt.replace(R.id.frag1_container, child);
            childFt.addToBackStack(null);
            childFt.commit();
        }
    }

}