package com.muf.mymuf.mobilesurvey.fragment.aplikasi;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.muf.mymuf.mobilesurvey.R;

public class StrukturKreditMainFragment extends Fragment {
    public StrukturKreditMainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_struktur_kredit_main, container, false);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, new StrukturKreditFragment());
        fragmentTransaction.commit();

        Button strukturKredit = (Button) rootView.findViewById(R.id.btn_struktur_kredit);
        Button infromasiBiaya = (Button) rootView.findViewById(R.id.btn_informasi_biaya);
        Button asuransiKerugian = (Button) rootView.findViewById(R.id.btn_asuransi_kerugian);
        Button perluasanJaminan = (Button) rootView.findViewById(R.id.btn_perluasan_jaminan);
        Button asuransiTambahan = (Button) rootView.findViewById(R.id.btn_asuransi_tambahan);

        strukturKredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_container, new StrukturKreditFragment());
                fragmentTransaction.commit();
            }
        });

        infromasiBiaya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_container, new InformasiBiayaFragment());
                fragmentTransaction.commit();
            }
        });

        asuransiKerugian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_container, new AsuransiKerugianFragment());
                fragmentTransaction.commit();
            }
        });

        perluasanJaminan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_container, new PerluasanJaminanFragment());
                fragmentTransaction.commit();
            }
        });

        asuransiTambahan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_container, new AsuransiTambahanFragment());
                fragmentTransaction.commit();
            }
        });

        return rootView;
    }
}
