package com.muf.mymuf.mobilesurvey.fragment.customer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.muf.mymuf.mobilesurvey.R;
import com.muf.mymuf.mobilesurvey.activity.FormInformasiAlamatActivity;

public class IdentitasFragment extends Fragment {

    private TextInputLayout containerPhone2;
    private TextInputLayout containerPhone3;

    private TextView addPhone;
    private TextView deletePhone;

    private EditText phone2;
    private EditText phone3;

    private Integer phoneCounter = 1;

    public IdentitasFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_identitas, container, false);

        Button btnNewAddress = (Button) rootView.findViewById(R.id.btn_add_address);
        btnNewAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FormInformasiAlamatActivity.class);
                startActivityForResult(intent, 9999);
            }
        });

        containerPhone2 = (TextInputLayout) rootView.findViewById(R.id.nama_no_hp_2);
        containerPhone3 = (TextInputLayout) rootView.findViewById(R.id.nama_no_hp_3);

        addPhone = (TextView) rootView.findViewById(R.id.input_tambah_no_hp);
        deletePhone = (TextView) rootView.findViewById(R.id.input_hapus_no_hp);

        phone2 = (EditText) rootView.findViewById(R.id.input_nama_no_hp_2);
        phone3 = (EditText) rootView.findViewById(R.id.input_nama_no_hp_3);

        addPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(phoneCounter == 1) {
                    phoneCounter++;
                    containerPhone2.setVisibility(View.VISIBLE);
                }
                else if(phoneCounter == 2) {
                    phoneCounter++;
                    containerPhone3.setVisibility(View.VISIBLE);
                    addPhone.setVisibility(View.GONE);
                    deletePhone.setVisibility(View.VISIBLE);
                }
            }
        });

        deletePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(phoneCounter == 3) {
                    phoneCounter--;
                    containerPhone3.setVisibility(View.GONE);
                    phone3.setText("");
                }
                else if(phoneCounter == 2) {
                    phoneCounter--;
                    containerPhone2.setVisibility(View.GONE);
                    phone2.setText("");
                    addPhone.setVisibility(View.VISIBLE);
                    deletePhone.setVisibility(View.GONE);
                }
            }
        });

        return rootView;
    }
}
