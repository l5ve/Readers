package com.capstone.readers.MemoCard;

import android.content.Context;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.service.autofill.Dataset;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.capstone.readers.R;
import com.capstone.readers.lib.MyToast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

// 메모 리스트의 메모를 처리하는 어댑터
public class MemoListAdapter extends RecyclerView.Adapter<MemoListAdapter.ViewHolder> {
    final boolean OrderNew = true;
    final boolean OrderOld = false;
    Context context;
    private List<MemoCard> mDataset;

    // 생성자에서 데이터 리스트 객체를 전달받음
    public MemoListAdapter(Context context, ArrayList<MemoCard> Dataset, boolean OrderType) {
        this.context = context;
        mDataset = Dataset;
        /* 최근 순 */
        if(OrderType) {
            Collections.sort(mDataset, new Comparator<MemoCard>() {
                public int compare(MemoCard obj1, MemoCard obj2) {
                    return obj2.update.compareTo(obj1.update);
                }
            });
        }
        /* 오래된 순 */
        else {
            Collections.sort(mDataset, new Comparator<MemoCard>() {
                public int compare(MemoCard obj1, MemoCard obj2) {
                    return obj1.update.compareTo(obj2.update);
                }
            });
        }
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
        /*myImgView.setImageDrawable(getResources().getDrawable(R.drawable.monkey));
         * myImgView.setImageDrawable(getResources().getDrawable(R.drawable.monkey, getApplicationContext().getTheme()));
        * */
        final String a = mDataset.get(position).memo;
        holder.mImageView.setImageResource(R.drawable.naver);
        holder.mPlatform.setText(mDataset.get(position).platform);
        // String text = viewHolder.itemView.getContext().getString(R.string.mystring);
        switch(mDataset.get(position).platform){
            case "네이버웹툰":
                holder.mPlatform.setTextColor(context.getResources().getColor(R.color.NaverGreen));
                break;
            case "다음웹툰":
                holder.mPlatform.setTextColor(context.getResources().getColor(R.color.DaumBlue));
                break;
            case "레진코믹스":
                holder.mPlatform.setTextColor(context.getResources().getColor(R.color.LezhinRed));
                break;
        }
        holder.mTitle.setText(mDataset.get(position).title);
        holder.mAuthor.setText(mDataset.get(position).author);
        holder.mMemo.setText(mDataset.get(position).memo);
        holder.mUpdate.setText(mDataset.get(position).update);
        Log.d("MemoListAdapter", "Put Dataset(" + position + ") " + mDataset.get(position).img + ", " + mDataset.get(position).memo);
    }

    // getItemCount() 전체 데이터 갯수 리턴
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}

class MemoCard{
    public String id;
    public int img;
    public String platform;
    public String title;
    public String author;
    public String memo;
    public String update;

    public MemoCard(String id, int img, String platform, String title, String author, String memo, String update){
        this.id = id;
        this.img = img;
        this.platform = platform;
        this.title = title;
        this.author = author;
        this.memo = memo;
        this.update = update;
    }
}