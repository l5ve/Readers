package com.capstone.readers.EpisodeCard;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.capstone.readers.MyApp;
import com.capstone.readers.R;
import com.capstone.readers.RetrofitClient;
import com.capstone.readers.ServiceApi;
import com.capstone.readers.ToonCard.ToonListAdapter;
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

public class EpisodeListAdapter extends RecyclerView.Adapter<EpisodeListAdapter.ViewHolder> {
    Context context;
    private List<EpisodeCard> mDataset;
    Bitmap bitmap;
    private ServiceApi service;
    private String toon_id;
    private String epi_title;
    private boolean bookmakred;

    public EpisodeListAdapter(Context context, ArrayList<EpisodeCard> Dataset) {
        this.context = context;
        mDataset = Dataset;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mBookmark;
        ImageView mThumbnail;
        TextView mTitle;
        TextView mUpdate;
        CardView mCardView;

        ViewHolder(View itemView) {
            super(itemView);

            mBookmark = itemView.findViewById(R.id.episode_cv_bookmark);
            mThumbnail = itemView.findViewById(R.id.episode_cv_thumbnail);
            mTitle = itemView.findViewById(R.id.episode_cv_title);
            mUpdate = itemView.findViewById(R.id.episode_cv_date);
            mCardView = itemView.findViewById(R.id.episode_cv);

            mBookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bookmakred = !bookmakred;
                    if (bookmakred) {
                        addBookmark();
                    }
                    else {
                        deleteBookmark();
                    }
                }
            });

        }
    }

    public void addBookmark() {
        String user_id = ((MyApp) context.getApplicationContext()).getUser_id();
        UserToonEpiData data = new UserToonEpiData(user_id, toon_id, epi_title);
        service.addBookmark(data).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    Log.d("EpisodeListAdapter", "addBookmark: " + context.getString(R.string.addbookmark_success));
                    MyToast.s(context, context.getString(R.string.addbookmark_success));
                    bookmakred = true;
                    notifyDataSetChanged();
                }
                else {
                    Log.e("EpisodeListAdapter", "addBookmark" + context.getString(R.string.addbookmark_fail));
                    MyToast.s(context, context.getString(R.string.addbookmark_fail));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("EpisodeListAdapter", "addBookmark: " + context.getString(R.string.toon_server_error));
                MyToast.s(context, context.getString(R.string.toon_server_error));
            }
        });
    }

    public void deleteBookmark() {
        String user_id = ((MyApp) context.getApplicationContext()).getUser_id();
        UserToonEpiData data = new UserToonEpiData(user_id, toon_id, epi_title);
        service.deleteBookmark(data).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    Log.d("EpisodeListAdapter", "deleteBookmark: " + context.getString(R.string.deletebookmark_success));
                    MyToast.s(context, context.getString(R.string.deletebookmark_success));
                    bookmakred = false;
                    notifyDataSetChanged();
                }
                else {
                    Log.e("EpisodeListAdapter", "deleteBookmark" + context.getString(R.string.deletebookmark_fail));
                    MyToast.s(context, context.getString(R.string.deletebookmark_fail));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("EpisodeListAdapter", "deleteBookmark: " + context.getString(R.string.toon_server_error));
                MyToast.s(context, context.getString(R.string.toon_server_error));
            }
        });
    }

    @Override
    public EpisodeListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int ViewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recyclerview_episode, parent, false);

        return new EpisodeListAdapter.ViewHolder(view);
    }

    // onBindViewHolder() position에 해당하는 데이터의 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(EpisodeListAdapter.ViewHolder holder, int position) {
        final EpisodeCard item = mDataset.get(position);
        final int pos = position;
        Thread mThread = new Thread(){
            @Override
            public void run() {
                try{
                    URL url = new URL(mDataset.get(pos).getEpi_thumbnail());

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
            holder.mThumbnail.setImageBitmap(bitmap);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        holder.mTitle.setText(mDataset.get(position).getEpi_title());
        holder.mUpdate.setText(mDataset.get(position).getEpi_date());
        if (mDataset.get(position).getIsBookmarked() == 1) {
            holder.mBookmark.setImageResource(R.drawable.bookmarked);
            bookmakred = true;
        } else {
            bookmakred = false;
        }

        toon_id = mDataset.get(position).getToon_id();
        service = RetrofitClient.getClient().create(ServiceApi.class);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}