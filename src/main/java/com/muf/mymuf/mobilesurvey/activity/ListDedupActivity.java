package com.muf.mymuf.mobilesurvey.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.muf.mymuf.mobilesurvey.R;
import com.muf.mymuf.mobilesurvey.adapter.DedupAdapter;
import com.muf.mymuf.mobilesurvey.list.DedupList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListDedupActivity extends AppCompatActivity {

    private List<DedupList> resultList = new ArrayList<>();
    private RecyclerView recyclerView;
    private DedupAdapter mAdapter;

    private Toolbar toolbar;

    private Button btnInputData;
    private Button btnCancel;
    private EditText filter;
    private RelativeLayout searchContainer;
    private RelativeLayout numContainer;
    private RelativeLayout listContainer;

    private TextView num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_dedup);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Detection Duplication");
        getSupportActionBar().setSubtitle("PER-PERSONAL");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        final Integer insertedId = getIntent().getExtras().getInt("INSERTED_ID");

        btnInputData = (Button) findViewById(R.id.btn_ide);
        btnCancel = (Button) findViewById(R.id.btn_cancel);
        filter = (EditText) findViewById(R.id.filter);
        num = (TextView) findViewById(R.id.result_num);

        searchContainer = (RelativeLayout) findViewById(R.id.search_container);
        numContainer = (RelativeLayout) findViewById(R.id.result_num_container );
        listContainer = (RelativeLayout) findViewById(R.id.recycler_container);

        try {
            JSONObject jsonObject = new JSONObject(getIntent().getStringExtra("JSON_RESPONSE"));
            Integer row = jsonObject.getInt("DEDUP_RESULT_ROW");

            num.setText(String.valueOf(row));

            if(row > 0) {
                JSONArray jsonArray = new JSONArray(jsonObject.getString("case4"));
                for(int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jObj = jsonArray.getJSONObject(i);

                    String customerId = jObj.getString("CUST_NO");
                    String customerOid = jObj.getString("CUST_OID");
                    String tipeCustomer = jObj.getString("CUST_TYPE");
                    String namaLengkap = jObj.getString("CUST_NAME");
                    String noIdentitas = jObj.getString("CUST_ID_NO");
                    String alamatDomisili = jObj.getString("CUST_ADDRESS");
                    String status = jObj.getString("STATUS_NASABAH");
                    String rowID = jObj.getString("ROW_ID");
                    Integer statusCode = jObj.getInt("CUST_STATUS");

                    resultList.add(new DedupList(customerId, customerOid, tipeCustomer, namaLengkap,
                            noIdentitas, alamatDomisili, status, rowID, statusCode, insertedId));
                }

                if(jsonArray.length() == 1 && jsonArray.getJSONObject(0).getInt("CUST_STATUS") == 1) {
                    // DATA 1 DAN BLACK LIST
                    btnInputData.setVisibility(View.GONE);
                    numContainer.setVisibility(View.GONE);
                    searchContainer.setVisibility(View.GONE);
                    listContainer.setVisibility(View.GONE);
                    btnCancel.setText("EXIT");
                }

                mAdapter = new DedupAdapter(resultList, ListDedupActivity.this);

                mAdapter.notifyDataSetChanged();

                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(mAdapter);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = filter.getText().toString();
                mAdapter.filter(text);
                num.setText(String.valueOf(resultList.size()));
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListDedupActivity.this, ReasonToCancelActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
