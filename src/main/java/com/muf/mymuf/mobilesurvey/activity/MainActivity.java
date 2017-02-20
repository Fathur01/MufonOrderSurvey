package com.muf.mymuf.mobilesurvey.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.muf.mymuf.mobilesurvey.R;
import com.muf.mymuf.mobilesurvey.fragment.DedupPersonalFragment;
import com.muf.mymuf.mobilesurvey.fragment.MonitoringFragment;
import com.muf.mymuf.mobilesurvey.fragment.ProfileFragment;
import com.muf.mymuf.mobilesurvey.fragment.ToDoListFragment;
import com.muf.mymuf.mobilesurvey.util.SessionManager;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Mobile Survey");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        // -------------------------------------------------------------------------------------- //
        SessionManager session = new SessionManager(getApplicationContext());
        session.checkLogin();
        HashMap<String, String> user = session.getUserProfile();
        String nik = user.get(SessionManager.KEY_NIK);
        String nama = user.get(SessionManager.KEY_FULLNAME);
        if(session.isLoggedIn()) {
            Snackbar snackbar = Snackbar.make(toolbar, "Selamat Datang, " + nik + " - " + nama, Snackbar.LENGTH_SHORT);
            snackbar.show();
        }

        String menuName = getIntent().getStringExtra("MENU_NAME");
        Integer flagMenu = getIntent().getIntExtra("FLAG", -1);
        navigationView.getMenu().getItem(flagMenu).setChecked(true);

        if(flagMenu != -1) {
            if(flagMenu == 0) {
                getSupportActionBar().setSubtitle(menuName);

                FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
                tx.replace(R.id.frame_nav_container, new DedupPersonalFragment());
                tx.commit();
            }
            else if(flagMenu == 1) {
                getSupportActionBar().setSubtitle(menuName);

                FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
                tx.replace(R.id.frame_nav_container, new ToDoListFragment());
                tx.commit();
            }
            else if(flagMenu == 2) {
                getSupportActionBar().setSubtitle(menuName);

                FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
                tx.replace(R.id.frame_nav_container, new MonitoringFragment());
                tx.commit();
            }
            else if(flagMenu == 3) {
                getSupportActionBar().setSubtitle(menuName);

                FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
                tx.replace(R.id.frame_nav_container, new ProfileFragment());
                tx.commit();
                // GO TO PROFILE FRAGMENT
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        Fragment fragment = null;

        if (id == R.id.nav_new_order) {
            getSupportActionBar().setSubtitle("New Order");
            fragment = new DedupPersonalFragment();
        }
        else if (id == R.id.nav_to_do_list) {
            getSupportActionBar().setSubtitle("To Do List");
            fragment = new ToDoListFragment();
        }
        else if (id == R.id.nav_monitoring) {
            getSupportActionBar().setSubtitle("Monitoring");
            fragment = new MonitoringFragment();
        }
        else if (id == R.id.nav_profile) {
            getSupportActionBar().setSubtitle("Profile");
            fragment = new ProfileFragment();
        }
        else if (id == R.id.nav_logout) {
            AlertDialog.Builder logoutWarningBuilder = new AlertDialog.Builder(MainActivity.this);
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
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_nav_container, fragment).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
