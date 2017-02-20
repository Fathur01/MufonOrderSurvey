package com.muf.mymuf.mobilesurvey.activity;

import android.app.ProgressDialog;
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
import com.muf.mymuf.mobilesurvey.adapter.SearchResultAdapter;
import com.muf.mymuf.mobilesurvey.list.SearchResultList;

import java.util.ArrayList;
import java.util.List;

public class SearchResultActivity extends AppCompatActivity {

    private List<SearchResultList> resultList = new ArrayList<>();
    private RecyclerView recyclerView;
    private SearchResultAdapter mAdapter;

    private Toolbar toolbar;

    private TextView resultNum;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String searchKey = getIntent().getExtras().getString("SEARCH_KEY");
        resultList = getIntent().getParcelableArrayListExtra("SEARCH_DATA");
        Integer requestCode = getIntent().getExtras().getInt("SEARCH_REQUEST_CODE");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Cari " + searchKey);

        final EditText edtxtSearch = (EditText) findViewById(R.id.filter);
        edtxtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = edtxtSearch.getText().toString();
                mAdapter.filter(text);
                resultNum.setText(String.valueOf(resultList.size()));
            }
        });

        resultNum = (TextView) findViewById(R.id.result_num);
        resultNum.setText(String.valueOf(resultList.size()));

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        mAdapter = new SearchResultAdapter(resultList, requestCode, SearchResultActivity.this);

        mAdapter.notifyDataSetChanged();

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
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
}
