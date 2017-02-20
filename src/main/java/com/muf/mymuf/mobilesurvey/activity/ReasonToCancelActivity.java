package com.muf.mymuf.mobilesurvey.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.muf.mymuf.mobilesurvey.R;
import com.muf.mymuf.mobilesurvey.util.Configs;
import com.muf.mymuf.mobilesurvey.util.Dialogs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ReasonToCancelActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private Handler mHandler;
    private ProgressDialog progressDialog;

    private final String URL_GET_REASON = Configs.URL_SERVICE + "m=p&srv=SRVAAM&rt=listParamSubResultByResult";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reason_to_cancel);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Reason to Cancel");

        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        getReason(this, radioGroup);

        Button selectBtn = (Button) findViewById(R.id.btn_select);
        selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int radioButtonID = radioGroup.getCheckedRadioButtonId();
                View radioButton = radioGroup.findViewById(radioButtonID);
                int idx = radioGroup.indexOfChild(radioButton);

                if(idx == -1) {
                    Dialogs.showDialog(mHandler, ReasonToCancelActivity.this, "Warning", "Pilih Alasan Terlebih Dahulu", false);
                }
                else {
                    RadioButton r = (RadioButton) radioGroup.getChildAt(idx);
                    String selectedText = r.getText().toString();
                    String selectedId = "M0" + String.valueOf(r.getId());
                    Toast.makeText(ReasonToCancelActivity.this, selectedId, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onPause() {
        if(progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        super.onPause();
    }

    @Override
    public void onDestroy() {
        if(progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        super.onDestroy();
    }

    public void getReason(final Activity activity, final RadioGroup rGroup) {

        progressDialog = new ProgressDialog(ReasonToCancelActivity.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        mHandler = new Handler(Looper.getMainLooper());

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        Map<String, String> params = new HashMap<String, String>();
        params.put("appr_code", "REJC");
        params.put("appr_result_code", "M");

        JSONObject parameter = new JSONObject(params);
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON, parameter.toString());
        Request request = new Request.Builder()
                .url(URL_GET_REASON)
                .post(body)
                .addHeader("content-type", "application/json; charset=utf-8")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("debug_failure", e.getMessage());

                final String msg = e.getMessage();
                if(progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Dialogs.showDialog(mHandler, ReasonToCancelActivity.this, "Info", msg, false);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                Log.d("debug_response", result);

                if(response.code() == 200) {
                    try {
                        JSONArray jsonArray = new JSONArray(result);
                        for(int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            String reasonCode = jsonObject.getString("appr_subresult_code");
                            String reasonDesc = jsonObject.getString("appr_subresult_desc");

                            final RadioButton radioButton = new RadioButton(activity);
                            radioButton.setId(i + 1);
                            radioButton.setText(reasonDesc);

                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    rGroup.addView(radioButton);
                                }
                            });
                        }

                        if(progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    if(progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Dialogs.showDialog(mHandler, ReasonToCancelActivity.this, "Info", "Silahkan Coba Lagi", false);
                }
            }
        });
    }
}
