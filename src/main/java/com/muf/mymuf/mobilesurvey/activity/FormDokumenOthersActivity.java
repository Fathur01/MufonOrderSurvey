package com.muf.mymuf.mobilesurvey.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.muf.mymuf.mobilesurvey.R;
import com.muf.mymuf.mobilesurvey.fragment.dokumen.DokumenFragment;
import com.muf.mymuf.mobilesurvey.util.Dialogs;
import com.mvc.imagepicker.ImagePicker;

public class FormDokumenOthersActivity extends AppCompatActivity {

    private Handler mHandler;
    private Toolbar toolbar;

    private EditText name;
    private Button upload;

    private String documentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_dokumen_others);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Upload Dokumen");

        mHandler = new Handler(Looper.getMainLooper());

        name = (EditText) findViewById(R.id.input_nama_dokumen) ;
        upload = (Button) findViewById(R.id.btn_upload_others);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int permissionCheck = ContextCompat.checkSelfPermission(FormDokumenOthersActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

                if(permissionCheck == PackageManager.PERMISSION_GRANTED) {
                    ImagePicker.setMinQuality(600, 600);
                    ImagePicker.pickImage(FormDokumenOthersActivity.this, "Select Image From :");

                    documentName = name.getText().toString();
                }
                else {
                    Dialogs.showDialog(mHandler, FormDokumenOthersActivity.this, "Warning", "Permission Needed !", false);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("debug_OTHERS_REQ", "" + requestCode);
        Log.d("debug_OTHERS_RES", "" + resultCode);
        if(resultCode != 0) {
            // TODO UPLOAD DOCUMENT OTHER

            Intent intent = new Intent();
            intent.putExtra("DOCUMENT_OTHER_NAME", documentName);
            setResult(9999, intent);
            finish();
        }
    }
}
