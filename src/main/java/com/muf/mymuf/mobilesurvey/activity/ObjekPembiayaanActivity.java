package com.muf.mymuf.mobilesurvey.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.muf.mymuf.mobilesurvey.R;
import com.muf.mymuf.mobilesurvey.fragment.aplikasi.ObjekPembiayaanFragment;
import com.muf.mymuf.mobilesurvey.fragment.aplikasi.RefundFragment;
import com.muf.mymuf.mobilesurvey.fragment.aplikasi.StrukturKreditMainFragment;
import com.muf.mymuf.mobilesurvey.util.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class ObjekPembiayaanActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objek_pembiayaan);

        toolbar = (Toolbar) findViewById(R.id.toolbar_nav);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Objek Pembiayaan");
        getSupportActionBar().setSubtitle("AUTOMOTIVE");

        viewPager = (ViewPager) findViewById(R.id.view_pager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);

        // ----- DEFAULT SELECTED MENU 1 ----- //
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        // ----- DEFAULT SELECTED MENU 1 ----- //
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_data_entry, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_save:
                Toast.makeText(ObjekPembiayaanActivity.this, "SAVE", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_reset:
                Toast.makeText(ObjekPembiayaanActivity.this, "RESET", Toast.LENGTH_SHORT).show();
                return true;


            case R.id.action_confirm:
                setResult(Activity.RESULT_OK);
                finish();
                return true;

            case R.id.action_info:

                new MaterialDialog.Builder(this)
                        .customView(R.layout.dialog_info, true)
                        .positiveText("OK")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                // CLOSE
                            }
                        })
                        .show();

                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.clearFragment();
        adapter.addFragment(new ObjekPembiayaanFragment(), "OBJEK PEMBIAYAAN");
        adapter.addFragment(new StrukturKreditMainFragment(), "STRUKTUR KREDIT");
        adapter.addFragment(new RefundFragment(), "REFUND");

        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        public void clearFragment() {
            mFragmentList.clear();
            Log.d("debug_clear?", mFragmentList.toString());
            mFragmentTitleList.clear();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
