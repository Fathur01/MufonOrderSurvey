package com.muf.mymuf.mobilesurvey.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.muf.mymuf.mobilesurvey.R;
import com.muf.mymuf.mobilesurvey.adapter.CustDtlAdapter;
import com.muf.mymuf.mobilesurvey.list.CustDtlList;
import com.muf.mymuf.mobilesurvey.util.Configs;
import com.muf.mymuf.mobilesurvey.util.DatabaseHelper;
import com.muf.mymuf.mobilesurvey.util.Dialogs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CustomerDetailActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private TextView noKTP;
    private TextView namaLengkap;
    private TextView custType;
    private TextView address;
    private TextView tglLahir;
    private TextView tmptLahir;
    private TextView namaPasangan;
    private TextView namaIbu;

    ProgressDialog progressDialog;
    private Handler mHandler;

    private List<CustDtlList> resultList = new ArrayList<>();
    private RecyclerView recyclerView;
    private CustDtlAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private final String URL_GET_DEDUP_HEADER = Configs.URL_SERVICE + "m=p&srv=SRVAAM&rt=detailDedupHdr";
    private final String URL_GET_DEDUP_DETAIL = Configs.URL_SERVICE + "m=p&srv=SRVAAM&rt=detailDedupDtl";
    private final String URL_GET_ORDER_ID = Configs.URL_SERVICE_2 + "get_order_id.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_detail);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Detail Customer Perorangan");

        String rowID = getIntent().getStringExtra("ROW_ID");
        final Integer statusCode = getIntent().getExtras().getInt("STATUS_CODE");
        final Integer insertedId = getIntent().getExtras().getInt("INSERTED_ID");

        mHandler = new Handler(Looper.getMainLooper());

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());

        noKTP = (TextView) findViewById(R.id.txt_ktp);
        namaLengkap = (TextView) findViewById(R.id.txt_nama);
        custType = (TextView) findViewById(R.id.txt_cust_type);
        address = (TextView) findViewById(R.id.txt_address);
        tglLahir = (TextView) findViewById(R.id.txt_tgl_lahir);
        tmptLahir = (TextView) findViewById(R.id.txt_tempat_lahir);
        namaPasangan = (TextView) findViewById(R.id.txt_nama_pasangan);
        namaIbu = (TextView) findViewById(R.id.txt_nama_ibu);

        Button btnSelect = (Button) findViewById(R.id.btn_select);
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("INSERTED_ID", String.valueOf(insertedId));

                if(statusCode == 1) {
                    Dialogs.showDialog(mHandler, CustomerDetailActivity.this, "Warning", "Status Nasabah yang dipilih BLACK LIST", false);
                }
                else {
                    getOrderId(String.valueOf(insertedId));
                }
            }
        });

        Button btnCancel = (Button) findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerDetailActivity.this, ReasonToCancelActivity.class);
                startActivity(intent);
                finish();
            }
        });

        getDedupHeader(rowID);
    }

    @Override
    protected void onPause() {
        if(progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if(progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        super.onDestroy();
    }

    /**
     *
     * get data detail dedup dari Scala
     *
     **/
    private void getDedupHeader(final String rowID) {

        progressDialog = new ProgressDialog(CustomerDetailActivity.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        Map<String, String> params = new HashMap<>();
        params.put("P_ROWID", rowID);

        final JSONObject jsonObject = new JSONObject(params);
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request = new Request.Builder()
                .url(URL_GET_DEDUP_HEADER)
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
                Dialogs.showDialog(mHandler, CustomerDetailActivity.this, "Info", msg, false);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                Log.d("debug_response", result);

                if(response.code() == 200) {
                    try {
                        JSONArray jsonArray = new JSONArray(result);
                        JSONObject jObj = jsonArray.getJSONObject(0);

                        final String idNo = jObj.getString("CUST_ID_NO");
                        final String custName = jObj.getString("CUST_NAME");
                        final String cust_type = jObj.getString("CUST_TYPE");
                        final String custAddress = jObj.getString("CUST_ADDRESS");
                        final String custDate = jObj.getString("CUST_DATE");
                        final String custBirthPlace = jObj.getString("CUST_BIRTH_PLACE");
                        final String custSpouseName = jObj.getString("CUST_SPOUSE_NAME");
                        final String custMotherName = jObj.getString("CUST_MOTHER_NAME");

                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                noKTP.setText(idNo);
                                namaLengkap.setText(custName);
                                custType.setText(cust_type);
                                address.setText(custAddress);
                                tglLahir.setText(custDate);
                                tmptLahir.setText(custBirthPlace);
                                namaPasangan.setText(custSpouseName);
                                namaIbu.setText(custMotherName);
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    getDedupDetail(rowID);
                }
                else {
                    if(progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Dialogs.showDialog(mHandler, CustomerDetailActivity.this, "Info", "Silahkan Coba Lagi", false);
                }
            }
        });
    }

    /**
     *
     * get data detail dedup dari Scala
     *
     **/
    private void getDedupDetail(String rowID) {

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        Map<String, String> params = new HashMap<>();
        params.put("P_ROWID", rowID);

        JSONObject jsonObject = new JSONObject(params);
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request = new Request.Builder()
                .url(URL_GET_DEDUP_DETAIL)
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
                Dialogs.showDialog(mHandler, CustomerDetailActivity.this, "Info", msg, false);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                Log.d("debug_response", result);

                if(response.code() == 200) {
                    try {
                        JSONArray jsonArray = new JSONArray(result);

                        for(int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jObj = jsonArray.getJSONObject(i);

                            String cabang = jObj.getString("APPL_BR_ID");
                            String noKontrak = jObj.getString("APPL_CONTRACT_NO");
                            String noAplikasi = jObj.getString("APPL_NO");
                            String statusAplikasi = jObj.getString("STATUS_DESC");

                            resultList.add(new CustDtlList(cabang, noKontrak, noAplikasi, statusAplikasi));
                        }

                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter = new CustDtlAdapter(resultList);

                                mAdapter.notifyDataSetChanged();

                                recyclerView.setLayoutManager(mLayoutManager);
                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                                recyclerView.setAdapter(mAdapter);

                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    if(progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }
                else {
                    if(progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Dialogs.showDialog(mHandler, CustomerDetailActivity.this, "Info", "Silahkan Coba Lagi", false);
                }
            }
        });
    }

    /**
     *
     * get sequence Order Id & update kolom orderId pada db MySQL
     *
     **/
    public void getOrderId(final String insertedId) {

        progressDialog = new ProgressDialog(CustomerDetailActivity.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        Map<String, String> params = new HashMap<>();
        params.put("inserted_id", insertedId);

        JSONObject jsonObject = new JSONObject(params);
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request = new Request.Builder()
                .url(URL_GET_ORDER_ID)
                .post(body)
                .addHeader("content-type", "application/json; charset=utf-8")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("debug_order_failure", e.getMessage());

                final String msg = e.getMessage();
                if(progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Dialogs.showDialog(mHandler, CustomerDetailActivity.this, "Info", msg, false);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();

                    final String result = response.body().string();
                    Log.d("debug_order_response", result);

                    if(response.code() == 200) {
                        try {
                            JSONObject json = new JSONObject(result);
                            Integer success = json.getInt("success");
                            String message = json.getString("message");

                            Log.d("debug_order_response", String.valueOf(success) + " - " + message);

                            if(success != 0) {
                                Integer orderId = json.getInt("order_id");

                                updateMktOrder(Integer.parseInt(insertedId), orderId);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    /**
     *
     * update kolom orderId pada db SQLite
     *
     **/
    public void updateMktOrder(Integer insertedId, Integer orderId) {
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        db.updateOrderIdMktOrder(insertedId, orderId);

        Intent intent = new Intent(CustomerDetailActivity.this, DataEntryActivity.class);
        intent.putExtra("ORDER_ID", orderId);
        startActivity(intent);
    }
}
