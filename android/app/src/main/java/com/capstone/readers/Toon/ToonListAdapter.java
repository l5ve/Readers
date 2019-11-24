package com.capstone.readers.Toon;

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

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ToonListAdapter extends RecyclerView.Adapter<ToonListAdapter.ViewHolder> {
    Context context;
    private List<ToonCard> mDataset;
    Bitmap bitmap;

    public ToonListAdapter(Context context, ArrayList<ToonCard> Dataset) {
        this.context = context;
        mDataset = Dataset;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView mPlatform;
        TextView mTitle;
        TextView mAuthor;
        CardView mCardView;

        ViewHolder(View itemView) {
            super(itemView);

            // 뷰 객체에 대한 참조
            mImageView = itemView.findViewById(R.id.toon_cv_image);
            mPlatform = itemView.findViewById(R.id.toon_cv_platform);
            mTitle = itemView.findViewById(R.id.toon_cv_title);
            mAuthor = itemView.findViewById(R.id.toon_cv_author);
            mCardView = itemView.findViewById(R.id.toon_cv);

            mCardView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    ((MyApp) context.getApplicationContext()).setDetail_page_info(mDataset.get(getAdapterPosition()));

                    AppCompatActivity aca = (AppCompatActivity) view.getContext();
                    Fragment fg = EpisodeFragment.newInstance();
                    aca.getSupportFragmentManager().beginTransaction().replace(R.id.frag1_container, fg).addToBackStack(null).commit();
                }

            });
        }
    }

    @Override
    public ToonListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int ViewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recyclerview_toon, parent, false);

        return new ToonListAdapter.ViewHolder(view);
    }


    // onBindViewHolder() position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(ToonListAdapter.ViewHolder holder, int position){
        final ToonCard item = mDataset.get(position);
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

        holder.mPlatform.setText(mDataset.get(position).getPlatform());
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
        }
        holder.mTitle.setText(mDataset.get(position).getTitle());
        holder.mAuthor.setText(mDataset.get(position).getAuthor());
    }

    // getItemCount() 전체 데이터 갯수 리턴
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}