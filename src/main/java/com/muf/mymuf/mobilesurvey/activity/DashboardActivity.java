package com.muf.mymuf.mobilesurvey.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.muf.mymuf.mobilesurvey.R;
import com.muf.mymuf.mobilesurvey.util.SessionManager;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        LinearLayout newOrder = (LinearLayout) findViewById(R.id.new_order_container);
        LinearLayout toDoList = (LinearLayout) findViewById(R.id.todo_container);
        LinearLayout monitoring = (LinearLayout) findViewById(R.id.monitoring_container);
        LinearLayout profile = (LinearLayout) findViewById(R.id.profile_container);
        LinearLayout logout = (LinearLayout) findViewById(R.id.logout_container);

        newOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
                intent.putExtra("MENU_NAME", "New Order");
                intent.putExtra("FLAG", 0);
                startActivity(intent);
                //finish();
                //overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        toDoList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
                intent.putExtra("MENU_NAME", "To Do List");
                intent.putExtra("FLAG",1);
                startActivity(intent);
                //finish();
                //overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        monitoring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
                intent.putExtra("MENU_NAME", "Monitoring");
                intent.putExtra("FLAG",2);
                startActivity(intent);
                //finish();
                //overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
                intent.putExtra("MENU_NAME", "Profile");
                intent.putExtra("FLAG",3);
                startActivity(intent);
                //Toast.makeText(DashboardActivity.this, "PROFILE", Toast.LENGTH_SHORT).show();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder logoutWarningBuilder = new AlertDialog.Builder(DashboardActivity.this);
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
        });
    }
}
