package com.muf.mymuf.mobilesurvey.fragment.aplikasi;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.muf.mymuf.mobilesurvey.R;

public class ObjekPembiayaanFragment extends Fragment {
    public ObjekPembiayaanFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_objek_pembiayaan, container, false);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_container_objek_pembiayaan, new ObjekPembiayaanDataAutomotiveFragment());
        fragmentTransaction.commit();

        Button dataAutomotive = (Button) rootView.findViewById(R.id.btn_data_automotive);
        Button infoSupplier = (Button) rootView.findViewById(R.id.btn_info_supplier);
        Button objekKaroseri = (Button) rootView.findViewById(R.id.btn_objek_karoseri);

        dataAutomotive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_container_objek_pembiayaan, new ObjekPembiayaanDataAutomotiveFragment());
                fragmentTransaction.commit();
            }
        });

        infoSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_container_objek_pembiayaan, new ObjekPembiayaanInformasiSupplierFragment());
                fragmentTransaction.commit();
            }
        });

        objekKaroseri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_container_objek_pembiayaan, new ObjekPembiayaanKaroseriFragment());
                fragmentTransaction.commit();
            }
        });

        return rootView;
    }
}
