package com.makman.rivertracker;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by sam on 3/25/16.
 */

public class RiverRecyclerViewAdapter extends RecyclerView.Adapter<RiverRecyclerViewAdapter.RiverViewHolder> {

    public interface OnRiverRowClickListener{
        void onRiverRowClicked(River river);
    }

    ArrayList<River> mRivers;
    OnRiverRowClickListener mListener;

    public RiverRecyclerViewAdapter(ArrayList<River> mRivers, OnRiverRowClickListener listener) {
        this.mRivers = mRivers;
        mListener = listener;
    }

    @Override
    public RiverViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View row = inflater.inflate(R.layout.row_river, parent, false);
        return new RiverViewHolder(row);
    }

    @Override
    public void onBindViewHolder(final RiverViewHolder holder, int position) {
        River river  = mRivers.get(position);
        holder.name.setText(river.getName());
        holder.difficulty.setText(river.getDifficulty());
        holder.state.setText(river.getState());
        holder.section.setText(river.getSection());
        String url = river.getPicture().getPicture().getUrl();
        if(url != null) {
            Picasso.with(holder.name.getContext()).load(url).fit().centerCrop().into(holder.image);
        }else{
            holder.image.setVisibility(View.GONE);
        }
        String cfs;
        if(river.getCfs() == null){
            cfs = "";
        }else {
             cfs = String.format(holder.name.getContext().getString(R.string.cfs), river.getCfs());

        }
        holder.cfs.setText(cfs);
        holder.fullView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(mListener != null){
                    mListener.onRiverRowClicked(mRivers.get(holder.getAdapterPosition()));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRivers.size();
    }

    static class RiverViewHolder extends RecyclerView.ViewHolder{
        TextView name, section, cfs, difficulty, state;
        ImageView image;
        View fullView;
        public RiverViewHolder(View itemView) {
            super(itemView);
            fullView = itemView;
            name = (TextView) itemView.findViewById(R.id.row_river_name);
            section = (TextView) itemView.findViewById(R.id.row_river_section);
            cfs = (TextView) itemView.findViewById(R.id.row_river_cfs);
            difficulty = (TextView) itemView.findViewById(R.id.row_river_difficulty);
            state = (TextView) itemView.findViewById(R.id.row_river_state);
            image = (ImageView) itemView.findViewById(R.id.row_river_image);
        }
    }
}
