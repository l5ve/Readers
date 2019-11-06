package com.capstone.readers.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.capstone.readers.R;
import com.capstone.readers.item.MemoData;
import com.capstone.readers.lib.MyToast;

import java.util.ArrayList;

// 메모 리스트의 메모를 처리하는 어댑터
public class MemoListAdapter extends RecyclerView.Adapter<MemoListAdapter.ViewHolder> {
    Context context;
    private ArrayList<MemoCard> mDataset;

    // 생성자에서 데이터 리스트 객체를 전달받음
    public MemoListAdapter(ArrayList<MemoCard> Dataset) {
        mDataset = Dataset;
    }

    // onCreateViewHolder() 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴
    @Override
    public MemoListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recyclerview_memo, parent, false);
        MemoListAdapter.ViewHolder vh = new MemoListAdapter.ViewHolder(view);

        return vh;
    }

    // onBindViewHolder() position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(MemoListAdapter.ViewHolder holder, int position){
        final String a = mDataset.get(position).memo;
        holder.mImageView.setImageResource(mDataset.get(position).img);
        holder.mTextView.setText(mDataset.get(position).memo);
        holder.mCardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                MyToast.s(context, a);
            }
        });
    }

    // getItemCount() 전체 데이터 갯수 리턴
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView mTextView;
        CardView mCardView;

        ViewHolder(View itemView) {
            super(itemView);

            // 뷰 객체에 대한 참조
            mImageView = itemView.findViewById(R.id.memo_image);
            mTextView = itemView.findViewById(R.id.memo_text);
            mCardView = itemView.findViewById(R.id.memo_cardview);
        }
    }
}

class MemoCard{
    public String memo;
    public int img;
    public MemoCard(String memo, int img){
        this.memo = memo;
        this.img = img;
    }
}

