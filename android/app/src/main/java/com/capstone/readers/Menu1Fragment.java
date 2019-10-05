package com.capstone.readers;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.capstone.readers.R;
import com.google.android.material.tabs.TabLayout;

public class Menu1Fragment extends Fragment {
    Toolbar toolbar;

    Menu1Fragment1 menu1Fragment1;   // 요일별
    Menu1Fragment2 menu1Fragment2;   // 장르별
    Menu1Fragment3 menu1Fragment3;   // 완결

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fv = inflater.inflate(R.layout.fragment_menu1, container, false);

        TabLayout tabs = (TabLayout) fv.findViewById(R.id.first_tabs);
        setChildFragment(Menu1Fragment1.newInstance());

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Log.d("Menu1Fragment", "선택된 탭: "+position);

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
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        return fv;
    }

    private void setChildFragment(Fragment child) {
        FragmentTransaction childFt = getChildFragmentManager().beginTransaction();

        if(!child.isAdded()) {
            childFt.replace(R.id.frag1_container, child);
            childFt.addToBackStack(null);
            childFt.commit();
        }
    }

}