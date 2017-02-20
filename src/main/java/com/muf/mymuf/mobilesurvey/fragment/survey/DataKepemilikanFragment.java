package com.muf.mymuf.mobilesurvey.fragment.survey;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.muf.mymuf.mobilesurvey.R;
import com.muf.mymuf.mobilesurvey.fragment.aplikasi.InformasiAplikasiFragment;
import com.muf.mymuf.mobilesurvey.fragment.aplikasi.InformasiExternalSalesFragment;
import com.muf.mymuf.mobilesurvey.fragment.aplikasi.InformasiInternalSalesFragment;

public class DataKepemilikanFragment extends Fragment {
    public DataKepemilikanFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_data_kepemilikan, container, false);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_data_kepemilikan_container, new KepemilikanKendaraanFragment());
        fragmentTransaction.commit();

        Button kepemilikanKendaraan = (Button) rootView.findViewById(R.id.btn_kepemilikan_kendaraan);
        Button detailRumah = (Button) rootView.findViewById(R.id.btn_detail_rumah);
        Button areaCoverage = (Button) rootView.findViewById(R.id.btn_area_coverage);

        kepemilikanKendaraan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_data_kepemilikan_container, new KepemilikanKendaraanFragment());
                fragmentTransaction.commit();
            }
        });

        detailRumah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_data_kepemilikan_container, new DetailRumahFragment());
                fragmentTransaction.commit();
            }
        });

        areaCoverage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_data_kepemilikan_container, new AreaCoverageFragment());
                fragmentTransaction.commit();
            }
        });

        return rootView;
    }
}
