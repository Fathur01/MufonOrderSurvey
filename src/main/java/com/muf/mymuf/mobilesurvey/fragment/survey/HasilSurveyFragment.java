package com.muf.mymuf.mobilesurvey.fragment.survey;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.github.gcacace.signaturepad.views.SignaturePad;
import com.muf.mymuf.mobilesurvey.R;

public class HasilSurveyFragment extends Fragment {
    public HasilSurveyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_hasil_survey, container, false);

        final SignaturePad signaturePad = (SignaturePad) rootView.findViewById(R.id.signature_pad);

        ImageButton clearSignature = (ImageButton) rootView.findViewById(R.id.btn_clear_signature);
        clearSignature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signaturePad.clear();
            }
        });

        return rootView;
    }
}
