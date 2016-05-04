package com.makman.rivertracker;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.NetworkPolicy;
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
        River river = mRivers.get(position);
        holder.name.setText(river.getName());
        holder.difficulty.setText(river.getDifficulty());
        holder.state.setText(river.getState());
        holder.section.setText(river.getSection());
        String url = river.getPicture().getPicture().getUrl();
        if (!river.isHas_alert()) {
            holder.alertUpper.setVisibility(View.GONE);
            holder.alertLower.setVisibility(View.GONE);

        }
        if (url != null) {
            holder.alertLower.setVisibility(View.GONE);
            Picasso.with(holder.name.getContext()).load(url).fit().centerCrop().into(holder.image);
        } else {
            holder.image.setVisibility(View.GONE);
            holder.alertUpper.setVisibility(View.GONE);

        }

        if(river.getId().equals("1")){
            Log.d("temp", "white Salmon");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (river.getCfs() == 0 || (river.getMax_cfs() == 0 && river.getMin_cfs() == 0)) {
                holder.fullView.setBackground(holder.fullView.getContext().getDrawable(R.drawable.river_background_no_data));
            } else if ((river.getCfs() > river.getMin_cfs() || river.getMin_cfs() == 0) && (river.getCfs() < river.getMax_cfs()|| river.getMax_cfs()==0)) {
                holder.fullView.setBackground(holder.fullView.getContext().getDrawable(R.drawable.river_background_in));
            } else {
                holder.fullView.setBackground(holder.fullView.getContext().getDrawable(R.drawable.river_background_out));
        }
    }

        String cfs;

         cfs = String.format(holder.name.getContext().getString(R.string.cfs), river.getCfs());

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
        ImageView image, alertUpper, alertLower;
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
            alertUpper = (ImageView) itemView.findViewById(R.id.row_river_alert_icon_upper);
            alertLower = (ImageView) itemView.findViewById(R.id.row_river_alert_icon_lower);
        }
    }
}
