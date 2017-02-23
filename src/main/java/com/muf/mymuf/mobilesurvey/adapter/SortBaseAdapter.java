package com.muf.mymuf.mobilesurvey.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.muf.mymuf.mobilesurvey.R;
import com.muf.mymuf.mobilesurvey.activity.ListToDoListActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 16003041 on 22/02/2017.
 */

public class SortBaseAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List<String> list = new ArrayList<>();

    private ListToDoListActivity listToDoListActivity;

    public SortBaseAdapter(Context context, List<String> list, ListToDoListActivity listToDoListActivity) {
        this.layoutInflater = LayoutInflater.from(context);
        this.list = list;
        this.listToDoListActivity = listToDoListActivity;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View view = convertView;
        final Integer i = position;

        if (view == null) {
            view = layoutInflater.inflate(R.layout.sort_list_row, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) view.findViewById(R.id.txt_sort_item);
            viewHolder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listToDoListActivity.getSortDialogResult(list.get(i));
                }
            });
            view.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.textView.setText(list.get(position));

        return view;
    }

    static class ViewHolder {
        TextView textView;
    }
}
