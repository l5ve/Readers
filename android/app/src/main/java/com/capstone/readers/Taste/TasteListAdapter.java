package com.capstone.readers.Taste;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.recyclerview.widget.RecyclerView;

import com.capstone.readers.MyApp;
import com.capstone.readers.R;

import java.util.ArrayList;

public class TasteListAdapter extends RecyclerView.Adapter<TasteListAdapter.ViewHolder> {
    Context context;
    private ArrayList<String> mDataset;

    public TasteListAdapter(Context context, ArrayList<String> Dataset) {
        this.context = context;
        this.mDataset = Dataset;
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {
        Button mButton;
        boolean checked = false;

        ViewHolder(View itemView) {
            super(itemView);

            mButton = itemView.findViewById(R.id.taste_button);

            mButton.setOnClickListener(new Button.OnClickListener(){
                @Override
                public void onClick(View view) {
                    checked = !checked;
                    if(checked) {
                        mButton.setBackgroundDrawable(view.getResources().getDrawable(R.drawable.taste_button_clicked));
                        ((MyApp) context.getApplicationContext()).setGenre_selected(getAdapterPosition(), true);
                    }
                    else {
                        mButton.setBackgroundDrawable(view.getResources().getDrawable(R.drawable.taste_button));
                        ((MyApp) context.getApplicationContext()).setGenre_selected(getAdapterPosition(), false);
                    }
                }
            });

        }
    }

    @Override
    public TasteListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int ViewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recyclerview_taste, parent, false);

        return new TasteListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TasteListAdapter.ViewHolder holder, int position) {
        holder.mButton.setText(mDataset.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
