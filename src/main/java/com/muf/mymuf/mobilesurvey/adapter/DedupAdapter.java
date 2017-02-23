package com.muf.mymuf.mobilesurvey.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.muf.mymuf.mobilesurvey.activity.CustomerDetailActivity;
import com.muf.mymuf.mobilesurvey.R;
import com.muf.mymuf.mobilesurvey.list.DedupList;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by 16003041 on 03/02/2017.
 */

public class DedupAdapter extends RecyclerView.Adapter<DedupAdapter.MyViewHolder> {

    private List<DedupList> dedupList;
    private ArrayList<DedupList> arrayDedupList;
    private Activity activity;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dedup_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final DedupList resultList = dedupList.get(position);
        holder.customerId.setText(resultList.getCustomerId());
        holder.customerOid.setText(resultList.getCustomerId());
        holder.tipeCustomer.setText(resultList.getTipeCustomer());
        holder.namaLengkap.setText(resultList.getNamaLengkap());
        holder.noIdentitas.setText(resultList.getNoIdentitas());
        holder.alamatDomisili.setText(resultList.getAlamatDomisili());
        holder.status.setText(resultList.getStatus());

        if(resultList.getStatus().equals("BLACK LIST")) {
            holder.status.setTextColor(Color.RED);
        }
        else if (resultList.getStatus().equals("TIDAK BLACK LIST")) {
            holder.status.setTextColor(Color.GREEN);
        }
    }

    @Override
    public int getItemCount() {
        return dedupList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView customerId;
        private TextView customerOid;
        private TextView tipeCustomer;
        private TextView namaLengkap;
        private TextView noIdentitas;
        private TextView alamatDomisili;
        private TextView status;
        private Button info;

        public MyViewHolder(View view) {
            super(view);
            customerId = (TextView) view.findViewById(R.id.txt_customer_id);
            customerOid = (TextView) view.findViewById(R.id.txt_customer_oid);
            tipeCustomer = (TextView) view.findViewById(R.id.txt_tipe_customer);
            namaLengkap = (TextView) view.findViewById(R.id.txt_nama);
            noIdentitas = (TextView) view.findViewById(R.id.txt_no_identitas);
            alamatDomisili = (TextView) view.findViewById(R.id.txt_alamat);
            status = (TextView) view.findViewById(R.id.txt_status);
            info = (Button) view.findViewById(R.id.btn_info);

            info.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Integer position = getAdapterPosition();
            DedupList resultList = dedupList.get(position);

            Intent intent = new Intent(view.getContext(), CustomerDetailActivity.class);
            intent.putExtra("ROW_ID", resultList.getRowID());
            intent.putExtra("STATUS_CODE", resultList.getStatusCode());
            intent.putExtra("INSERTED_ID", resultList.getInsertedId());
            view.getContext().startActivity(intent);
            activity.finish();
        }
    }

    public DedupAdapter(List<DedupList> searchResultList, Activity activity) {
        this.dedupList = searchResultList;
        this.arrayDedupList = new ArrayList<DedupList>();
        this.arrayDedupList.addAll(searchResultList);
        this.activity = activity;
    }

    public void filter(String query) {
        query = query.toLowerCase(Locale.getDefault());
        dedupList.clear();

        for(DedupList l : arrayDedupList) {
            if(l.getCustomerId().toLowerCase(Locale.getDefault()).contains(query)) {
                dedupList.add(l);
            }
            else if(l.getCustomerOid().toLowerCase(Locale.getDefault()).contains(query)) {
                dedupList.add(l);
            }
            else if(l.getTipeCustomer().toLowerCase(Locale.getDefault()).contains(query)) {
                dedupList.add(l);
            }
            else if(l.getNamaLengkap().toLowerCase(Locale.getDefault()).contains(query)) {
                dedupList.add(l);
            }
            else if(l.getNoIdentitas().toLowerCase(Locale.getDefault()).contains(query)) {
                dedupList.add(l);
            }
            else if(l.getAlamatDomisili().toLowerCase(Locale.getDefault()).contains(query)) {
                dedupList.add(l);
            }
            else if(l.getStatus().toLowerCase(Locale.getDefault()).contains(query)) {
                dedupList.add(l);
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
