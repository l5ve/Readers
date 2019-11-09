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
    private Boolean DayTab;
    private Boolean GenreTab;
    private Boolean EndTab;
    private String day;
    private String genre;

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

        /* 요일/장르/완결 탭 구분 */
        DayTab = ((MyApp) getActivity().getApplication()).getDayTab();
        GenreTab = ((MyApp) getActivity().getApplication()).getGenreTab();
        EndTab = ((MyApp) getActivity().getApplication()).getEndTab();

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

        if(DayTab) {
            day = ((MyApp) getActivity().getApplication()).getDay();
            Log.d("ToonFragment", "Argument(Day): " + day);
            getDayData(day);
        }
        else if (GenreTab) {
            genre = ((MyApp) getActivity().getApplication()).getGenre();
            Log.d("ToonFragment", "Argument(Genre): " + genre);
            getGenreData(genre);
        }
        else if (EndTab) {
            Log.d("ToonFragment", "Argument(End): true");
            getEndData();
        }

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

    public void getDayData(String day){
        if (day.equals("mon")){
            myDataset.add(new ToonCard("0", 0, getString(R.string.naver), "월요일만화","김작가", "2017-02-01"));
            myDataset.add(new ToonCard("1", 0, getString(R.string.naver), "아무만화","아무개작가", "2019-03-31"));
            myDataset.add(new ToonCard("2", 0, getString(R.string.lezhin), "왈왈","이작가", "2019-11-07"));
            myDataset.add(new ToonCard("3", 0, getString(R.string.daum), "룬디","요일작가", "2018-01-03"));
            myDataset.add(new ToonCard("4", 0, getString(R.string.daum), "먼데이","일요일요일", "2019-11-08"));
        }
        else {
            myDataset.add(new ToonCard("0", 0, getString(R.string.naver), "월요일말곤","김작가", "2017-02-01"));
            myDataset.add(new ToonCard("1", 0, getString(R.string.naver), "귀찮아서","아무개작가", "2019-03-31"));
            myDataset.add(new ToonCard("2", 0, getString(R.string.lezhin), "예시 안 넣음","이작가", "2019-11-07"));
            myDataset.add(new ToonCard("3", 0, getString(R.string.daum), "화수목금토일","요일작가", "2018-01-03"));
            myDataset.add(new ToonCard("4", 0, getString(R.string.daum), "주말","일요일요일", "2019-11-08"));
        }

    }

    public void getGenreData(String genre) {
        if (genre.equals("감성")) {
            myDataset.add(new ToonCard("542", 0, getString(R.string.bomtoon), "감성장르","김작가", "2018-11-08"));
            myDataset.add(new ToonCard("754", 0, getString(R.string.bufftoon), "갬성","이작가", "2019-11-08"));
            myDataset.add(new ToonCard("324", 0, getString(R.string.bomtoon), "센티멘탈","윤작가", "2016-11-08"));
            myDataset.add(new ToonCard("924", 0, getString(R.string.comicgt), "장르장르","한작가", "2019-11-01"));
        }
        else {
            myDataset.add(new ToonCard("542", 0, getString(R.string.naver), "다른장르","최작가", "2018-11-08"));
            myDataset.add(new ToonCard("754", 0, getString(R.string.daum), "아무장르","정작가", "2019-11-08"));
            myDataset.add(new ToonCard("324", 0, getString(R.string.daum), "감성말곤","신작가", "2016-11-08"));
            myDataset.add(new ToonCard("924", 0, getString(R.string.comicgt), "귀찮아서","송작가", "2019-11-01"));
            myDataset.add(new ToonCard("542", 0, getString(R.string.bomtoon), "안했음","김작가", "2018-11-08"));
            myDataset.add(new ToonCard("754", 0, getString(R.string.bufftoon), "쏘리","이작가", "2019-11-08"));
            myDataset.add(new ToonCard("324", 0, getString(R.string.bomtoon), "낫쏘리","윤작가", "2016-11-08"));
            myDataset.add(new ToonCard("924", 0, getString(R.string.mrblue), "하하하","한작가", "2019-11-01"));
        }
    }

    public void getEndData(){
        myDataset.add(new ToonCard("100", 0, getString(R.string.toptoon), "옛날웹툰1","완결", "2015-11-08"));
        myDataset.add(new ToonCard("120", 0, getString(R.string.comicgt), "오래된웹툰","완완", "2016-12-08"));
        myDataset.add(new ToonCard("439", 0, getString(R.string.comica), "라떼는웹툰","결결", "2017-11-02"));
        myDataset.add(new ToonCard("954", 0, getString(R.string.daum), "예전만화","결완", "2017-10-08"));
    }
}
