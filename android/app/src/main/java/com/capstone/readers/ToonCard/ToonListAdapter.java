package com.capstone.readers.ToonCard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.capstone.readers.EpisodeCard.EpisodeFragment;
import com.capstone.readers.MainActivity;
import com.capstone.readers.MemoCard.MemoListAdapter;
import com.capstone.readers.Menu1Fragment;
import com.capstone.readers.MyApp;
import com.capstone.readers.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import retrofit2.http.Url;

public class ToonListAdapter extends RecyclerView.Adapter<ToonListAdapter.ViewHolder> {


    int OrderType; // 1: 제목순, 2: 업데이트순, 3: 연재처순
    Context context;
    private List<ToonCard> mDataset;
    Bitmap bitmap;

    public ToonListAdapter(Context context, ArrayList<ToonCard> Dataset, int OrderType) {
        this.context = context;
        mDataset = Dataset;
        this.OrderType = OrderType;

        /* 제목 순 */
        if (OrderType == 1) {
            Collections.sort(mDataset, new Comparator<ToonCard>() {
                public int compare(ToonCard o1, ToonCard o2) {
                    return o1.getTitle().compareTo(o2.getTitle());
                }
            });
        }
        /* 업데이트 순 */
        else if (OrderType == 2) {
            Collections.sort(mDataset, new Comparator<ToonCard>() {
                public int compare(ToonCard o1, ToonCard o2) {
                    return o2.getUpdate().compareTo(o1.getUpdate());
                }
            });
        }
        /* 연재처 순 */
        else if (OrderType == 3) {
            Collections.sort(mDataset, new Comparator<ToonCard>() {
                public int compare(ToonCard o1, ToonCard o2) {
                    return o1.getPlatform().compareTo(o2.getPlatform());
                }
            });
        }
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
                holder.mPlatform.setText("NAVER");
                holder.mPlatform.setTextColor(context.getResources().getColor(R.color.NaverGreen));
                break;
            case "다음웹툰":
                holder.mPlatform.setTextColor(context.getResources().getColor(R.color.DaumBlue));
                break;
            case "레진코믹스":
                holder.mPlatform.setTextColor(context.getResources().getColor(R.color.LezhinRed));
                break;
        }
        holder.mTitle.setText(mDataset.get(position).getTitle());
        holder.mAuthor.setText(mDataset.get(position).getAuthor());
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    // getItemCount() 전체 데이터 갯수 리턴
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
