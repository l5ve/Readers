package com.capstone.readers.EpisodeCard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class EpisodeListAdapter extends RecyclerView.Adapter<EpisodeListAdapter.ViewHolder> {
    Context context;
    private List<EpisodeCard> mDataset;
    Bitmap bitmap;
    private ServiceApi service;

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
        LinearLayout mLayout;

        ViewHolder(View itemView) {
            super(itemView);

            mBookmark = itemView.findViewById(R.id.episode_cv_bookmark);
            mThumbnail = itemView.findViewById(R.id.episode_cv_thumbnail);
            mTitle = itemView.findViewById(R.id.episode_cv_title);
            mUpdate = itemView.findViewById(R.id.episode_cv_date);
            mCardView = itemView.findViewById(R.id.episode_cv);
            mLayout = itemView.findViewById(R.id.episode_layout);

            mBookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        /* unbookmarked 상태에서 버튼을 누른 경우 (북마크 추가 시도) */
                        if (mDataset.get(pos).getIsBookmarked() == 1) {
                            deleteBookmark(mDataset.get(pos).getToon_id(), mDataset.get(pos).getEpi_title(), pos);
                        }
                        /* bookmarked 상태에서 버튼을 누른 경우 (북마크 해제 시도) */
                        else {
                            addBookmark(mDataset.get(pos).getToon_id(), mDataset.get(pos).getEpi_title(), pos);
                        }
                    }
                }
            });

            mLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        String url = mDataset.get(pos).getEpi_url();

                        ((MyApp) context.getApplicationContext()).setEpisodeUrl(url);
                        AppCompatActivity aca = (AppCompatActivity) v.getContext();
                        Fragment fg = WebviewFragment.newInstance();
                        aca.getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, fg).addToBackStack(null).commit();
                    }
                }
            });

        }
    }

    public void addBookmark(String toon_id, String epi_title, final int position) {
        String user_id = ((MyApp) context.getApplicationContext()).getUser_id();
        UserToonEpiData data = new UserToonEpiData(user_id, toon_id, epi_title);
        service.addBookmark(data).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    Log.d("EpisodeListAdapter", "addBookmark: " + context.getString(R.string.addbookmark_success));
                    mDataset.get(position).setIsBookmarked(1);
                    notifyDataSetChanged();
                    //notifyItemChanged(position);
                }
                else {
                    Log.e("EpisodeListAdapter", "addBookmark" + context.getString(R.string.addbookmark_fail));
                    MyToast.s(context, context.getString(R.string.addbookmark_fail));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("EpisodeListAdapter", "addBookmark: " + context.getString(R.string.server_error));
                MyToast.s(context, context.getString(R.string.server_error));
            }
        });
    }

    public void deleteBookmark(String toon_id, String epi_title, final int position) {
        String user_id = ((MyApp) context.getApplicationContext()).getUser_id();
        UserToonEpiData data = new UserToonEpiData(user_id, toon_id, epi_title);
        service.deleteBookmark(data).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    Log.d("EpisodeListAdapter", "deleteBookmark: " + context.getString(R.string.deletebookmark_success));
                    mDataset.get(position).setIsBookmarked(0);
                    notifyDataSetChanged();
                    //notifyItemChanged(position);
                }
                else {
                    Log.e("EpisodeListAdapter", "deleteBookmark" + context.getString(R.string.deletebookmark_fail));
                    MyToast.s(context, context.getString(R.string.deletebookmark_fail));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("EpisodeListAdapter", "deleteBookmark: " + context.getString(R.string.server_error));
                MyToast.s(context, context.getString(R.string.server_error));
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
        holder.setIsRecyclable(false);
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

        if (mDataset.get(holder.getAdapterPosition()).getIsBookmarked() == 1) {
            holder.mBookmark.setImageResource(R.drawable.bookmarked);
        }
        holder.mTitle.setText(mDataset.get(position).getEpi_title());
        holder.mUpdate.setText(mDataset.get(position).getEpi_date());

        service = RetrofitClient.getClient().create(ServiceApi.class);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}