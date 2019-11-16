package com.capstone.readers.ToonCard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capstone.readers.MyApp;
import com.capstone.readers.R;
import com.capstone.readers.RetrofitClient;
import com.capstone.readers.ServiceApi;
import com.capstone.readers.item.ToonResponse;
import com.capstone.readers.item.getDayToonData;
import com.capstone.readers.item.getEndToonData;
import com.capstone.readers.item.getGenreToonData;
import com.capstone.readers.lib.MyToast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/* 웹툰 리스트 화면(Menu1) Fragment
*  요일별/장르별/완결 웹툰 리스트
* */
public class ToonFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<ToonResponse> list;
    private ArrayList<ToonCard> myDataset;

    private RadioGroup sort_group;
    private RadioButton sort_title;
    private RadioButton sort_update;
    private RadioButton sort_platform;
    private String order_by;
    private String user_id;
    private Boolean DayTab;
    private Boolean GenreTab;
    private Boolean EndTab;
    private String day;
    private String genre;
    private ServiceApi service;

    private int indicator;
    final int paging = 12;

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

        service = RetrofitClient.getClient().create(ServiceApi.class);

        order_by = "name";
        indicator = 0;

        /* 요일/장르/완결 탭 구분 */
        DayTab = ((MyApp) getActivity().getApplication()).getDayTab();
        GenreTab = ((MyApp) getActivity().getApplication()).getGenreTab();
        EndTab = ((MyApp) getActivity().getApplication()).getEndTab();

        /* 작품 숨김용 user_id */
        user_id = ((MyApp) getActivity().getApplication()).getUser_id();

        /* 제목순/업데이트순/연재처순 */
        sort_group = (RadioGroup) fv.findViewById(R.id.toon_sort_group);
        sort_title = (RadioButton) fv.findViewById(R.id.toon_sort_title);
        sort_update = (RadioButton) fv.findViewById(R.id.toon_sort_update);
        sort_platform = (RadioButton) fv.findViewById(R.id.toon_sort_platform);

        /* RecyclerView GridLayoutManager 지정 */
        mRecyclerView = (RecyclerView) fv.findViewById(R.id.toon_list);
        mLayoutManager = new GridLayoutManager(getContext(), 3);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged (RecyclerView recyclerView, int newState) {
                if (!mRecyclerView.canScrollVertically(1)){
                    Log.d("ToonFragment", "End of list");
                    addMoreItem();
                }
            }
        });

        /* 제목순/업데이트순/연재처순 버튼 클릭시 onCheckedChanged */
        sort_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId){
                String result;
                if(checkedId == R.id.toon_sort_title){
                    order_by = "name";
                } else if(checkedId == R.id.toon_sort_update) {
                    order_by = "site";
                } else if(checkedId == R.id.toon_sort_platform) {
                    order_by = "update";
                }
                getData();
            }
        });

        /* RecyclerView에 표시할 데이터 리스트 생성 */
        myDataset = new ArrayList<>();

        getData();

        return fv;
    }

    public void getData() {
        if(DayTab) {
            day = ((MyApp) getActivity().getApplication()).getDay();
            Log.d("ToonFragment", "요일 탭: " + day);
            getDayToonData data = new getDayToonData(day, user_id, order_by);
            getDayData(data);
        }
        else if (GenreTab) {
            genre = ((MyApp) getActivity().getApplication()).getGenre();
            Log.d("ToonFragment", "장르 탭: " + genre);
            getGenreToonData data = new getGenreToonData(genre, user_id, order_by);
            getGenreData(data);
        }
        else if (EndTab) {
            Log.d("ToonFragment", "완결 탭: true");
            getEndToonData data = new getEndToonData(user_id, order_by);
            getEndData(data);
        }
    }

    /* RecyclerView adapter 지정 */
    public void setAdapter() {
        mAdapter = new ToonListAdapter(getContext(), myDataset);
        mRecyclerView.setAdapter(mAdapter);
    }

    /* 요일별 웹툰 데이터 받아오기 */
    public void getDayData(getDayToonData data){
        service.getDayToon(data).enqueue(new Callback<ArrayList<ToonResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<ToonResponse>> call, Response<ArrayList<ToonResponse>> response) {
                list = response.body();

                if (response.isSuccessful() && list != null) {
                    for (int i = 0; i < paging; i++) {
                       myDataset.add(list.get(i).getToonCard());
                    }
                    Log.d("ToonFragment", "Put DayToons in myDataset(size: " + list.size() + ")");

                    setAdapter();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ToonResponse>> call, Throwable t) {
                Log.e("ToonFragment", "getDayData: " + getString(R.string.toon_server_error));
                MyToast.s(getContext(), getString(R.string.toon_server_error));
            }
        });

    }

    /* 장르별 웹툰 데이터 받아오기 */
    public void getGenreData(getGenreToonData data) {
        service.getGenreToon(data).enqueue(new Callback<ArrayList<ToonResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<ToonResponse>> call, Response<ArrayList<ToonResponse>> response) {
                list = response.body();

                /* ArrayList ToonCard 데이터형으로 생성해서 totalDataset에 넣기 */
                if (response.isSuccessful() && list != null) {
                    for (int i = 0; i < paging; i++) {
                        myDataset.add(list.get(i).getToonCard());
                    }
                    Log.d("ToonFragment", "Put GenreToons in myDataset(size: " + list.size() + ")");

                    setAdapter();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ToonResponse>> call, Throwable t) {
                Log.e("ToonFragment", "getGenreData: " + getString(R.string.toon_server_error));
                MyToast.s(getContext(), getString(R.string.toon_server_error));
            }
        });

    }

    /* 완결 웹툰 데이터 받아오기 */
    public void getEndData(getEndToonData data){
        service.getEndToon(data).enqueue(new Callback<ArrayList<ToonResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<ToonResponse>> call, Response<ArrayList<ToonResponse>> response) {
                list = response.body();

                /* ArrayList ToonCard 데이터형으로 생성해서 totalDataset에 넣기 */
                if (response.isSuccessful() && list != null) {
                    for (int i = 0; i < paging; i++) {
                        myDataset.add(list.get(i).getToonCard());
                    }
                    Log.d("ToonFragment", "Put EndToons in myDataset(size: " + list.size() + ")");

                    setAdapter();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ToonResponse>> call, Throwable t) {
                Log.e("ToonFragment", "getEndData: " + getString(R.string.toon_server_error));
                MyToast.s(getContext(), getString(R.string.toon_server_error));
            }
        });
    }

    public void addMoreItem() {
        if (indicator >= list.size())
            return;

        int limit = indicator + paging;
        for (int i = indicator; i < limit && i < list.size(); i++) {
            myDataset.add(list.get(i).getToonCard());
            indicator++;
        }

        mAdapter.notifyDataSetChanged();
    }
}
