package com.muf.mymuf.mobilesurvey.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.muf.mymuf.mobilesurvey.R;
import com.muf.mymuf.mobilesurvey.list.CustDtlList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 16003041 on 03/02/2017.
 */

public class CustDtlAdapter extends RecyclerView.Adapter<CustDtlAdapter.MyViewHolder> {

    private List<CustDtlList> custDtlList;
    private ArrayList<CustDtlList> arrayCustDtlList;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cust_dtl_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CustDtlAdapter.MyViewHolder holder, int position) {
        CustDtlList resultList = custDtlList.get(position);
        holder.branch.setText(resultList.getBranch());
        holder.contractNo.setText(resultList.getContractNo());
        holder.appNo.setText(resultList.getAppNo());
        holder.appStatus.setText(resultList.getAppStatus());
    }

    @Override
    public int getItemCount() {
        return custDtlList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView branch;
        private TextView contractNo;
        private TextView appNo;
        private TextView appStatus;

        public MyViewHolder(View view) {
            super(view);
            branch = (TextView) view.findViewById(R.id.txt_cabang);
            contractNo = (TextView) view.findViewById(R.id.txt_no_kontrak);
            appNo = (TextView) view.findViewById(R.id.txt_no_aplikasi);
            appStatus = (TextView) view.findViewById(R.id.txt_status_aplikasi);
        }
    }

    public CustDtlAdapter(List<CustDtlList> searchResultList) {
        this.custDtlList = searchResultList;
        this.arrayCustDtlList = new ArrayList<CustDtlList>();
        this.arrayCustDtlList.addAll(searchResultList);
    }
}
