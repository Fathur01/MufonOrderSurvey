package com.muf.mymuf.mobilesurvey.fragment.aplikasi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.muf.mymuf.mobilesurvey.R;
import com.muf.mymuf.mobilesurvey.activity.FormInformasiExternalSalesActivity;
import com.muf.mymuf.mobilesurvey.activity.FormInformasiInternalSalesActivity;

public class InformasiInternalSalesFragment extends Fragment {

    private RelativeLayout notEmptyDataLayout;
    private RelativeLayout emptyDataLayout;

    public InformasiInternalSalesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_informasi_internal_sales, container, false);

        notEmptyDataLayout = (RelativeLayout) rootView.findViewById(R.id.data_not_empty_container);
        emptyDataLayout = (RelativeLayout) rootView.findViewById(R.id.data_empty_container);

        // ---------- SIMULASI ---------- //
        Integer data = 0;
        if(data > 0) {
            notEmptyDataLayout.setVisibility(View.VISIBLE);
            emptyDataLayout.setVisibility(View.GONE);
        }
        else {
            notEmptyDataLayout.setVisibility(View.GONE);
            emptyDataLayout.setVisibility(View.VISIBLE);
        }
        // ---------- SIMULASI ---------- //

        FloatingActionButton floatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FormInformasiInternalSalesActivity.class);
                startActivity(intent);
            }
        });

        Button addData = (Button) rootView.findViewById(R.id.btn_add_data);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FormInformasiInternalSalesActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == 1) && (resultCode == Activity.RESULT_OK)) {
            notEmptyDataLayout.setVisibility(View.VISIBLE);
            emptyDataLayout.setVisibility(View.GONE);
        }
    }
}
