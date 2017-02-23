package com.muf.mymuf.mobilesurvey.fragment.aplikasi;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.muf.mymuf.mobilesurvey.R;

public class RefundFragment extends Fragment {
    public RefundFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_refund, container, false);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_container_refund, new RefundDariSupplierFragment());
        fragmentTransaction.commit();

        Button dariSupplier = (Button) rootView.findViewById(R.id.btn_dari_supplier);
        Button dariMUF = (Button) rootView.findViewById(R.id.btn_dari_muf);
        Button komisiChannel = (Button) rootView.findViewById(R.id.btn_komisi_channel);
        Button refundMobil = (Button) rootView.findViewById(R.id.btn_refund_mobil);

        dariSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_container_refund, new RefundDariSupplierFragment());
                fragmentTransaction.commit();
            }
        });

        dariMUF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_container_refund, new RefundDariMUFFragment());
                fragmentTransaction.commit();
            }
        });

        komisiChannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_container_refund, new RefundKomisiChannelFragment());
                fragmentTransaction.commit();
            }
        });

        refundMobil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_container_refund, new RefundMobilFragment());
                fragmentTransaction.commit();
            }
        });

        return rootView;
    }
}
