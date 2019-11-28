package com.capstone.readers.Search;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
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

public class DescSearchListAdapter extends RecyclerView.Adapter<DescSearchListAdapter.ViewHolder> {
    Context context;
    private ArrayList<DescSearchCard> mDataset;
    private Bitmap bitmap;

    public DescSearchListAdapter(Context context, ArrayList<DescSearchCard> Dataset) {
        this.context = context;
        mDataset = Dataset;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView mPlatform;
        TextView mTitle;
        TextView mAuthor;
        LinearLayout mLayout;
        TextView mDesc;

        ViewHolder(View itemView) {
            super(itemView);

            mImageView = (ImageView) itemView.findViewById(R.id.desc_search_cv_image);
            mPlatform = (TextView) itemView.findViewById(R.id.desc_search_cv_platform);
            mTitle = (TextView) itemView.findViewById(R.id.desc_search_cv_title);
            mAuthor = (TextView) itemView.findViewById(R.id.desc_search_cv_author);
            mLayout = (LinearLayout) itemView.findViewById(R.id.desc_search_cv);
            mDesc = (TextView) itemView.findViewById(R.id.desc_search_cv_description);

            mLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DescSearchCard s = mDataset.get(getAdapterPosition());
                    ToonCard data = new ToonCard(s.getToon_id(), s.getTitle(), s.getPlatform(), s.getAuthor(), s.getThumbnail(), "");
                    ((MyApp) context.getApplicationContext()).setDetail_page_info(data);

                    AppCompatActivity aca = (AppCompatActivity) v.getContext();
                    Fragment fg = EpisodeFragment.newInstance();
                    aca.getSupportFragmentManager().beginTransaction().replace(R.id.frag1_container, fg).addToBackStack(null).commit();
                }
            });

        }
    }

    // onCreateViewHolder() 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴
    @Override
    public DescSearchListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recyclerview_descsearch, parent, false);

        return new DescSearchListAdapter.ViewHolder(view);
    }

    // onBindViewHolder() position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(DescSearchListAdapter.ViewHolder holder, int position){
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
        holder.mDesc.setText(mDataset.get(position).getDescription());
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

    // getItemCount() 전체 데이터 갯수 리턴
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
