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

        holder.mTitle.setText(mDataset.get(position).getTitle());
        holder.mUpdate.setText(mDataset.get(position).getUpdate());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}