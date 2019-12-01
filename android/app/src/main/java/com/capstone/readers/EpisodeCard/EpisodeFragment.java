package com.capstone.readers.EpisodeCard;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capstone.readers.MyApp;
import com.capstone.readers.R;
import com.capstone.readers.RetrofitClient;
import com.capstone.readers.ServiceApi;
import com.capstone.readers.Toon.ToonCard;
import com.capstone.readers.Toon.ToonListAdapter;
import com.capstone.readers.item.DetailPageResponse;
import com.capstone.readers.item.MemoSaveData;
import com.capstone.readers.item.ToonGenreResponse;
import com.capstone.readers.item.ToonIdData;
import com.capstone.readers.item.UserToonData;
import com.capstone.readers.lib.MyToast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/* 각 웹툰의 회차정보를 표시하는 Fragment */
public class EpisodeFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<EpisodeCard> list;
    private ArrayList<EpisodeCard> myDataset;
    private UserToonData utdata;
    private DetailPageResponse mData;
    private boolean isSubscribed;
    private boolean isBlocked;
    private ArrayList<String> genres;
    private ArrayList<ToonGenreResponse> genrelist;

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
    private LinearLayout mGenreLayout;
    private String user_id;
    Bitmap bitmap;

    private int indicator;
    final int paging = 5;

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
        genres = new ArrayList<>();
        indicator = 12;

        myDataset = new ArrayList<>();

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
        mGenreLayout = (LinearLayout) fv.findViewById(R.id.detail_page_genre_layout);

        mMemobtn = (ImageButton) fv.findViewById(R.id.detail_page_memo_btn);
        mEditText = (EditText) fv.findViewById(R.id.detail_page_memo_input);
        mMemoSave = (Button) fv.findViewById(R.id.detail_page_memo_save);
        mMemoDelete = (Button) fv.findViewById(R.id.detail_page_memo_delete);
        mMemolayout = (LinearLayout) fv.findViewById(R.id.detail_page_memo_layout);
        isMemoOpened = false;
        isSubscribed = false;
        isBlocked = false;

        /* 작품 설명, 메모, 구독 여부, 숨김 여부 데이터를 받아옴 */
        getDetailPageData();
        /* 작품 장르를 받아옴 */
        getGenres();

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

        switch(info.getPlatform()){
            case "naver":
                mPlatform.setText(Html.fromHtml(getResources().getString(R.string.naver_colored)));
                break;
            case "daum":
                mPlatform.setText(Html.fromHtml(getResources().getString(R.string.daum_colored)));
                break;
            case "lezhin":
                mPlatform.setText(Html.fromHtml(getResources().getString(R.string.lezhin_colored)));
                break;
            case "mrblue":
                mPlatform.setText(Html.fromHtml(getResources().getString(R.string.mrblue_colored)));
                break;
            case "buff":
                mPlatform.setText(Html.fromHtml(getResources().getString(R.string.bufftoon_colored)));
                break;
            case "bomtoon":
                mPlatform.setText(Html.fromHtml(getResources().getString(R.string.bomtoon_colored)));
                break;
            case "bbuding":
                mPlatform.setText(Html.fromHtml(getResources().getString(R.string.bbuding_colored)));
                break;
            case "kakao":
                mPlatform.setText(Html.fromHtml(getResources().getString(R.string.kakaopage_colored)));
                break;
            case "comica":
                mPlatform.setText(Html.fromHtml(getResources().getString(R.string.comica_colored)));
                break;
            case "comicgt":
                mPlatform.setText(Html.fromHtml(getResources().getString(R.string.comicgt_colored)));
                break;
            case "ktoon":
                mPlatform.setText(Html.fromHtml(getResources().getString(R.string.ktoon_colored)));
                break;
            case "toptoon":
                mPlatform.setText(Html.fromHtml(getResources().getString(R.string.toptoon_colored)));
                break;
            case "toomics":
                mPlatform.setText(Html.fromHtml(getResources().getString(R.string.toomics_colored)));
                break;
            case "peanutoon":
                mPlatform.setText(Html.fromHtml(getResources().getString(R.string.peanutoon_colored)));
                break;
        }

        mTitle.setText(info.getTitle());
        mAuthor.setText(info.getAuthor());


        mRecyclerView = (RecyclerView) fv.findViewById(R.id.episode_list);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                Log.d("EpisodeFragment", "End of the list");
                if (indicator < list.size()) {
                    addMoreItem();
                }
            }
        });

        getEpisodeList();

        return fv;
    }

    public void setAdapter() {
        mAdapter = new EpisodeListAdapter(getContext(), myDataset);
        mRecyclerView.setAdapter(mAdapter);
    }


    public void setSubscribeBtn() {
        isSubscribed = !isSubscribed;
        if (isSubscribed && isBlocked) {
            MyToast.l(getContext(), getString(R.string.subs_notice_msg));
            isSubscribed = !isSubscribed;
        }
        else if(isSubscribed) {
            subscribe();
        }
        else {
            unsubscribe();
        }
    }

    public void setBlockBtn() {
        isBlocked = !isBlocked;
        if (isBlocked && isSubscribed) {
            MyToast.l(getContext(), getString(R.string.block_notice_msg));
            isBlocked = !isBlocked;
        }
        else if(isBlocked){
            block();
        }
        else {
            unblock();
        }
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

                if ((double) mData.getSubs_flag() == 1) {
                    isSubscribed = true;
                    mSubscribeText.setTextColor(getResources().getColor(R.color.check_green));
                    mSubscribeImg.setImageResource(R.drawable.check_green);
                    mBlock.setVisibility(View.GONE);
                }
                if ((double) mData.getBlock_flag() == 1) {
                    isBlocked = true;
                    mBlockText.setTextColor(getResources().getColor(R.color.check_red));
                    mBlockImg.setImageResource(R.drawable.check_red);
                    mSubscribe.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<DetailPageResponse>> call, Throwable t) {
                Log.e("EpisodeFragment", "getDetailPageData: " + getString(R.string.server_error));
                MyToast.s(getContext(), getString(R.string.server_error));
            }
        });
    }

    public void getGenres() {
        ToonIdData data = new ToonIdData(info.getId());
        service.getDetailGenres(data).enqueue(new Callback<ArrayList<ToonGenreResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<ToonGenreResponse>> call, Response<ArrayList<ToonGenreResponse>> response) {
                genrelist = response.body();
                if(response.code() == 200) {
                    for (int i = 0; i < genrelist.size(); i++) {
                        genres.add(genrelist.get(i).getGenre_name());

                        Button btn = new Button(getContext());
                        btn.setMinimumWidth(0);
                        btn.setWidth((int) getResources().getDimension(R.dimen.detail_page_genre_width));
                        btn.setText(genrelist.get(i).getGenre_name());
                        btn.setBackground(getResources().getDrawable(R.drawable.loginout_button_white));
                        btn.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.detail_page_genre_text_size));
                        mGenreLayout.addView(btn);
                    }
                }
                else {
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ToonGenreResponse>> call, Throwable t) {
                Log.e("EpisodeFragment", "getGenres: " + getString(R.string.server_error));
                MyToast.s(getContext(), getString(R.string.server_error));
            }
        });
    }

    public void subscribe() {
        service.subscribe(utdata).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    mSubscribeText.setTextColor(getResources().getColor(R.color.check_green));
                    mSubscribeImg.setImageResource(R.drawable.check_green);
                    mBlock.setVisibility(View.GONE);
                    Log.d("EpisodeFragment", "subscribe: " + getString(R.string.subs_success));
                }
                else {
                    isSubscribed = !isSubscribed;
                    Log.d("EpisodeFragment", "subscribe: " + getString(R.string.subs_fail));
                    MyToast.s(getContext(), getString(R.string.subs_fail));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("EpisodeFragment", "subscribe: " + getString(R.string.server_error));
                MyToast.s(getContext(), getString(R.string.server_error));
            }
        });
    }

    public void unsubscribe() {
        service.unsubscribe(utdata).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    mSubscribeText.setTextColor(getResources().getColor(R.color.colorWhite));
                    mSubscribeImg.setImageResource(R.drawable.add);
                    mBlock.setVisibility(View.VISIBLE);
                    Log.d("EpisodeFragment", "unsubscribe: " + getString(R.string.unsubs_success));

                }
                else {
                    isSubscribed = !isSubscribed;
                    Log.d("EpisodeFragment", "unsubscribe: " + getString(R.string.unsubs_fail));
                    MyToast.s(getContext(), getString(R.string.unsubs_fail));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("EpisodeFragment", "unsubscribe: " + getString(R.string.server_error));
                MyToast.s(getContext(), getString(R.string.server_error));
            }
        });
    }

    public void block() {
        service.block(utdata).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    mBlockText.setTextColor(getResources().getColor(R.color.check_red));
                    mBlockImg.setImageResource(R.drawable.check_red);
                    mSubscribe.setVisibility(View.GONE);

                    RecyclerView.Adapter adapter = ((MyApp) getContext().getApplicationContext()).getGlobalTLA();
                    int pos = ((MyApp) getContext().getApplicationContext()).getPos();
                    Log.d("EpisodeFragment", "remove item " + pos + " from the adapter");
                    List<ToonCard> mDataset = ((MyApp) getContext().getApplicationContext()).getmDataset();
                    mDataset.remove(pos);
                    adapter.notifyItemRemoved(pos);

                    Log.d("EpisodeFragment", "unblock: " + getString(R.string.block_success));
                }
                else {
                    isBlocked = !isBlocked;
                    Log.d("EpisodeFragment", "block: " + getString(R.string.block_fail));
                    MyToast.s(getContext(), getString(R.string.block_fail));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("EpisodeFragment", "block: " + getString(R.string.server_error));
                MyToast.s(getContext(), getString(R.string.server_error));
            }
        });
    }

    public void unblock() {
        service.unblock(utdata).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    mBlockText.setTextColor(getResources().getColor(R.color.colorWhite));
                    mBlockImg.setImageResource(R.drawable.hide);
                    mSubscribe.setVisibility(View.VISIBLE);

                    boolean existbefore = ((MyApp) getContext().getApplicationContext()).getExistedBefore();
                    if (existbefore) {
                        RecyclerView.Adapter adapter = ((MyApp) getContext().getApplicationContext()).getGlobalTLA();
                        int pos = ((MyApp) getContext().getApplicationContext()).getPos();
                        Log.d("EpisodeFragment", "add item " + pos + " to the adapter");
                        List<ToonCard> mDataset = ((MyApp) getContext().getApplicationContext()).getmDataset();
                        mDataset.add(pos, info);
                        adapter.notifyItemInserted(pos);
                    }
                    Log.d("EpisodeFragment", "unblock: " + getString(R.string.unblock_success));
                }
                else {
                    isBlocked = !isBlocked;
                    Log.d("EpisodeFragment", "unblock: " + getString(R.string.unblock_fail));
                    MyToast.s(getContext(), getString(R.string.unblock_fail));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("EpisodeFragment", "unblock: " + getString(R.string.server_error));
                MyToast.s(getContext(), getString(R.string.server_error));
            }
        });

    }

    public void getEpisodeList() {
        service.getEpisodeList(utdata).enqueue(new Callback<ArrayList<EpisodeCard>>() {
            @Override
            public void onResponse(Call<ArrayList<EpisodeCard>> call, Response<ArrayList<EpisodeCard>> response) {
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
            public void onFailure(Call<ArrayList<EpisodeCard>> call, Throwable t) {
                Log.e("EpisodeFragment", "getEpisodeList: " + getString(R.string.server_error));
                MyToast.s(getContext(), getString(R.string.server_error));
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
                Log.e("EpisodeFragment", "saveMemo: " + getString(R.string.server_error));
                MyToast.s(getContext(), getString(R.string.server_error));
            }
        });
    }

    public void deleteMemo() {
        service.deleteMemo(utdata).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    mEditText.getText().clear();
                    isMemoOpened = !isMemoOpened;
                    mMemolayout.setVisibility(View.GONE);
                    Log.d("EpisodeFragment", "deleteMemo: " + getString(R.string.memo_delete_success));
                    MyToast.s(getContext(), getString(R.string.memo_delete_success));
                } else {
                    Log.e("EpisodeFragment", "deleteMemo: " + getString(R.string.memo_delete_fail));
                    MyToast.s(getContext(), getString(R.string.memo_delete_fail));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("EpisodeFragment", "deleteMemo: " + getString(R.string.server_error));
                MyToast.s(getContext(), getString(R.string.server_error));
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

    public void addMoreItem() {
        if (indicator >= list.size())
            return;

        int limit = indicator + paging;
        if (limit > list.size())
            limit = list.size();

        for (int i = indicator; i < limit; i++) {
            myDataset.add(list.get(i));
            indicator++;
        }

        mAdapter.notifyItemRangeChanged(indicator, paging);
    }

}
