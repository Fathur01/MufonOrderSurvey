package com.muf.mymuf.mobilesurvey.fragment.aplikasi;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.muf.mymuf.mobilesurvey.R;

public class InformasiBiayaFragment extends Fragment {
    public InformasiBiayaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_informasi_biaya, container, false);
    }
}
