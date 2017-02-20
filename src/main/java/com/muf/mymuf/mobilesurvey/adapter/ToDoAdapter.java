package com.muf.mymuf.mobilesurvey.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.muf.mymuf.mobilesurvey.R;
import com.muf.mymuf.mobilesurvey.list.ToDoList;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by 16003041 on 06/02/2017.
 */

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.MyViewHolder> {

    private List<ToDoList> toDoList;
    private ArrayList<ToDoList> arrayToDoList;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.to_do_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final ToDoList resultList = toDoList.get(position);
        holder.applicantNo.setText(resultList.getApplicantNo());
        holder.orderDate.setText(resultList.getOrderDate());
        holder.customerName.setText(resultList.getCustomerName());
        holder.process.setText(resultList.getProcess());
    }

    @Override
    public int getItemCount() {
        return toDoList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView applicantNo;
        private TextView orderDate;
        private TextView customerName;
        private TextView process;
        private Button edit;

        public MyViewHolder(View view) {
            super(view);
            applicantNo = (TextView) view.findViewById(R.id.txt_no_aplikasi);
            orderDate = (TextView) view.findViewById(R.id.txt_tgl_order);
            customerName = (TextView) view.findViewById(R.id.txt_nama_customer);
            process = (TextView) view.findViewById(R.id.txt_proses);
            edit = (Button) view.findViewById(R.id.btn_edit);

            edit.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Integer position = getAdapterPosition();
            ToDoList resultList = toDoList.get(position);

            /*Intent i = new Intent(view.getContext(), CustomerDetailActivity.class);
            i.putExtra("APPLICATION_ORDER_ID", resultList.getApplicantOrderId());
            i.putExtra("APPLICATION_NO", resultList.getApplicantNo());
            view.getContext().startActivity(i);*/
        }
    }

    public ToDoAdapter(List<ToDoList> searchResultList) {
        this.toDoList = searchResultList;
        this.arrayToDoList = new ArrayList<ToDoList>();
        this.arrayToDoList.addAll(searchResultList);
    }

    public void filter(String query) {
        query = query.toLowerCase(Locale.getDefault());
        toDoList.clear();
        for(ToDoList l : arrayToDoList) {
            if(l.getApplicantNo().toLowerCase(Locale.getDefault()).contains(query)) {
                toDoList.add(l);
            }
            else if(l.getOrderDate().toLowerCase(Locale.getDefault()).contains(query)) {
                toDoList.add(l);
            }
            else if(l.getCustomerName().toLowerCase(Locale.getDefault()).contains(query)) {
                toDoList.add(l);
            }
            else if(l.getProcess().toLowerCase(Locale.getDefault()).contains(query)) {
                toDoList.add(l);
            }
        }

        notifyDataSetChanged();
    }
}
