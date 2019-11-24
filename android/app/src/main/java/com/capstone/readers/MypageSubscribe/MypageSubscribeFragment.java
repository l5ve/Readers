package com.capstone.readers.MypageSubscribe;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capstone.readers.MyApp;
import com.capstone.readers.R;
import com.capstone.readers.RetrofitClient;
import com.capstone.readers.ServiceApi;
import com.capstone.readers.Toon.ToonCard;
import com.capstone.readers.item.getDayToonData;
import com.capstone.readers.item.getEndToonData;
import com.capstone.readers.lib.MyToast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MypageSubscribeFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<ToonCard> list;
    private ArrayList<ToonCard> myDataset;

    private RadioGroup sort_group;
    private RadioButton sort_title;
    private RadioButton sort_update;
    private RadioButton sort_platform;
    private String order_by;
    private String user_id;

    private String day;
    private ServiceApi service;

    private int indicator;
    final int paging = 12;

    public static MypageSubscribeFragment newInstance() {
        return new MypageSubscribeFragment();
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

        /* RecyclerView에 표시할 데이터 리스트 생성 */
        myDataset = new ArrayList<>();

        /* 작품 숨김용 user_id */
        user_id = ((MyApp) getActivity().getApplication()).getUser_id();
        day = ((MyApp) getActivity().getApplication()).getMypagesubscribetab();

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
                    Log.d("MypageSubscribeFragment", "End of list");
                    if (indicator < list.size()){
                        addMoreItem();
                    }
                }
            }
        });

        /* 제목순/업데이트순/연재처순 버튼 클릭시 onCheckedChanged */
        sort_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId){
                if(checkedId == R.id.toon_sort_title){
                    order_by = "name";
                } else if(checkedId == R.id.toon_sort_update) {
                    order_by = "update";
                } else if(checkedId == R.id.toon_sort_platform) {
                    order_by = "site";
                }
                myDataset.clear();
                getData();
            }
        });

        getData();

        return fv;
    }

    public void getData() {
        /* 완결 탭을 클릭한 경우 */
        if(day.equals("end")) {
            getEndToonData data = new getEndToonData(user_id, order_by);
            getEndData(data);
        }
        /* 요일 탭을 클릭한 경우 */
        else {
            getDayToonData data = new getDayToonData(day, user_id, order_by);
            getDayData(data);
        }
    }

    /* RecyclerView adapter 지정 */
    public void setAdapter() {
        mAdapter = new MypageSubscribeListAdapter(getContext(), myDataset);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void getDayData(getDayToonData data) {
        service.getSubscribeDayList(data).enqueue(new Callback<ArrayList<ToonCard>>() {
            @Override
            public void onResponse(Call<ArrayList<ToonCard>> call, Response<ArrayList<ToonCard>> response) {
                list = response.body();

                if (response.isSuccessful() && list != null) {
                    int i;
                    for (i = 0; i < paging && i < list.size(); i++) {
                        myDataset.add(list.get(i));
                    }
                    indicator = i;

                    setAdapter();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ToonCard>> call, Throwable t) {
                Log.e("MypageSubscribeFragment", "getDayData: " + getString(R.string.toon_server_error));
                MyToast.s(getContext(), getString(R.string.toon_server_error));
            }
        });
    }

    public void getEndData(getEndToonData data) {
        service.getSubscribeEndList(data).enqueue(new Callback<ArrayList<ToonCard>>() {
            @Override
            public void onResponse(Call<ArrayList<ToonCard>> call, Response<ArrayList<ToonCard>> response) {
                list = response.body();

                if (response.isSuccessful() && list != null) {
                    int i;
                    for (i = 0; i < paging && i < list.size(); i++) {
                        myDataset.add(list.get(i));
                    }
                    indicator = i;

                    setAdapter();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ToonCard>> call, Throwable t) {
                Log.e("MypageSubscribeFragment", "getEndData: " + getString(R.string.toon_server_error));
                MyToast.s(getContext(), getString(R.string.toon_server_error));
            }
        });
    }


    public void addMoreItem() {
        if (indicator >= list.size())
            return;

        int limit = indicator + paging;
        for (int i = indicator; i < limit && i < list.size(); i++) {
            myDataset.add(list.get(i));
            indicator++;
        }

        mAdapter.notifyItemRangeChanged(indicator, paging);
    }
}
