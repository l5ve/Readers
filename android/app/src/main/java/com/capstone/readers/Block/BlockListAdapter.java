package com.capstone.readers.Block;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.capstone.readers.MyApp;
import com.capstone.readers.R;
import com.capstone.readers.RetrofitClient;
import com.capstone.readers.ServiceApi;
import com.capstone.readers.item.UserToonData;
import com.capstone.readers.lib.MyToast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BlockListAdapter extends RecyclerView.Adapter<BlockListAdapter.ViewHolder> {
    Context context;
    private ArrayList<BlockCard> mDataset;
    private Bitmap bitmap;
    private ServiceApi service;
    private String toon_id;
    private boolean unblocked;
    private int thispos;

    public BlockListAdapter(Context context, ArrayList<BlockCard> Dataset) {
        this.context = context;
        mDataset = Dataset;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView mPlatform;
        TextView mTitle;
        TextView mAuthor;
        CardView mCardView;
        LinearLayout mLinearLayout;

        ViewHolder(View itemView) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.block_cv_image);
            mPlatform = itemView.findViewById(R.id.block_cv_platform);
            mTitle = itemView.findViewById(R.id.block_cv_title);
            mAuthor = itemView.findViewById(R.id.block_cv_author);
            mCardView = itemView.findViewById(R.id.block_cv);
            mLinearLayout = itemView.findViewById(R.id.block_cancel_btn);
            mLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    unblock();
                }
            });
        }
    }

    public void unblock() {
        String user_id = ((MyApp) context.getApplicationContext()).getUser_id();
        UserToonData data = new UserToonData(user_id, toon_id);
        service.unblock(data).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    Log.d("BlockListAdapter", "unblock: " + context.getString(R.string.unblock_success));
                    MyToast.s(context, context.getString(R.string.unblock_success));
                    mDataset.remove(thispos);
                    notifyDataSetChanged();
                }
                else {
                    Log.d("BlockListAdapter", "unblock: " + context.getString(R.string.unblock_fail));
                    MyToast.s(context, context.getString(R.string.unblock_fail));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("BlockListAdapter", "unblock: " + context.getString(R.string.toon_server_error));
                MyToast.s(context, context.getString(R.string.toon_server_error));
            }
        });
    }

    @Override
    public BlockListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int ViewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recyclerview_block, parent, false);

        return new BlockListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BlockListAdapter.ViewHolder holder, int  position) {
        final int pos = position;
        thispos = position;
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
        toon_id = mDataset.get(position).getToon_id();
        unblocked = false;
        service = RetrofitClient.getClient().create(ServiceApi.class);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
