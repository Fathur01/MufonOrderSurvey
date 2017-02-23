package com.muf.mymuf.mobilesurvey.fragment.customer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.muf.mymuf.mobilesurvey.R;

public class PekerjaanFragment extends Fragment {

    private TextView seekbarMin;
    private TextView seekbarMax;

    private String seekbarValue;

    public PekerjaanFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_pekerjaan, container, false);

        seekbarMin = (TextView) rootView.findViewById(R.id.seekbar_min);
        seekbarMax = (TextView) rootView.findViewById(R.id.seekbar_max);

        SeekBar seekBar = (SeekBar) rootView.findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                seekbarValue = "";
                switch (i) {
                    case 0 :
                        seekbarValue = "0 Bulan";
                        break;
                    case 1 :
                        seekbarValue = "6 Bulan";
                        break;
                    case 2 :
                        seekbarValue = "1 Tahun";
                        break;
                    case 3 :
                        seekbarValue = "2 Tahun";
                        break;
                    case 4 :
                        seekbarValue = "3 Tahun";
                        break;
                    case 5 :
                        seekbarValue = "4 Tahun";
                        break;
                    case 6 :
                        seekbarValue = "5 Tahun";
                        break;
                    case 7 :
                        seekbarValue = "> 5 Tahun";
                        break;
                }
                seekbarMin.setText(seekbarValue);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        return rootView;
    }
}
