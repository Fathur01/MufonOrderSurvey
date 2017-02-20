package com.muf.mymuf.mobilesurvey.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.codetroopers.betterpickers.calendardatepicker.MonthAdapter;
import com.muf.mymuf.mobilesurvey.R;
import com.muf.mymuf.mobilesurvey.util.Configs;
import com.muf.mymuf.mobilesurvey.util.Dialogs;
import com.muf.mymuf.mobilesurvey.util.SessionManager;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SearchDataEntryActivity extends AppCompatActivity {

    private static final String FRAG_TAG_DATE_PICKER = "fragment_date_picker_name";
    private EditText tglOrderFrom;
    private EditText tglOrderTo;

    private Handler mHandler;

    private Integer yearTo;
    private Integer monthTo;
    private Integer dayTo;

    ProgressDialog progressDialog;

    private final String URL_GET_LIST_IDE = Configs.URL_SERVICE + "m=p&srv=SRVAAM&rt=searchListIDEPersonal";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_data_entry);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("List Data Entry");

        SessionManager session = new SessionManager(getApplicationContext());
        session.checkLogin();
        HashMap<String, String> user = session.getUserProfile();
        String nik = user.get(SessionManager.KEY_NIK);
        String nama = user.get(SessionManager.KEY_FULLNAME);

        HashMap<String, String> userLocation = session.getUserLocation();
        String branchCode = userLocation.get(SessionManager.KEY_BRANCH_CODE);

        if(session.isLoggedIn()) {
            Snackbar snackbar = Snackbar.make(toolbar, "Selamat Datang, " + nik + " - " + nama, Snackbar.LENGTH_LONG);
            snackbar.show();
        }

        mHandler = new Handler(Looper.getMainLooper());

        tglOrderFrom = (EditText) findViewById(R.id.input_tgl_from);
        tglOrderTo = (EditText) findViewById(R.id.input_tgl_to);

        tglOrderFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DateTime now = DateTime.now();

                MonthAdapter.CalendarDay maxDate = new MonthAdapter.CalendarDay(now.getYear(), now.getMonthOfYear() - 1, now.getDayOfMonth());

                CalendarDatePickerDialogFragment cal = new CalendarDatePickerDialogFragment()
                        .setDateRange(null, maxDate)
                        .setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                            @Override
                            public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
                                yearTo = year;
                                monthTo = monthOfYear;
                                dayTo = dayOfMonth;

                                String strDay;
                                if(dayOfMonth <= 9) {
                                    strDay = "0" + String.valueOf(dayOfMonth);
                                }
                                else {
                                    strDay = String.valueOf(dayOfMonth);
                                }

                                String strMonth = getMonthLetter(monthOfYear);
                                String strYear = String.valueOf(year);
                                String strTglLahir = strDay + "-" + strMonth + "-" + strYear;

                                Log.d("debug_calendar", strTglLahir);
                                tglOrderFrom.setText(strTglLahir);
                                tglOrderTo.setText("");
                            }
                        });
                cal.show(getSupportFragmentManager(), FRAG_TAG_DATE_PICKER);
            }
        });

        tglOrderTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!tglOrderFrom.getText().toString().equals("") || yearTo != null || monthTo != null || dayTo != null ) {
                    MonthAdapter.CalendarDay minDate = new MonthAdapter.CalendarDay(yearTo, monthTo, dayTo);

                    CalendarDatePickerDialogFragment cal = new CalendarDatePickerDialogFragment()
                            .setDateRange(minDate, null)
                            .setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                                @Override
                                public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
                                    String strDay;
                                    if(dayOfMonth <= 9) {
                                        strDay = "0" + String.valueOf(dayOfMonth);
                                    }
                                    else {
                                        strDay = String.valueOf(dayOfMonth);
                                    }

                                    String strMonth = getMonthLetter(monthOfYear);
                                    String strYear = String.valueOf(year);
                                    String strTglLahir = strDay + "-" + strMonth + "-" + strYear;

                                    Log.d("debug_calendar", strTglLahir);
                                    tglOrderTo.setText(strTglLahir);
                                }
                            });
                    cal.show(getSupportFragmentManager(), FRAG_TAG_DATE_PICKER);
                }
            }
        });

        Button searchList = (Button) findViewById(R.id.btn_search);
        searchList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchListDataEntry();
            }
        });

        Button newOrder = (Button) findViewById(R.id.btn_create_order);
        newOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchDataEntryActivity.this, DedupActivity.class);
                startActivity(intent);
            }
        });
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
                AlertDialog.Builder logoutWarningBuilder = new AlertDialog.Builder(SearchDataEntryActivity.this);
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
     * get list data entry
     *
     **/
    public void searchListDataEntry() {

        progressDialog = new ProgressDialog(SearchDataEntryActivity.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        String data1 = "";
        String data2 = "";
        String data3 = "";
        String data4 = "";
        String data5 = "";
        String data6 = "";
        String data7 = "";

        String branchCode = "0201";
        String tglAwal = "01-FEB-2017";
        String tglAkhir = "06-FEB-2017";
        String salesChannel = "";
        String outletChannelCode = "";
        String supplierCode = "";
        String tipeNasabah = "";
        String sumberNasabah = "";

        if (!branchCode.equals("")){
            data1 = "and i.branch_id = '" + branchCode +"'";
        }
        if (!tglAwal.equals("")){
            data2 = "and trunc(i.order_date) BETWEEN '" + tglAwal + "' AND '" + tglAkhir + "'";
        }
        if (!salesChannel.equals("")){
            data3 = "AND i.channel_type_code = '" + salesChannel + "'";
        }
        if (!outletChannelCode.equals("")){
            data4 = "AND i.channel_code = '" + outletChannelCode + "'";
        }
        if (!supplierCode.equals("")){
            data5 = "AND i.outlet_code = '" + supplierCode + "'";
        }
        if (!tipeNasabah.equals("")){
            data6 = "AND i.applicant_type = '" + tipeNasabah + "'";
        }
        if (!sumberNasabah.equals("")) {
            data7 = "AND i.cust_origin_code = '" + sumberNasabah + "'";
        }

        Map <String, String> params = new HashMap<>();
        params.put("data1", data1);
        params.put("data2", data2);
        params.put("data3", data3);
        params.put("data4", data4);
        params.put("data5", data5);
        params.put("data6", data6);
        params.put("data7", data7);

        Log.d("debugggg", params.toString());

        JSONObject jsonObject = new JSONObject(params);
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request = new Request.Builder()
                .url(URL_GET_LIST_IDE)
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
                Dialogs.showDialog(mHandler, SearchDataEntryActivity.this, "Info", msg, false);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                Log.d("debug_response", result);

                if(response.code() == 200) {
                    if(progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    try {
                        JSONArray jsonArray = new JSONArray(result);

                        Intent i = new Intent(SearchDataEntryActivity.this, ListDataEntryActivity.class);
                        i.putExtra("JSON_RESPONSE", jsonArray.toString());
                        startActivity(i);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    if(progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Dialogs.showDialog(mHandler, SearchDataEntryActivity.this, "Info", "Silahkan Coba Lagi", false);
                }
            }
        });
    }

    /**
     *
     * convert integer bulan (0-11) menjadi 3 digit string bulan dalam bahasa inggris (JAN-DEC)
     *
     **/
    public String getMonthLetter(Integer monthOfYear) {
        String monthString;
        switch (monthOfYear) {
            case 0:
                monthString = "JAN";
                break;
            case 1:
                monthString = "FEB";
                break;
            case 2:
                monthString = "MAR";
                break;
            case 3:
                monthString = "APR";
                break;
            case 4:
                monthString = "MAY";
                break;
            case 5:
                monthString = "JUN";
                break;
            case 6:
                monthString = "JUL";
                break;
            case 7:
                monthString = "AUG";
                break;
            case 8:
                monthString = "SEP";
                break;
            case 9:
                monthString = "OCT";
                break;
            case 10:
                monthString = "NOV";
                break;
            case 11:
                monthString = "DEC";
                break;
            default:
                monthString = "INVALID";
                break;
        }
        return monthString;
    }
}
