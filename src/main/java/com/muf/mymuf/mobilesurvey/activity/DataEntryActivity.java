package com.muf.mymuf.mobilesurvey.activity;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.muf.mymuf.mobilesurvey.R;
import com.muf.mymuf.mobilesurvey.fragment.customer.IdentitasFragment;
import com.muf.mymuf.mobilesurvey.fragment.customer.PendapatanFragment;
import com.muf.mymuf.mobilesurvey.fragment.customer.PekerjaanFragment;
import com.muf.mymuf.mobilesurvey.fragment.aplikasi.ObjekPembiayaanListFragment;
import com.muf.mymuf.mobilesurvey.fragment.aplikasi.AplikasiFragment;
import com.muf.mymuf.mobilesurvey.fragment.dokumen.DokumenFragment;
import com.muf.mymuf.mobilesurvey.fragment.survey.HasilSurveyFragment;
import com.muf.mymuf.mobilesurvey.fragment.survey.DataKepemilikanFragment;

import java.util.ArrayList;
import java.util.List;

public class DataEntryActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager1;
    private ViewPager viewPager2;
    private ViewPager viewPager3;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_entry);

        toolbar = (Toolbar) findViewById(R.id.toolbar_nav);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Customer Perorangan");

        viewPager1 = (ViewPager) findViewById(R.id.view_pager_1);
        viewPager2 = (ViewPager) findViewById(R.id.view_pager_2);
        viewPager3 = (ViewPager) findViewById(R.id.view_pager_3);
        frameLayout = (FrameLayout) findViewById(R.id.frame_dokumen_container);

        tabLayout = (TabLayout) findViewById(R.id.tabs);

        // ----- DEFAULT SELECTED MENU 1 ----- //
        setupViewPager(viewPager1, R.id.action_menu_customer);

        tabLayout.setupWithViewPager(viewPager1);

        viewPager1.setVisibility(View.VISIBLE);
        viewPager2.setVisibility(View.GONE);
        viewPager3.setVisibility(View.GONE);
        frameLayout.setVisibility(View.GONE);
        // ----- DEFAULT SELECTED MENU 1 ----- //

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        //BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.action_menu_customer :
                        tabLayout.setupWithViewPager(viewPager1);
                        setupViewPager(viewPager1, R.id.action_menu_customer);

                        tabLayout.setVisibility(View.VISIBLE);
                        viewPager1.setVisibility(View.VISIBLE);
                        viewPager2.setVisibility(View.GONE);
                        viewPager3.setVisibility(View.GONE);
                        frameLayout.setVisibility(View.GONE);
                        break;

                    case R.id.action_menu_aplikasi:
                        tabLayout.setupWithViewPager(viewPager2);
                        setupViewPager(viewPager2, R.id.action_menu_aplikasi);

                        tabLayout.setVisibility(View.VISIBLE);
                        viewPager1.setVisibility(View.GONE);
                        viewPager2.setVisibility(View.VISIBLE);
                        viewPager3.setVisibility(View.GONE);
                        frameLayout.setVisibility(View.GONE);
                        break;

                    case R.id.action_menu_survey:
                        tabLayout.setupWithViewPager(viewPager3);
                        setupViewPager(viewPager3, R.id.action_menu_survey);

                        tabLayout.setVisibility(View.VISIBLE);
                        viewPager1.setVisibility(View.GONE);
                        viewPager2.setVisibility(View.GONE);
                        viewPager3.setVisibility(View.VISIBLE);
                        frameLayout.setVisibility(View.GONE);
                        break;

                    case R.id.action_menu_dokumen:
                        tabLayout.setVisibility(View.GONE);
                        viewPager1.setVisibility(View.GONE);
                        viewPager2.setVisibility(View.GONE);
                        viewPager3.setVisibility(View.GONE);
                        frameLayout.setVisibility(View.VISIBLE);

                        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
                        tx.replace(R.id.frame_dokumen_container, new DokumenFragment());
                        tx.commit();
                        break;
                }

                return true;
            }

            // START
            Integer orderId = getIntent().getExtras().getInt("ORDER_ID");
        });
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
                Toast.makeText(DataEntryActivity.this, "SAVE", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_reset:
                Toast.makeText(DataEntryActivity.this, "RESET", Toast.LENGTH_SHORT).show();
                return true;


            case R.id.action_confirm:
                Toast.makeText(DataEntryActivity.this, "KONFIRMASI", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(DataEntryActivity.this);
        builder.setTitle("Warning");
        builder.setMessage("Data anda belum tersimpan.\nApakah anda yakin untuk keluar ?");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // CLOSE
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }

    public void setupViewPager(ViewPager viewPager, Integer itemId) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        if(itemId == R.id.action_menu_customer) {
            adapter.clearFragment();
            adapter.addFragment(new IdentitasFragment(), "IDENTITAS");
            adapter.addFragment(new PekerjaanFragment(), "PEKERJAAN");
            adapter.addFragment(new PendapatanFragment(), "PENDAPATAN");
        }
        else if(itemId == R.id.action_menu_aplikasi) {
            adapter.clearFragment();
            adapter.addFragment(new AplikasiFragment(), "APLIKASI");
            adapter.addFragment(new ObjekPembiayaanListFragment(), "OBJEK PEMBIAYAAN");
        }
        else if(itemId == R.id.action_menu_survey) {
            adapter.clearFragment();
            adapter.addFragment(new DataKepemilikanFragment(), "DATA KEPEMILIKAN");
            adapter.addFragment(new HasilSurveyFragment(), "HASIL SURVEY");
        }
        else {
            adapter.clearFragment();
        }

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
