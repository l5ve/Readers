package com.capstone.readers.MypageMemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.capstone.readers.EpisodeCard.EpisodeFragment;
import com.capstone.readers.MyApp;
import com.capstone.readers.R;
import com.capstone.readers.RetrofitClient;
import com.capstone.readers.ServiceApi;
import com.capstone.readers.Toon.ToonCard;
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

// 메모 리스트의 메모를 처리하는 어댑터
public class MemoListAdapter extends RecyclerView.Adapter<MemoListAdapter.ViewHolder> {
    final boolean OrderNew = true;
    final boolean OrderOld = false;
    Context context;
    private List<MemoCard> mDataset;
    private Bitmap bitmap;
    private ServiceApi service;

    // 생성자에서 데이터 리스트 객체를 전달받음
    public MemoListAdapter(Context context, ArrayList<MemoCard> Dataset) {
        this.context = context;
        mDataset = Dataset;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView mPlatform;
        TextView mTitle;
        TextView mAuthor;
        TextView mMemo;
        TextView mUpdate;
        CardView mCardView;
        ImageButton mDelete;

        ViewHolder(View itemView) {
            super(itemView);

            // 뷰 객체에 대한 참조
            mImageView = (ImageView) itemView.findViewById(R.id.memo_cv_image);
            mPlatform = (TextView) itemView.findViewById(R.id.memo_cv_platform);
            mTitle = (TextView) itemView.findViewById(R.id.memo_cv_title);
            mAuthor = (TextView) itemView.findViewById(R.id.memo_cv_author);
            mMemo = (TextView) itemView.findViewById(R.id.memo_cv_text);
            mUpdate = (TextView) itemView.findViewById(R.id.memo_cv_update);
            mCardView = (CardView) itemView.findViewById(R.id.memo_cv);
            mDelete = (ImageButton) itemView.findViewById(R.id.memo_cv_delete);

            mCardView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        MemoCard temp = mDataset.get(pos);
                        ToonCard data = new ToonCard(temp.getToon_id(), temp.getTitle(), temp.getPlatform(), temp.getAuthor(), temp.getThumbnail(), "");

                        ((MyApp) context.getApplicationContext()).setFromMemo(true);
                        ((MyApp) context.getApplicationContext()).setDetail_page_info(data);
                        ((MyApp) context.getApplicationContext()).setMemoPos(pos);
                        ((MyApp) context.getApplicationContext()).setMemoDataset(mDataset);
                        AppCompatActivity aca = (AppCompatActivity) view.getContext();
                        Fragment fg = EpisodeFragment.newInstance();
                        aca.getSupportFragmentManager().beginTransaction().replace(R.id.mypage_fragment, fg).addToBackStack(null).commit();
                    }
                }
            });

            mDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        deleteMemo(mDataset.get(pos).getToon_id(), pos);
                    }
                }
            });
        }
    }


    public void deleteMemo(String toon_id, final int position) {
        String user_id = ((MyApp) context.getApplicationContext()).getUser_id();
        UserToonData utdata = new UserToonData(user_id, toon_id);
        service.deleteMemo(utdata).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    Log.d("MemoListAdapter", "deleteMemo: " + context.getResources().getString(R.string.memo_delete_success));
                    mDataset.remove(position);
                    notifyItemRemoved(position);
                } else {
                    Log.e("MemoListAdapter", "deleteMemo: " + context.getResources().getString(R.string.memo_delete_fail));
                    MyToast.s(context, context.getResources().getString(R.string.memo_delete_fail));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("MemoListAdapter", "deleteMemo: " + context.getResources().getString(R.string.server_error));
                MyToast.s(context, context.getResources().getString(R.string.server_error));
            }
        });
    }

    // onCreateViewHolder() 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴
    @Override
    public MemoListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recyclerview_memo, parent, false);

        return new MemoListAdapter.ViewHolder(view);
    }

    // onBindViewHolder() position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(MemoListAdapter.ViewHolder holder, int position){
        final int pos = position;
        Thread mThread = new Thread(){
            @Override
            public void run() {
                try{
                    URL url = new URL(mDataset.get(pos).getThumbnail());

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
            holder.mImageView.setImageBitmap(bitmap);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        holder.mTitle.setText(mDataset.get(position).getTitle());
        holder.mAuthor.setText(mDataset.get(position).getAuthor());
        holder.mMemo.setText(mDataset.get(position).getContent());
        holder.mUpdate.setText(mDataset.get(position).getMemo_date());
        switch(mDataset.get(position).getPlatform()){
            case "naver":
                holder.mPlatform.setText(Html.fromHtml(context.getResources().getString(R.string.naver_colored)));
                break;
            case "daum":
                holder.mPlatform.setText(Html.fromHtml(context.getResources().getString(R.string.daum_colored)));
                break;
            case "lezhin":
                holder.mPlatform.setText(Html.fromHtml(context.getResources().getString(R.string.lezhin_colored)));
                break;
            case "mrblue":
                holder.mPlatform.setText(Html.fromHtml(context.getResources().getString(R.string.mrblue_colored)));
                break;
            case "buff":
                holder.mPlatform.setText(Html.fromHtml(context.getResources().getString(R.string.bufftoon_colored)));
                break;
            case "bomtoon":
                holder.mPlatform.setText(Html.fromHtml(context.getResources().getString(R.string.bomtoon_colored)));
                break;
            case "bbuding":
                holder.mPlatform.setText(Html.fromHtml(context.getResources().getString(R.string.bbuding_colored)));
                break;
            case "kakao":
                holder.mPlatform.setText(Html.fromHtml(context.getResources().getString(R.string.kakaopage_colored)));
                break;
            case "comica":
                holder.mPlatform.setText(Html.fromHtml(context.getResources().getString(R.string.comica_colored)));
                break;
            case "comicgt":
                holder.mPlatform.setText(Html.fromHtml(context.getResources().getString(R.string.comicgt_colored)));
                break;
            case "ktoon":
                holder.mPlatform.setText(Html.fromHtml(context.getResources().getString(R.string.ktoon_colored)));
                break;
            case "toptoon":
                holder.mPlatform.setText(Html.fromHtml(context.getResources().getString(R.string.toptoon_colored)));
                break;
            case "toomics":
                holder.mPlatform.setText(Html.fromHtml(context.getResources().getString(R.string.toomics_colored)));
                break;
            case "peanutoon":
                holder.mPlatform.setText(Html.fromHtml(context.getResources().getString(R.string.peanutoon_colored)));
                break;
        }
        service = RetrofitClient.getClient().create(ServiceApi.class);
    }

    // getItemCount() 전체 데이터 갯수 리턴
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}