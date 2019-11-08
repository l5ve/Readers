package com.capstone.readers.ToonCard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capstone.readers.MyApp;
import com.capstone.readers.R;

import java.util.ArrayList;

public class ToonFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<ToonCard> myDataset;

    private RadioGroup sort_group;
    private RadioButton sort_title;
    private RadioButton sort_update;
    private RadioButton sort_platform;
    private String user_id;

    public static ToonFragment newInstance() {
        return new ToonFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fv = inflater.inflate(R.layout.fragment_toon, container, false);

        // service = RetrofitClient.getClient().create(ServiceApi.class);
        user_id = ((MyApp) getActivity().getApplication()).getUser_id();

        sort_group = (RadioGroup) fv.findViewById(R.id.toon_sort_group);
        sort_title = (RadioButton) fv.findViewById(R.id.toon_sort_title);
        sort_update = (RadioButton) fv.findViewById(R.id.toon_sort_update);
        sort_platform = (RadioButton) fv.findViewById(R.id.toon_sort_platform);

        // 리사이클러뷰에 LinearLayoutManager 객체 지정
        mRecyclerView = (RecyclerView) fv.findViewById(R.id.toon_list);
        mLayoutManager = new GridLayoutManager(getContext(), 3);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // 리사이클러뷰에 표시할 데이터 리스트 생성
        myDataset = new ArrayList<>();

        myDataset.add(new ToonCard("0", 0, getString(R.string.naver), "가짜만화","김작가", "2017-02-01"));
        myDataset.add(new ToonCard("1", 0, getString(R.string.naver), "아무만화","아무개작가", "2019-03-31"));
        myDataset.add(new ToonCard("2", 0, getString(R.string.lezhin), "레진만화임","이작가", "2019-11-07"));
        myDataset.add(new ToonCard("3", 0, getString(R.string.daum), "다음이용","네 다음 작가", "2018-01-03"));
        myDataset.add(new ToonCard("4", 0, getString(R.string.daum), "다음다음~","네다작", "2019-11-08"));
        myDataset.add(new ToonCard("5", 0, getString(R.string.bomtoon), "코코리치","망고맛", "2018-11-08"));

        myDataset.add(new ToonCard("4", 0, getString(R.string.bufftoon), "머신러닝","홍병우", "2019-11-08"));
        myDataset.add(new ToonCard("4", 0, getString(R.string.bomtoon), "스크레이핑","위키북스", "2016-11-08"));
        myDataset.add(new ToonCard("4", 0, getString(R.string.toptoon), "크롤링","위키북스", "2015-11-08"));

        myDataset.add(new ToonCard("4", 0, getString(R.string.comicgt), "실전개발입문","강사", "2016-12-08"));
        myDataset.add(new ToonCard("4", 0, getString(R.string.comicgt), "데이터","사이언스", "2019-11-01"));
        myDataset.add(new ToonCard("4", 0, getString(R.string.comica), "사이언스","뭐다?", "2017-11-02"));
        myDataset.add(new ToonCard("4", 0, getString(R.string.daum), "뿌뿌뿌","빠빠빠", "2017-10-08"));

        for(int i = 0; i < myDataset.size(); i++){
            Log.d("ToonMemoFragment", "Dataset(" + i + ") " + myDataset.get(i).img + ", " + myDataset.get(i).title);
        }

        mAdapter = new ToonListAdapter(getContext(), myDataset, 1);
        mRecyclerView.setAdapter(mAdapter);

        sort_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId){
                String result;
                if(checkedId == R.id.toon_sort_title){
                    mAdapter = new ToonListAdapter(getContext(), myDataset, 1);
                    mRecyclerView.setAdapter(mAdapter);
                } else if(checkedId == R.id.toon_sort_update) {
                    mAdapter = new ToonListAdapter(getContext(), myDataset, 2);
                    mRecyclerView.setAdapter(mAdapter);
                } else if(checkedId == R.id.toon_sort_platform) {
                    mAdapter = new ToonListAdapter(getContext(), myDataset, 3);
                    mRecyclerView.setAdapter(mAdapter);
                }
            }
        });

        return fv;
    }
}
