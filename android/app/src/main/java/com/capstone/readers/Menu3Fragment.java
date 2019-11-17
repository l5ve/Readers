package com.capstone.readers;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.capstone.readers.MemoCard.MemoFragment;

import static android.content.Context.MODE_PRIVATE;

/** 3번째 메뉴인 마이페이지 화면을 나타내는 프래그먼트
 *
 */
public class Menu3Fragment extends Fragment {
    private String name;
    private int subs_num;
    private int bookmark_num;
    private int memo_num;

    private TextView profile_name;
    private TextView mypage_subscribe;
    private TextView mypage_bookmark;
    private TextView mypage_memo;

    private SharedPreferences appData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fv = inflater.inflate(R.layout.fragment_menu3, container, false);

        // 설정값 불러오기
        appData = this.getActivity().getSharedPreferences("appData", MODE_PRIVATE);
        load();

        profile_name = (TextView) fv.findViewById(R.id.profile_name);
        mypage_subscribe = (TextView) fv.findViewById(R.id.mypage_subscribe);
        mypage_bookmark = (TextView) fv.findViewById(R.id.mypage_bookmark);
        mypage_memo = (TextView) fv.findViewById(R.id.mypage_memo);

        profile_name.setText(name);
        mypage_subscribe.setText(Integer.toString(subs_num));
        mypage_bookmark.setText(Integer.toString(bookmark_num));
        mypage_memo.setText(Integer.toString(memo_num));

        Fragment fg = Menu3Fragment1.newInstance();
        setChildFragment(fg);

        mypage_memo.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fg = MemoFragment.newInstance();
                setChildFragment(fg);
            }
        });

        return fv;
    }

    private void setChildFragment(Fragment child) {
        FragmentTransaction childFt = getChildFragmentManager().beginTransaction();

        if(!child.isAdded()) {
            childFt.replace(R.id.mypage_fragment, child);
            childFt.addToBackStack(null);
            childFt.commit();
        }
    }

    // 설정값을 불러오는 함수
    private void load() {
        name = ((MyApp) getActivity().getApplication()).getUser_name();
        subs_num = ((MyApp) getActivity().getApplication()).getSubs_num();
        bookmark_num = ((MyApp) getActivity().getApplication()).getBookmark_num();
        memo_num = ((MyApp) getActivity().getApplication()).getMemo_num();
    }
}