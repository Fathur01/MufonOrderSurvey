package com.muf.mymuf.mobilesurvey.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.muf.mymuf.mobilesurvey.R;
import com.muf.mymuf.mobilesurvey.util.Configs;
import com.muf.mymuf.mobilesurvey.util.Dialogs;
import com.muf.mymuf.mobilesurvey.util.SessionManager;

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

public class LoginActivity extends AppCompatActivity {

    private EditText nikText;
    private EditText passwordText;
    private Button btnLogin;

    private Handler mHandler;

    private ProgressDialog progressDialog;

    private final String URL_GET_USER_PROFILE = Configs.URL_SERVICE + "m=p&srv=SRVIAM&rt=getDetailUserProfile";

    private String APPLICATION_CODE = "AAMA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        nikText = (EditText) findViewById(R.id.input_nik);
        passwordText = (EditText) findViewById(R.id.input_password);

        btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    @Override
    protected void onResume() {
        super.onResume();

        SessionManager session = new SessionManager(getApplicationContext());
        if(session.isLoggedIn()) {
            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
            startActivity(intent);
            finish();
            //overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        }
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

    public void login() {
        if (!validate()) {
            onLoginFailed("");
            return;
        }

        progressDialog = new ProgressDialog(LoginActivity.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        String nik = nikText.getText().toString();
        String password = passwordText.getText().toString();

        mHandler = new Handler(Looper.getMainLooper());

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        Map<String, String> params_LOGIN = new HashMap<String, String>();
        params_LOGIN.put("nik", nik);
        params_LOGIN.put("application", APPLICATION_CODE);

        JSONObject parameter = new JSONObject(params_LOGIN);
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON, parameter.toString());
        Request request = new Request.Builder()
                .url(URL_GET_USER_PROFILE)
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
                Dialogs.showDialog(mHandler, LoginActivity.this, "Info", msg, false);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                Log.d("debug_response", result);

                if(response.code() == 200) {
                    try {
                        JSONObject json = new JSONObject(result);

                        JSONObject jObj_userProfile = json.getJSONObject("resultUserProfileHeader");
                        final String nik = jObj_userProfile.getString("nik");
                        final String fullname = jObj_userProfile.getString("fullname");

                        JSONObject jObj_userLocation = json.getJSONArray("resultUserProfileLocation").getJSONObject(0);
                        final String branch_code = jObj_userLocation.getString("branch_code");

                        if(nik == null || fullname.equals("USER TIDAK TERDAFTAR")) {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    onLoginFailed("User Tidak Terdaftar");
                                    progressDialog.dismiss();
                                }
                            });
                        }
                        else {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    SessionManager session = new SessionManager(getApplicationContext());
                                    session.createLoginSession(nik, fullname, branch_code);
                                    onLoginSuccess();
                                    progressDialog.dismiss();
                                }
                            });
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    if(progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Dialogs.showDialog(mHandler, LoginActivity.this, "Info", "Silahkan Coba Lagi", false);
                }
            }
        });
    }

    public void onLoginSuccess() {
        btnLogin.setEnabled(true);

        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
        startActivity(intent);
        finish();
        //overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public void onLoginFailed(String message) {
        mHandler = new Handler(Looper.getMainLooper());

        Dialogs.showDialog(mHandler, LoginActivity.this, "Warning", "Login Gagal\n" + message, false);

        btnLogin.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String nik = nikText.getText().toString();
        String password = passwordText.getText().toString();

        if (nik.isEmpty()) {
            nikText.setError("NIK harus diisi");
            valid = false;
        } else {
            nikText.setError(null);
        }

        if (password.isEmpty()) {
            passwordText.setError("Password harus diisi");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        return valid;
    }
}
