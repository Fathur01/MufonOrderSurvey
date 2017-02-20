package com.muf.mymuf.mobilesurvey.fragment.aplikasi;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.muf.mymuf.mobilesurvey.R;

public class AplikasiFragment extends Fragment {

    public AplikasiFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_aplikasi, container, false);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, new InformasiAplikasiFragment());
        fragmentTransaction.commit();

        Button infromasiApp = (Button) rootView.findViewById(R.id.btn_informasi_aplikasi);
        Button infromasiExt = (Button) rootView.findViewById(R.id.btn_informasi_ext);
        Button infromasiInt = (Button) rootView.findViewById(R.id.btn_informasi_int);

        infromasiApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_container, new InformasiAplikasiFragment());
                fragmentTransaction.commit();
            }
        });

        infromasiExt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_container, new InformasiExternalSalesFragment());
                fragmentTransaction.commit();
            }
        });

        infromasiInt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_container, new InformasiInternalSalesFragment());
                fragmentTransaction.commit();
            }
        });

        return rootView;
    }
}
