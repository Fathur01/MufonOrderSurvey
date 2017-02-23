package com.muf.mymuf.mobilesurvey.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.muf.mymuf.mobilesurvey.R;
import com.muf.mymuf.mobilesurvey.activity.CustomerDetailActivity;
import com.muf.mymuf.mobilesurvey.activity.MonitoringProsesSurveyActivity;
import com.muf.mymuf.mobilesurvey.list.DedupList;
import com.muf.mymuf.mobilesurvey.list.TimeLineList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 16003041 on 14/02/2017.
 */

public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.MyViewHolder> {

    private List<TimeLineList> timeLineList;
    private ArrayList<TimeLineList> arrayTimeLineList;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.monitoring_timeline_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TimeLineAdapter.MyViewHolder holder, int position) {
        TimeLineList resultList = timeLineList.get(position);
        holder.name.setText(resultList.getName());
        holder.amount.setText(resultList.getAmount() + " ITEMS");
    }

    @Override
    public int getItemCount() {
        return timeLineList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView name;
        private TextView amount;
        private ImageButton go;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            amount = (TextView) view.findViewById(R.id.amount);
            go = (ImageButton) view.findViewById(R.id.btn_go);

            go.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Integer position = getAdapterPosition();
            TimeLineList resultList = timeLineList.get(position);

            Intent i = new Intent(view.getContext(), MonitoringProsesSurveyActivity.class);
            i.putExtra("ITEM_NAME", resultList.getName());
            i.putExtra("ITEM_AMOUNT", resultList.getAmount());
            view.getContext().startActivity(i);
        }
    }

    public TimeLineAdapter(List<TimeLineList> searchResultList) {
        this.timeLineList = searchResultList;
        this.arrayTimeLineList = new ArrayList<TimeLineList>();
        this.arrayTimeLineList.addAll(searchResultList);
    }
}
