package com.makman.rivertracker;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by sam on 4/21/16.
 */
public class AlertRecyclerViewAdapter extends RecyclerView.Adapter<AlertRecyclerViewAdapter.AlertViewHolder>{

    Alert[] mAlerts;

    public AlertRecyclerViewAdapter(Alert[] mAlerts) {
        this.mAlerts = mAlerts;
    }

    @Override
    public AlertRecyclerViewAdapter.AlertViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View row = inflater.inflate(R.layout.row_alert, parent, false);
        return new AlertViewHolder(row);
    }

    @Override
    public void onBindViewHolder(AlertRecyclerViewAdapter.AlertViewHolder holder, int position) {
        Alert alert = mAlerts[position];
        holder.title.setText(alert.getmTitle());
        holder.details.setText(alert.getmDescription());
        holder.timestamp.setText(alert.getmDate());
    }

    @Override
    public int getItemCount() {
        return mAlerts.length;
    }

    static class AlertViewHolder extends RecyclerView.ViewHolder {

        TextView title, details, timestamp;
        public AlertViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.row_alert_title);
            details = (TextView) itemView.findViewById(R.id.row_alert_details);
            timestamp = (TextView) itemView.findViewById(R.id.row_alert_timestamp);

        }
    }
}

