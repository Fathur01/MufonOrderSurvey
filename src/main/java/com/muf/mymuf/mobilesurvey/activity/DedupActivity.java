package com.muf.mymuf.mobilesurvey.activity;

import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.muf.mymuf.mobilesurvey.R;
import com.muf.mymuf.mobilesurvey.fragment.DedupPersonalFragment;
import com.muf.mymuf.mobilesurvey.util.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class DedupActivity extends AppCompatActivity {

    private Toolbar toolbar;
    //private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dedup);

        toolbar = (Toolbar) findViewById(R.id.toolbar_nav);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.view_pager);

        //tabLayout = (TabLayout) findViewById(R.id.tabs);

        // ----- DEFAULT SELECTED MENU 1 ----- //
        setupViewPager(viewPager);

        //tabLayout.setupWithViewPager(viewPager);
        // ----- DEFAULT SELECTED MENU 1 ----- //
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_logout:
                AlertDialog.Builder logoutWarningBuilder = new AlertDialog.Builder(DedupActivity.this);
                logoutWarningBuilder.setTitle("Konfirmasi");
                logoutWarningBuilder.setMessage("Apakah anda yakin ingin Log Out ?");
                logoutWarningBuilder.setIcon(R.mipmap.ic_launcher);
                logoutWarningBuilder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SessionManager session = new SessionManager(getApplicationContext());
                        session.logoutUser();
                        finish();
                    }
                });
                logoutWarningBuilder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Cancel
                    }
                });
                AlertDialog logoutWarningAlert = logoutWarningBuilder.create();
                logoutWarningAlert.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.clearFragment();
        adapter.addFragment(new DedupPersonalFragment(), "PER-PERSONAL");

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
            mFragmentTitleList.clear();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
