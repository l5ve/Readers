package com.capstone.readers.EpisodeCard;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollographql.apollo.interceptor.ApolloInterceptor;
import com.capstone.readers.MyApp;
import com.capstone.readers.R;
import com.capstone.readers.ServiceApi;
import com.capstone.readers.ToonCard.ToonListAdapter;
import com.capstone.readers.item.DetailPageResponse;
import com.capstone.readers.lib.MyToast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/* 각 웹툰의 회차정보를 표시하는 Fragment */
public class EpisodeFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<EpisodeCard> myDataset;

    private String id;
    private ImageView mImageView;
    private TextView mPlatform;
    private TextView mTitle;
    private TextView mAuthor;
    private TextView mDesc;
    Bitmap bitmap;

    private ArrayList<String> genres;

    private boolean getMainData;
    private ServiceApi service;

    public static EpisodeFragment newInstance() {
        return new EpisodeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fv = inflater.inflate(R.layout.fragment_detail_page, container, false);

        id = ((MyApp) getActivity().getApplication()).getDetail_page_id();
        getMainData = false;

        mImageView = (ImageView) fv.findViewById(R.id.detail_page_thumbnail);
        mPlatform = (TextView) fv.findViewById(R.id.detail_page_platform);
        mTitle = (TextView) fv.findViewById(R.id.detail_page_title);
        mAuthor = (TextView) fv.findViewById(R.id.detail_page_author);
        mDesc = (TextView) fv.findViewById(R.id.detail_page_desc);

        mRecyclerView = (RecyclerView) fv.findViewById(R.id.episode_list);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        myDataset = new ArrayList<>();
        String temp = ((MyApp) getActivity().getApplication()).getTemp();
        mTitle.setText(temp);
        myDataset.add(new EpisodeCard("0", "0", temp + " 1화", "2019-01-01"));

        setAdapter();
        //getDetailData(id);
        //genres = new ArrayList<String>();


        //if(!getMainData) {
        //    return fv;
        //}

        //getDetailGenresData(id);



        return fv;
    }

    public void setAdapter() {
        mAdapter = new EpisodeListAdapter(getContext(), myDataset);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("EpisodeFragment", "onPause(): deleteeeeeeeeeeeeeeeeeee");
        AppCompatActivity aca = (AppCompatActivity) getContext();
        aca.getSupportFragmentManager().beginTransaction().remove(this).commit();
//        FragmentTransaction ft = getParentFragment().getChildFragmentManager().beginTransaction();
//        ft.remove(this);
//        ft.commit();
    }


    @Override
    public void onStop() {
        super.onStop();
        Log.d("EpisodeFragment", "onStop(): deleteeeeeeeeeeeeeeeeeee");
        AppCompatActivity aca = (AppCompatActivity) getContext();
        aca.getSupportFragmentManager().beginTransaction().remove(this).commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        Log.d("EpisodeFragment", "onDestroyView(): deleteeeeeeeeeeeeeeeeeee");
        AppCompatActivity aca = (AppCompatActivity) getContext();
        aca.getSupportFragmentManager().beginTransaction().remove(this).commit();
    }

    public void getDetailGenresData(String toon_id) {
        service.getDetailGenres(toon_id).enqueue(new Callback<ArrayList<String>>() {
            @Override
            public void onResponse(Call<ArrayList<String>> call, Response<ArrayList<String>> response) {
                ArrayList<String> list = response.body();

                if (response.isSuccessful() && list != null) {
                    for (int i = 0; i < list.size(); i++) {
                        genres.add(list.get(i));
                    }
                    Log.d("EpisodeFragment", "getDetailGenresData: Put Genres(size: " + list.size() + ")");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<String>> call, Throwable t) {
                Log.e("EpisodeFragment", "getDetailGenresData: " + getString(R.string.server_error_message));
                MyToast.s(getContext(), getString(R.string.server_error_message));
            }
        });

    }

    public void getDetailData(String toon_id) {
        service.getDetailPage(toon_id).enqueue(new Callback<DetailPageResponse>() {
            @Override
            public void onResponse(Call<DetailPageResponse> call, Response<DetailPageResponse> response) {
                DetailPageResponse result = response.body();

                if(result.getCode() != 200) {
                    Log.e("EpisodeFragment", "getDetailData: " + getString(R.string.null_body));
                    MyToast.s(getContext(), getString(R.string.server_error_message));
                }
                else {
                   setThumbnail(result.getThumbnail());
                   mPlatform.setText(result.getPlatform());
                   mTitle.setText(result.getTitle());
                   mAuthor.setText(result.getAuthor());
                   mDesc.setText(result.getDesc());

                   getMainData = true;
                    Log.d("EpisodeFragment", "getDetailData: " + result.getTitle());
                }
            }

            @Override
            public void onFailure(Call<DetailPageResponse> call, Throwable t) {
                Log.e("EpisodeFragment", "getDetailData: " + getString(R.string.server_error_message));
                MyToast.s(getContext(), getString(R.string.server_error_message));
            }
        });
    }

    public void setThumbnail(String thb_url) {
        final String thumbnail_url = thb_url;
        Thread mThread = new Thread(){
            @Override
            public void run() {
                try{
                    URL url = new URL(thumbnail_url);

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true);
                    conn.connect();

                    // InputStream 값을 가져와 Bitmap으로 변환
                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);

                } catch (MalformedURLException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        mThread.start();;

        try {
            mThread.join();
            mImageView.setImageBitmap(bitmap);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
