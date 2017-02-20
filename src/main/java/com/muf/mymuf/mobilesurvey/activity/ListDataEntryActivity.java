package com.muf.mymuf.mobilesurvey.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.muf.mymuf.mobilesurvey.R;
import com.muf.mymuf.mobilesurvey.adapter.ToDoAdapter;
import com.muf.mymuf.mobilesurvey.list.ToDoList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListDataEntryActivity extends AppCompatActivity {

    private List<ToDoList> resultList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ToDoAdapter mAdapter;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_data_entry);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("To Do List Data Entry");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        TextView resultNum = (TextView) findViewById(R.id.result_num);

        try {
            JSONArray jsonArray = new JSONArray(getIntent().getStringExtra("JSON_RESPONSE"));

            resultNum.setText(String.valueOf(jsonArray.length()));

            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject jObj = jsonArray.getJSONObject(i);

                String applicantOrderId = jObj.getString("applicant_order_id");
                String applicantNo = jObj.getString("applicant_no");
                String orderDate = jObj.getString("order_date");
                String customerName = jObj.getString("cus_name");
                String process = jObj.getString("cur_process");

                resultList.add(new ToDoList(applicantOrderId, applicantNo, orderDate, customerName, process));
            }

            mAdapter = new ToDoAdapter(resultList);

            mAdapter.notifyDataSetChanged();

            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        final EditText filter = (EditText) findViewById(R.id.filter);
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
            }
        });
    }
}
