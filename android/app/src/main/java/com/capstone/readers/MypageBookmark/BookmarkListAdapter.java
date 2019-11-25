package com.capstone.readers.MypageBookmark;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.capstone.readers.EpisodeCard.EpisodeFragment;
import com.capstone.readers.MyApp;
import com.capstone.readers.R;
import com.capstone.readers.Toon.ToonCard;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BookmarkListAdapter extends RecyclerView.Adapter<BookmarkListAdapter.ViewHolder> {
    Context context;
    private List<BookmarkCard> mDataset;
    private Bitmap bitmap;

    public BookmarkListAdapter(Context context, ArrayList<BookmarkCard> Dataset) {
        this.context = context;
        mDataset = Dataset;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView mPlatform;
        TextView mTitle;
        TextView mEpisode;
        TextView mAuthor;
        CardView mCardView;

        ViewHolder(View itemView) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.bookmark_cv_image);
            mPlatform = itemView.findViewById(R.id.bookmark_cv_platform);
            mTitle = itemView.findViewById(R.id.bookmark_cv_title);
            mEpisode = itemView.findViewById(R.id.bookmark_cv_episode);
            mAuthor = itemView.findViewById(R.id.bookmark_cv_author);
            mCardView = itemView.findViewById(R.id.bookmark_cv);

            mCardView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    BookmarkCard temp = mDataset.get(getAdapterPosition());
                    ToonCard data = new ToonCard(temp.getTood_id(), temp.getTitle(), temp.getPlatform(), temp.getAuthor(), temp.getThumbnail(), "");
                    ((MyApp) context.getApplicationContext()).setDetail_page_info(data);

                    AppCompatActivity aca = (AppCompatActivity) view.getContext();
                    Fragment fg = EpisodeFragment.newInstance();
                    aca.getSupportFragmentManager().beginTransaction().replace(R.id.mypage_fragment, fg).addToBackStack(null).commit();
                }
            });
        }
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
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
