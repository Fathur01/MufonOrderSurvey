package com.muf.mymuf.mobilesurvey.adapter;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.muf.mymuf.mobilesurvey.R;
import com.muf.mymuf.mobilesurvey.list.SearchResultList;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by 16003041 on 17/02/2017.
 */

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.MyViewHolder>{

    private List<SearchResultList> searchResultList;
    private ArrayList<SearchResultList> arraySearchResultList;
    private Activity activity;
    private Integer requestCode;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_result_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final SearchResultList resultList = searchResultList.get(position);
        holder.labelItem1.setText(resultList.getLabelItem1());
        holder.item1.setText(resultList.getItem1());
        holder.labelItem2.setText(resultList.getLabelItem2());
        holder.item2.setText(resultList.getItem2());
        holder.labelItem3.setText(resultList.getLabelItem3());
        holder.item3.setText(resultList.getItem3());
        holder.labelItem4.setText(resultList.getLabelItem4());
        holder.item4.setText(resultList.getItem4());
        holder.labelItem5.setText(resultList.getLabelItem5());
        holder.item5.setText(resultList.getItem5());
        holder.labelItem6.setText(resultList.getLabelItem6());
        holder.item6.setText(resultList.getItem6());

        if(resultList.getLabelItem1().equals("") || resultList.getItem1().equals("")) {
            if(resultList.getLabelItem2().equals("") || resultList.getItem2().equals("")) {
                holder.layout1.setVisibility(View.GONE);
                holder.layout2.setVisibility(View.GONE);
            }
            else {
                holder.labelItem1.setVisibility(View.GONE);
                holder.item1.setVisibility(View.GONE);
            }
        }
        if(resultList.getLabelItem2().equals("") || resultList.getItem2().equals("")) {
            if(resultList.getLabelItem1().equals("") || resultList.getItem1().equals("")) {
                holder.layout1.setVisibility(View.GONE);
                holder.layout2.setVisibility(View.GONE);
            }
            else {
                holder.labelItem2.setVisibility(View.GONE);
                holder.item2.setVisibility(View.GONE);
            }
        }
        if(resultList.getLabelItem3().equals("") || resultList.getItem3().equals("")) {
            if(resultList.getLabelItem4().equals("") || resultList.getItem4().equals("")) {
                holder.layout3.setVisibility(View.GONE);
                holder.layout4.setVisibility(View.GONE);
            }
            else {
                holder.labelItem3.setVisibility(View.GONE);
                holder.item3.setVisibility(View.GONE);
            }
        }
        if(resultList.getLabelItem4().equals("") || resultList.getItem4().equals("")) {
            if(resultList.getLabelItem3().equals("") || resultList.getItem3().equals("")) {
                holder.layout3.setVisibility(View.GONE);
                holder.layout4.setVisibility(View.GONE);
            }
            else {
                holder.labelItem4.setVisibility(View.GONE);
                holder.item4.setVisibility(View.GONE);
            }
        }
        if(resultList.getLabelItem5().equals("") || resultList.getItem5().equals("")) {
            if(resultList.getLabelItem6().equals("") || resultList.getItem6().equals("")) {
                holder.layout5.setVisibility(View.GONE);
                holder.layout6.setVisibility(View.GONE);
            }
            else {
                holder.labelItem5.setVisibility(View.GONE);
                holder.item5.setVisibility(View.GONE);
            }
        }
        if(resultList.getLabelItem6().equals("") || resultList.getItem6().equals("")) {
            if(resultList.getLabelItem5().equals("") || resultList.getItem5().equals("")) {
                holder.layout5.setVisibility(View.GONE);
                holder.layout6.setVisibility(View.GONE);
            }
            else {
                holder.labelItem6.setVisibility(View.GONE);
                holder.item6.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return searchResultList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView labelItem1;
        private TextView item1;
        private TextView labelItem2;
        private TextView item2;
        private TextView labelItem3;
        private TextView item3;
        private TextView labelItem4;
        private TextView item4;
        private TextView labelItem5;
        private TextView item5;
        private TextView labelItem6;
        private TextView item6;
        private Button select;

        private LinearLayout layout1;
        private LinearLayout layout2;
        private LinearLayout layout3;
        private LinearLayout layout4;
        private LinearLayout layout5;
        private LinearLayout layout6;

        public MyViewHolder(View view) {
            super(view);
            labelItem1 = (TextView) view.findViewById(R.id.item1);
            item1 = (TextView) view.findViewById(R.id.input_item1);
            labelItem2 = (TextView) view.findViewById(R.id.item2);
            item2 = (TextView) view.findViewById(R.id.input_item2);
            labelItem3 = (TextView) view.findViewById(R.id.item3);
            item3 = (TextView) view.findViewById(R.id.input_item3);
            labelItem4 = (TextView) view.findViewById(R.id.item4);
            item4 = (TextView) view.findViewById(R.id.input_item4);
            labelItem5 = (TextView) view.findViewById(R.id.item5);
            item5 = (TextView) view.findViewById(R.id.input_item5);
            labelItem6 = (TextView) view.findViewById(R.id.item6);
            item6 = (TextView) view.findViewById(R.id.input_item6);
            select = (Button) view.findViewById(R.id.btn_select_search);

            layout1 = (LinearLayout) view.findViewById(R.id.layout1);
            layout2 = (LinearLayout) view.findViewById(R.id.layout2);
            layout3 = (LinearLayout) view.findViewById(R.id.layout3);
            layout4 = (LinearLayout) view.findViewById(R.id.layout4);
            layout5 = (LinearLayout) view.findViewById(R.id.layout5);
            layout6 = (LinearLayout) view.findViewById(R.id.layout6);

            select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Integer position = getAdapterPosition();
                    SearchResultList resultList = searchResultList.get(position);

                    Intent intent = new Intent();
                    intent.putExtra("ITEM_1", resultList.getItem1());
                    intent.putExtra("ITEM_2", resultList.getItem2());
                    intent.putExtra("ITEM_3", resultList.getItem3());
                    intent.putExtra("ITEM_4", resultList.getItem4());
                    intent.putExtra("ITEM_5", resultList.getItem5());
                    intent.putExtra("ITEM_6", resultList.getItem6());
                    activity.setResult(requestCode, intent);
                    activity.finish();
                }
            });
        }
    }

    public SearchResultAdapter(List<SearchResultList> searchResultList, Integer requestCode, Activity activity) {
        this.searchResultList = searchResultList;
        this.arraySearchResultList = new ArrayList<SearchResultList>();
        this.arraySearchResultList.addAll(searchResultList);
        this.activity = activity;
        this.requestCode = requestCode;
    }

    public void filter(String query) {
        query = query.toLowerCase(Locale.getDefault());
        searchResultList.clear();

        for(SearchResultList l : arraySearchResultList) {
            if(l.getItem1().toLowerCase(Locale.getDefault()).contains(query)) {
                searchResultList.add(l);
            }
            else if(l.getItem2().toLowerCase(Locale.getDefault()).contains(query)) {
                searchResultList.add(l);
            }
            else if(l.getItem3().toLowerCase(Locale.getDefault()).contains(query)) {
                searchResultList.add(l);
            }
            else if(l.getItem4().toLowerCase(Locale.getDefault()).contains(query)) {
                searchResultList.add(l);
            }
            else if(l.getItem5().toLowerCase(Locale.getDefault()).contains(query)) {
                searchResultList.add(l);
            }
            else if(l.getItem6().toLowerCase(Locale.getDefault()).contains(query)) {
                searchResultList.add(l);
            }
        }

        //SORT DEFAULT BY ORDER ID ASC
        /*Collections.sort(searchResultList, new Comparator<SearchResultList>() {
            @Override
            public int compare(SearchResultList x, SearchResultList y) {
                return x.getId().compareTo(y.getId());
            }
        });*/

        notifyDataSetChanged();
    }
}
