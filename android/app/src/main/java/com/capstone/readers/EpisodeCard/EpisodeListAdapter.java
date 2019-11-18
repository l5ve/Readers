package com.capstone.readers.EpisodeCard;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.capstone.readers.R;
import com.capstone.readers.ToonCard.ToonListAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class EpisodeListAdapter extends RecyclerView.Adapter<EpisodeListAdapter.ViewHolder> {
    Context context;
    private List<EpisodeCard> mDataset;
    Bitmap bitmap;

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

        }
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
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}