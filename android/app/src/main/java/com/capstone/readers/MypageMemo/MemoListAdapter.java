package com.capstone.readers.MypageMemo;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

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

// 메모 리스트의 메모를 처리하는 어댑터
public class MemoListAdapter extends RecyclerView.Adapter<MemoListAdapter.ViewHolder> {
    final boolean OrderNew = true;
    final boolean OrderOld = false;
    Context context;
    private List<MemoCard> mDataset;
    private Bitmap bitmap;

    // 생성자에서 데이터 리스트 객체를 전달받음
    public MemoListAdapter(Context context, ArrayList<MemoCard> Dataset) {
        this.context = context;
        mDataset = Dataset;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView mPlatform;
        TextView mTitle;
        TextView mAuthor;
        TextView mMemo;
        TextView mUpdate;
        CardView mCardView;

        ViewHolder(View itemView) {
            super(itemView);

            // 뷰 객체에 대한 참조
            mImageView = itemView.findViewById(R.id.memo_cv_image);
            mPlatform = itemView.findViewById(R.id.memo_cv_platform);
            mTitle = itemView.findViewById(R.id.memo_cv_title);
            mAuthor = itemView.findViewById(R.id.memo_cv_author);
            mMemo = itemView.findViewById(R.id.memo_cv_text);
            mUpdate = itemView.findViewById(R.id.memo_cv_update);
            mCardView = itemView.findViewById(R.id.memo_cv);

            mCardView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    MemoCard temp = mDataset.get(getAdapterPosition());
                    ToonCard data = new ToonCard(temp.getToon_id(), temp.getTitle(), temp.getPlatform(), temp.getAuthor(), temp.getThumbnail(), temp.getMemo_date());
                    ((MyApp) context.getApplicationContext()).setDetail_page_info(data);

                    AppCompatActivity aca = (AppCompatActivity) view.getContext();
                    Fragment fg = EpisodeFragment.newInstance();
                    aca.getSupportFragmentManager().beginTransaction().replace(R.id.mypage_fragment, fg).addToBackStack(null).commit();
                }
            });
        }
    }

    // onCreateViewHolder() 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴
    @Override
    public MemoListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recyclerview_memo, parent, false);

        return new MemoListAdapter.ViewHolder(view);
    }

    // onBindViewHolder() position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(MemoListAdapter.ViewHolder holder, int position){
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
        holder.mMemo.setText(mDataset.get(position).getContent());
        holder.mUpdate.setText(mDataset.get(position).getMemo_date());
    }

    // getItemCount() 전체 데이터 갯수 리턴
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}