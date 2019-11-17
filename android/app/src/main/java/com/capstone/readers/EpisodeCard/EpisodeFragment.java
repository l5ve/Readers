package com.capstone.readers.EpisodeCard;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capstone.readers.MyApp;
import com.capstone.readers.R;
import com.capstone.readers.RetrofitClient;
import com.capstone.readers.ToonCard.ToonCard;
import com.capstone.readers.ServiceApi;
import com.capstone.readers.item.DetailPageResponse;
import com.capstone.readers.item.MemoSaveData;
import com.capstone.readers.item.UserToonData;
import com.capstone.readers.lib.MyToast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/* 각 웹툰의 회차정보를 표시하는 Fragment */
public class EpisodeFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<EpisodeCard> myDataset;
    private UserToonData utdata;
    private DetailPageResponse mData;
    private boolean isSubscribed;
    private boolean isBlocked;

    private ToonCard info;
    private LinearLayout mSubscribe;
    private TextView mSubscribeText;
    private ImageView mSubscribeImg;
    private LinearLayout mBlock;
    private TextView mBlockText;
    private ImageView mBlockImg;
    private ImageButton mMemobtn;
    private LinearLayout mMemolayout;
    private EditText mEditText;
    private Button mMemoSave;
    private Button mMemoDelete;
    private boolean isMemoOpened;
    private ImageView mImageView;
    private TextView mPlatform;
    private TextView mTitle;
    private TextView mAuthor;
    private TextView mDesc;
    private String user_id;
    Bitmap bitmap;

    private ArrayList<String> genres;

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

        service = RetrofitClient.getClient().create(ServiceApi.class);
        info = ((MyApp) getActivity().getApplicationContext()).getDetail_page_info();
        user_id = ((MyApp) getActivity().getApplicationContext()).getUser_id();
        utdata = new UserToonData(user_id, info.getId());

        mSubscribe = (LinearLayout) fv.findViewById(R.id.detail_page_subscribe);
        mSubscribeText = (TextView) fv.findViewById(R.id.detail_page_subscribe_text);
        mSubscribeImg = (ImageView) fv.findViewById(R.id.detail_page_subscribe_icon);
        mBlock = (LinearLayout) fv.findViewById(R.id.detail_page_block);
        mBlockText = (TextView) fv.findViewById(R.id.detail_page_block_text);
        mBlockImg = (ImageView) fv.findViewById(R.id.detail_page_block_icon);
        mImageView = (ImageView) fv.findViewById(R.id.detail_page_thumbnail);
        mPlatform = (TextView) fv.findViewById(R.id.detail_page_platform);
        mTitle = (TextView) fv.findViewById(R.id.detail_page_title);
        mAuthor = (TextView) fv.findViewById(R.id.detail_page_author);
        mDesc = (TextView) fv.findViewById(R.id.detail_page_desc);

        mMemobtn = (ImageButton) fv.findViewById(R.id.detail_page_memo_btn);
        mEditText = (EditText) fv.findViewById(R.id.detail_page_memo_input);
        mMemoSave = (Button) fv.findViewById(R.id.detail_page_memo_save);
        mMemoDelete = (Button) fv.findViewById(R.id.detail_page_memo_delete);
        mMemolayout = (LinearLayout) fv.findViewById(R.id.detail_page_memo_layout);
        isMemoOpened = false;
        isSubscribed = false;
        isBlocked = false;

        getDetailPageData();

        mSubscribe.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                setSubscribeBtn();
            }
        });

        mBlock.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                setBlockBtn();
            }
        });


        /* 메모 버튼 클릭 시 메모 레이아웃 여닫기 */
        mMemobtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                isMemoOpened = !isMemoOpened;
                if(isMemoOpened){
                    mMemolayout.setVisibility(View.VISIBLE);
                } else {
                    mMemolayout.setVisibility(View.GONE);
                }
            }
        });

        mMemoSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                saveMemo();
            }
        });

        mMemoDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                deleteMemo();
            }
        });

        /* 작품 상세 페이지 상단의 작품 썸네일 설정 */
        setThumbnail(info.getThumbnail());

        mPlatform.setText(info.getPlatform());

        mTitle.setText(info.getTitle());
        mAuthor.setText(info.getAuthor());



        mRecyclerView = (RecyclerView) fv.findViewById(R.id.episode_list);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        myDataset = new ArrayList<>();
        myDataset.add(new EpisodeCard("0", "0", info.getTitle() + " 1화", "2019-01-01"));

        setAdapter();


        return fv;
    }

    public void setSubscribeBtn() {
        isSubscribed = !isSubscribed;
        if(isSubscribed) {
            subscribe();
            mSubscribeText.setTextColor(getResources().getColor(R.color.check_green));
            mSubscribeImg.setImageResource(R.drawable.check_green);
        }
        else {
            unsubscribe();
            mSubscribeText.setTextColor(getResources().getColor(R.color.colorWhite));
            mSubscribeImg.setImageResource(R.drawable.add);
        }
    }

    public void setBlockBtn() {
        isBlocked = !isBlocked;
        if(isBlocked){
            mBlockText.setTextColor(getResources().getColor(R.color.check_red));
            mBlockImg.setImageResource(R.drawable.check_red);
        }
        else {
            mBlockText.setTextColor(getResources().getColor(R.color.colorWhite));
            mBlockImg.setImageResource(R.drawable.hide);
        }
    }

    public void setAdapter() {
        mAdapter = new EpisodeListAdapter(getContext(), myDataset);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void getDetailPageData(){
        UserToonData data = new UserToonData(user_id, info.getId());
        service.getDetailPageData(data).enqueue(new Callback<ArrayList<DetailPageResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<DetailPageResponse>> call, Response<ArrayList<DetailPageResponse>> response) {
                mData = response.body().get(0);

                mDesc.setText(mData.getToon_desc());
                if(mData.getContent() != null) {
                    isMemoOpened = true;
                    mEditText.setText(mData.getContent());
                    mMemolayout.setVisibility(View.VISIBLE);
                }

                if ((double) mData.getSubs_flag() == 1)
                    setSubscribeBtn();
                if ((double) mData.getBlock_flag() == 1)
                    setBlockBtn();
            }

            @Override
            public void onFailure(Call<ArrayList<DetailPageResponse>> call, Throwable t) {
                Log.e("EpisodeFragment", "getDetailPageData: " + getString(R.string.toon_server_error));
                MyToast.s(getContext(), getString(R.string.toon_server_error));
            }
        });
    }


    public void subscribe() {
        service.subscribe(utdata).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    Log.d("EpisodeFragment", "subscribe: " + getString(R.string.subs_success));
                    MyToast.s(getContext(), getString(R.string.subs_success));
                }
                else {
                    Log.d("EpisodeFragment", "subscribe: " + getString(R.string.subs_fail));
                    MyToast.s(getContext(), getString(R.string.subs_fail));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("EpisodeFragment", "subscribe: " + getString(R.string.toon_server_error));
                MyToast.s(getContext(), getString(R.string.toon_server_error));
            }
        });
    }

    public void unsubscribe() {
        service.unsubscribe(utdata).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    Log.d("EpisodeFragment", "unsubscribe: " + getString(R.string.unsubs_success));
                    MyToast.s(getContext(), getString(R.string.unsubs_success));
                }
                else {
                    Log.d("EpisodeFragment", "unsubscribe: " + getString(R.string.unsubs_fail));
                    MyToast.s(getContext(), getString(R.string.unsubs_fail));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("EpisodeFragment", "unsubscribe: " + getString(R.string.toon_server_error));
                MyToast.s(getContext(), getString(R.string.toon_server_error));
            }
        });
    }


    public void saveMemo() {
        MemoSaveData data = new MemoSaveData(user_id, info.getId(), mEditText.getText().toString());
        service.saveMemo(data).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    Log.d("EpisodeFragment", "saveMemo: " + getString(R.string.memo_save_success));
                    MyToast.s(getContext(), getString(R.string.memo_save_success));
                } else {
                    Log.e("EpisodeFragment", "saveMemo: " + getString(R.string.memo_save_fail));
                    MyToast.s(getContext(), getString(R.string.memo_save_fail));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("EpisodeFragment", "saveMemo: " + getString(R.string.toon_server_error));
                MyToast.s(getContext(), getString(R.string.toon_server_error));
            }
        });
    }

    public void deleteMemo() {
        service.deleteMemo(utdata).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    Log.d("EpisodeFragment", "deleteMemo: " + getString(R.string.memo_delete_success));
                    MyToast.s(getContext(), getString(R.string.memo_delete_success));
                } else {
                    Log.e("EpisodeFragment", "deleteMemo: " + getString(R.string.memo_delete_fail));
                    MyToast.s(getContext(), getString(R.string.memo_delete_fail));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("EpisodeFragment", "deleteMemo: " + getString(R.string.toon_server_error));
                MyToast.s(getContext(), getString(R.string.toon_server_error));
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
