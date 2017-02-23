package com.muf.mymuf.mobilesurvey.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.muf.mymuf.mobilesurvey.R;
import com.muf.mymuf.mobilesurvey.adapter.TimeLineAdapter;
import com.muf.mymuf.mobilesurvey.list.TimeLineList;

import java.util.ArrayList;
import java.util.List;

public class MonitoringStatusActivity extends AppCompatActivity {

    private List<TimeLineList> resultList = new ArrayList<>();
    private RecyclerView recyclerView;
    private TimeLineAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoring_status);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Monitoring");
        getSupportActionBar().setSubtitle("01 Jan 2017 - 10 Jan 2017");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());

        resultList.add(new TimeLineList("PROSES SURVEY", 26));
        resultList.add(new TimeLineList("PROSES APPROVE", 5));
        resultList.add(new TimeLineList("REJECT", 9));
        resultList.add(new TimeLineList("PURCHASE ORDER (GO LIVE)", 5));
        resultList.add(new TimeLineList("GO LIVE", 12));

        mAdapter = new TimeLineAdapter(resultList);
        mAdapter.notifyDataSetChanged();

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }
}
