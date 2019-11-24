package com.capstone.readers.Search;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.capstone.readers.EpisodeCard.EpisodeFragment;
import com.capstone.readers.MyApp;
import com.capstone.readers.MypageMemo.MemoListAdapter;
import com.capstone.readers.R;
import com.capstone.readers.Toon.ToonCard;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.ViewHolder> {
    Context context;
    private ArrayList<SearchCard> mDataset;
    private Bitmap bitmap;

    public SearchListAdapter(Context context, ArrayList<SearchCard> Dataset) {
        this.context = context;
        mDataset = Dataset;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView mPlatform;
        TextView mTitle;
        TextView mAuthor;
        LinearLayout mLayout;

        ViewHolder(View itemView) {
            super(itemView);

            mImageView = (ImageView) itemView.findViewById(R.id.search_cv_image);
            mPlatform = (TextView) itemView.findViewById(R.id.search_cv_platform);
            mTitle = (TextView) itemView.findViewById(R.id.search_cv_title);
            mAuthor = (TextView) itemView.findViewById(R.id.search_cv_author);
            mLayout = (LinearLayout) itemView.findViewById(R.id.search_cv);

            mLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SearchCard s = mDataset.get(getAdapterPosition());
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
    public SearchListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recyclerview_search, parent, false);

        return new SearchListAdapter.ViewHolder(view);
    }

    // onBindViewHolder() position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(SearchListAdapter.ViewHolder holder, int position){
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
        holder.mTitle.setText(mDataset.get(position).getTitle());
        holder.mAuthor.setText(mDataset.get(position).getAuthor());
        }

    // getItemCount() 전체 데이터 갯수 리턴
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}