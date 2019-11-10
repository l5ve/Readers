package com.capstone.readers.ToonCard;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.capstone.readers.MemoCard.MemoListAdapter;
import com.capstone.readers.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ToonListAdapter extends RecyclerView.Adapter<ToonListAdapter.ViewHolder> {
    int OrderType; // 1: 제목순, 2: 업데이트순, 3: 연재처순
    Context context;
    private List<ToonCard> mDataset;

    public ToonListAdapter(Context context, ArrayList<ToonCard> Dataset, int OrderType) {
        this.context = context;
        mDataset = Dataset;
        this.OrderType = OrderType;
        /* 제목 순 */
        if (OrderType == 1) {
            Collections.sort(mDataset, new Comparator<ToonCard>() {
                public int compare(ToonCard o1, ToonCard o2) {
                    return o1.title.compareTo(o2.title);
                }
            });
        }
        /* 업데이트 순 */
        else if (OrderType == 2) {
            Collections.sort(mDataset, new Comparator<ToonCard>() {
                public int compare(ToonCard o1, ToonCard o2) {
                    return o2.update.compareTo(o1.update);
                }
            });
        }
        /* 연재처 순 */
        else if (OrderType == 3) {
            Collections.sort(mDataset, new Comparator<ToonCard>() {
                public int compare(ToonCard o1, ToonCard o2) {
                    return o1.platform.compareTo(o2.platform);
                }
            });
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView mPlatform;
        TextView mTitle;
        TextView mAuthor;
        TextView mUpdate;
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
        holder.mImageView.setImageResource(R.drawable.profile_image_temp);
        holder.mPlatform.setText(mDataset.get(position).platform);
        switch(mDataset.get(position).platform){
            case "naver":
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
        Log.d("ToonListAdapter", "Put Dataset(" + position + ") " + mDataset.get(position).platform + " " + mDataset.get(position).title);
    }

    // getItemCount() 전체 데이터 갯수 리턴
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
