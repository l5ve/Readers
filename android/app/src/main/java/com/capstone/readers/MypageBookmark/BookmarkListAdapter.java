package com.capstone.readers.MypageBookmark;

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

import com.capstone.readers.MyApp;
import com.capstone.readers.R;
import com.capstone.readers.RetrofitClient;
import com.capstone.readers.ServiceApi;
import com.capstone.readers.WebviewFragment;
import com.capstone.readers.item.UserToonEpiData;
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

public class BookmarkListAdapter extends RecyclerView.Adapter<BookmarkListAdapter.ViewHolder> {
    Context context;
    private List<BookmarkCard> mDataset;
    private Bitmap bitmap;
    private ServiceApi service;

    public BookmarkListAdapter(Context context, ArrayList<BookmarkCard> Dataset) {
        this.context = context;
        mDataset = Dataset;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageButton mUnbookmark;
        ImageView mImageView;
        TextView mPlatform;
        TextView mTitle;
        TextView mEpisode;
        TextView mAuthor;
        CardView mCardView;

        ViewHolder(View itemView) {
            super(itemView);

            mUnbookmark = itemView.findViewById(R.id.bookmark_unbookmark);
            mImageView = itemView.findViewById(R.id.bookmark_cv_image);
            mPlatform = itemView.findViewById(R.id.bookmark_cv_platform);
            mTitle = itemView.findViewById(R.id.bookmark_cv_title);
            mEpisode = itemView.findViewById(R.id.bookmark_cv_episode);
            mAuthor = itemView.findViewById(R.id.bookmark_cv_author);
            mCardView = itemView.findViewById(R.id.bookmark_cv);

            mUnbookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        deleteBookmark(mDataset.get(pos).getTood_id(), mDataset.get(pos).getEpi_title(), pos);
                    }
                }
            });

            mCardView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        String url = mDataset.get(pos).getEpi_url();

                        ((MyApp) context.getApplicationContext()).setEpisodeUrl(url);

                        AppCompatActivity aca = (AppCompatActivity) view.getContext();
                        Fragment fg = WebviewFragment.newInstance();
                        aca.getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, fg).addToBackStack(null).commit();
                    }

                }
            });
        }
    }

    private void deleteBookmark(String toon_id, String epi_title, final int position) {
        String user_id = ((MyApp) context.getApplicationContext()).getUser_id();
        UserToonEpiData data = new UserToonEpiData(user_id, toon_id, epi_title);
        service.deleteBookmark(data).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    Log.d("BookmarkListAdapter", "deleteBookmark: " + context.getString(R.string.deletebookmark_success));
                    mDataset.remove(position);
                    notifyDataSetChanged();
                }
                else {
                    Log.e("BookmarkListAdapter", "deleteBookmark" + context.getString(R.string.deletebookmark_fail));
                    MyToast.s(context, context.getString(R.string.deletebookmark_fail));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("BookmarkListAdapter", "deleteBookmark: " + context.getString(R.string.server_error));
                MyToast.s(context, context.getString(R.string.server_error));
            }
        });
    }

    @Override
    public BookmarkListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recyclerview_bookmark, parent, false);

        return new BookmarkListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BookmarkListAdapter.ViewHolder holder, int position) {
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
        holder.mEpisode.setText(mDataset.get(position).getEpi_title());
        holder.mAuthor.setText(mDataset.get(position).getAuthor());
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

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
