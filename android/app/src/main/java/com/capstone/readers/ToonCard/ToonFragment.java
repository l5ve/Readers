package com.capstone.readers.ToonCard;

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
import com.capstone.readers.item.ToonResponse;
import com.capstone.readers.lib.MyToast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private ServiceApi service;

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

        Log.d("ToonMemoFragment", "Get the dataset(size: " + myDataset.size() + ")");

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

    public void getDayData(String toon_weekday){
        service.getDayToon(toon_weekday).enqueue(new Callback<ArrayList<ToonResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<ToonResponse>> call, Response<ArrayList<ToonResponse>> response) {
                ArrayList<ToonResponse> list = response.body();

                /* list ToonCard 데이터형으로 생성해서 myDataset에 넣기 */
                String id, title, platform, author, thumbnail, update;
                if (response.isSuccessful() && list != null) {
                    if (response.isSuccessful() && list != null) {
                        for (int i = 0; i < list.size(); i++) {
                            id = list.get(i).getId();
                            title = list.get(i).getTitle();
                            platform = list.get(i).getPlatform();
                            author = list.get(i).getAuthor();
                            thumbnail = list.get(i).getThumbnail();
                            update = list.get(i).getUpdate();
                            myDataset.add(new ToonCard(id, title, platform, author, thumbnail, update));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ToonResponse>> call, Throwable t) {

            }
        });

    }

    public void getGenreData(String genre_name) {
        service.getGenreToon(genre_name).enqueue(new Callback<ArrayList<ToonResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<ToonResponse>> call, Response<ArrayList<ToonResponse>> response) {
                ArrayList<ToonResponse> list = response.body();

                /* list ToonCard 데이터형으로 생성해서 myDataset에 넣기 */
                String id, title, platform, author, thumbnail, update;
                if (response.isSuccessful() && list != null) {
                    if (response.isSuccessful() && list != null) {
                        for (int i = 0; i < list.size(); i++) {
                            id = list.get(i).getId();
                            title = list.get(i).getTitle();
                            platform = list.get(i).getPlatform();
                            author = list.get(i).getAuthor();
                            thumbnail = list.get(i).getThumbnail();
                            update = list.get(i).getUpdate();
                            myDataset.add(new ToonCard(id, title, platform, author, thumbnail, update));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ToonResponse>> call, Throwable t) {
                Log.e("ToonFragment", "getGenreData: " + getString(R.string.toon_server_error));
                MyToast.s(getContext(), getString(R.string.toon_server_error));
            }
        });

    }

    public void getEndData(){
        String is_end = "O"; // 알파벳 대문자 오

        service.getEndToon(is_end).enqueue(new Callback<ArrayList<ToonResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<ToonResponse>> call, Response<ArrayList<ToonResponse>> response) {
                ArrayList<ToonResponse> list = response.body();

                /* list ToonCard 데이터형으로 생성해서 myDataset에 넣기 */
                String id, title, platform, author, thumbnail, update;
                if (response.isSuccessful() && list != null) {
                    for (int i = 0; i < list.size(); i++) {
                        id = list.get(i).getId();
                        title = list.get(i).getTitle();
                        platform = list.get(i).getPlatform();
                        author = list.get(i).getAuthor();
                        thumbnail = list.get(i).getThumbnail();
                        update = list.get(i).getUpdate();
                        myDataset.add(new ToonCard(id, title, platform, author, thumbnail, update));
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ToonResponse>> call, Throwable t) {
                Log.e("ToonFragment", "getEndData: " + getString(R.string.toon_server_error));
                MyToast.s(getContext(), getString(R.string.toon_server_error));
            }
        });
    }
}
