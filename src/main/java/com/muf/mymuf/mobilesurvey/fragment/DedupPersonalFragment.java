package com.muf.mymuf.mobilesurvey.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.codetroopers.betterpickers.calendardatepicker.MonthAdapter;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.muf.mymuf.mobilesurvey.activity.ListDedupActivity;
import com.muf.mymuf.mobilesurvey.R;
import com.muf.mymuf.mobilesurvey.activity.SearchResultActivity;
import com.muf.mymuf.mobilesurvey.list.SearchResultList;
import com.muf.mymuf.mobilesurvey.model.MktOrder;
import com.muf.mymuf.mobilesurvey.util.Configs;
import com.muf.mymuf.mobilesurvey.util.DatabaseHelper;
import com.muf.mymuf.mobilesurvey.util.Dialogs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DedupPersonalFragment extends Fragment implements LocationListener {

    private static final String FRAG_TAG_DATE_PICKER = "fragment_date_picker_name";

    private EditText noKTP;
    private EditText namaNasabah;
    private EditText tglLahir;
    private EditText namaIbuNasabah;

    private Button check;

    private EditText edtxtObjek;
    private EditText edtxtObjekBrand;
    private EditText edtxtObjekModel;
    private EditText edtxtChannel;

    private LocationManager locMan;
    private Location lokasi;
    private boolean isGPSEnabled = false;
    private boolean isNetworkEnabled = false;
    private Double latitude;
    private Double longitude;

    ProgressDialog progressDialog;

    private Handler mHandler;

    private final String URL_CHECK_DEDUP = Configs.URL_SERVICE + "m=p&srv=SRVAAM&rt=saveActivity";
    private final String URL_SAVE_DEDUP = Configs.URL_SERVICE_2 + "save_dedup.php";
    private final String URL_GET_OBJEK = Configs.URL_SERVICE + "m=g&srv=SRVMDM&rt=listMasterObjectAuto";
    private final String URL_GET_OBJEK_BRAND = Configs.URL_SERVICE + "m=p&srv=SRVMDM&rt=searchObjectBrand";
    private final String URL_GET_OBJEK_MODEL = Configs.URL_SERVICE + "m=p&srv=SRVMDM&rt=getModelObject";
    private final String URL_GET_CHANNEL = Configs.URL_SERVICE + "m=g&srv=SRVMDM&rt=listApplicantOrder";

    private final Integer REQUEST_CODE_GET_OBJEK = 1001;
    private final Integer REQUEST_CODE_GET_OBJEK_BRAND = 1002;
    private final Integer REQUEST_CODE_GET_OBJEK_MODEL = 1003;
    private final Integer REQUEST_CODE_GET_CHANNEL = 1004;

    private List<SearchResultList> resultList = new ArrayList<>();

    private String objGroupCode = "";
    private String objectBrandCode = "";

    public DedupPersonalFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_dedup_personal, container, false);

        mHandler = new Handler(Looper.getMainLooper());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionListener dialogPermissionListener =
                    DialogOnDeniedPermissionListener.Builder
                            .withContext(getContext())
                            .withTitle("GPS permission")
                            .withMessage("GPS permission is needed !")
                            .withButtonText(android.R.string.ok)
                            .withIcon(R.mipmap.ic_launcher)
                            .build();

            Dexter.withActivity(getActivity())
                    .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    .withListener(dialogPermissionListener)
                    .check();
        }

        //---------- CHECK GPS & NETWORK ----------//
        locMan = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        isGPSEnabled = locMan.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnabled = locMan.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if(!isGPSEnabled && !isNetworkEnabled) {
            Dialogs.showDialog(mHandler, getActivity(), "Warning", "Aktifkan GPS", true);
        }
        //------- ENDOF CHECK GPS & NETWORK -------//

        noKTP = (EditText) rootView.findViewById(R.id.edtxtNoKTP);
        namaNasabah = (EditText) rootView.findViewById(R.id.edtxtNamaNasabah);
        tglLahir = (EditText) rootView.findViewById(R.id.edtxtTglLahir);
        namaIbuNasabah = (EditText) rootView.findViewById(R.id.edtxtNamaIbu);
        check = (Button) rootView.findViewById(R.id.btn_dedup);

        tglLahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MonthAdapter.CalendarDay minDate = new MonthAdapter.CalendarDay(1960, 0, 1);
                MonthAdapter.CalendarDay maxDate = new MonthAdapter.CalendarDay(1999, 11, 31);

                CalendarDatePickerDialogFragment cal = new CalendarDatePickerDialogFragment()
                        .setDateRange(minDate, maxDate)
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
                                tglLahir.setText(strTglLahir);
                            }
                        });
                cal.show(getFragmentManager(), FRAG_TAG_DATE_PICKER);
            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(noKTP.getText().toString().equals("") && namaNasabah.getText().toString().equals("")) {
                    Snackbar.make(view, "No KTP atau Nama Wajib Di isi", Snackbar.LENGTH_SHORT).show();
                }
                else {
                    checkDedup();
                }
            }
        });

        edtxtObjek = (EditText) rootView.findViewById(R.id.input_objek);
        edtxtObjek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getObject(URL_GET_OBJEK);
                edtxtObjekBrand.setText("");
                edtxtObjekModel.setText("");
            }
        });

        edtxtObjekBrand = (EditText) rootView.findViewById(R.id.input_merek);
        edtxtObjekBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!objGroupCode.equals("")) {
                    getObjectBrand(URL_GET_OBJEK_BRAND);
                    edtxtObjekModel.setText("");
                }
                else {
                    Dialogs.showDialog(mHandler, getActivity(), "Warning", "Pilih Objek Terlebih Dahulu", false);
                }
            }
        });

        edtxtObjekModel = (EditText) rootView.findViewById(R.id.input_model);
        edtxtObjekModel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!objGroupCode.equals("") && !objectBrandCode.equals("")) {
                    getObjectModel(URL_GET_OBJEK_MODEL);
                }
                else {
                    Dialogs.showDialog(mHandler, getActivity(), "Warning", "Pilih Objek & Merek Terlebih Dahulu", false);
                }
            }
        });

        edtxtChannel = (EditText) rootView.findViewById(R.id.input_channel);
        edtxtChannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getChannel(URL_GET_CHANNEL);
            }
        });

        return rootView;
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d("debug_REQ", String.valueOf(requestCode));
        Log.d("debug_RES", String.valueOf(resultCode));

        if(resultCode != 0) {
            String item1 = data.getStringExtra("ITEM_1");
            String item2 = data.getStringExtra("ITEM_2");
            String item3 = data.getStringExtra("ITEM_3");
            String item4 = data.getStringExtra("ITEM_4");
            String item5 = data.getStringExtra("ITEM_5");
            String item6 = data.getStringExtra("ITEM_6");

            if (requestCode == REQUEST_CODE_GET_OBJEK) {
                edtxtObjek.setText(item1 + " - " + item2);

                if(item1.equals("001") || item1.equals("002")) {
                    objGroupCode = "001";
                }
                else {
                    objGroupCode = "002";
                }
            }
            else if (requestCode == REQUEST_CODE_GET_OBJEK_BRAND) {
                edtxtObjekBrand.setText(item1 + " - " + item2);
                objectBrandCode = item1;
            }
            else if (requestCode == REQUEST_CODE_GET_OBJEK_MODEL) {
                edtxtObjekModel.setText(item1 + " - " + item2);
            }
            else if (requestCode == REQUEST_CODE_GET_CHANNEL) {
                edtxtChannel.setText(item1 + " - " + item2);
            }
        }
    }

    /**
     *
     * cek dedup di service scala
     *
     **/
    public void checkDedup() {

        progressDialog = new ProgressDialog(getActivity(), R.style.AppTheme_Dark_Dialog);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        latitude = 0.0;
        longitude = 0.0;
        if(getLocation() != null) {
            latitude = getLocation().getLatitude();
            longitude = getLocation().getLongitude();
        }

        final String strUser = "user_test";
        final String strOrder = "";
        final String strKtp = noKTP.getText().toString();
        final String strNama = namaNasabah.getText().toString();
        final String strTgl = tglLahir.getText().toString();
        final String strNamaIbu = namaIbuNasabah.getText().toString();
        final String currentTime = getCurrentTime();

        // ------------ CASE 1 ------------ //

        Map<String, Object> params_case1 = new HashMap<>();
        params_case1.put("CUST_ID_NO", noKTP.getText().toString());
        params_case1.put("CUST_NAME", namaNasabah.getText().toString());
        params_case1.put("CUST_MOTHER_NAME", namaIbuNasabah.getText().toString());
        params_case1.put("CUST_BIRTH_DATE", tglLahir.getText().toString());

        // ------------ CASE 2 ------------ //

        Map<String, Object> params_case2 = new HashMap<>();
        params_case2.put("CUST_ID_NO", "");
        params_case2.put("CUST_NAME", "");
        params_case2.put("CUST_MOTHER_NAME", "");
        params_case2.put("CUST_BIRTH_DATE", "");
        params_case2.put("USER_ID", "TEST");
        params_case2.put("USER_NAME", "TEST");
        params_case2.put("USER_JOB_CODE", "TEST");
        params_case2.put("USER_BR_ID", "TEST");
        params_case2.put("DEDUP_RESULT_ROW", 0);
        params_case2.put("DEDUP_DATE", "");
        params_case2.put("CUST_DATE", "");
        params_case2.put("DEDUP_SPOUSE_NAME", "");
        params_case2.put("DEDUP_CUST_ADDR", "");
        params_case2.put("DEDUP_CALL_FROM", "");
        params_case2.put("DEDUP_CUST_TYPE", "");

        // ------------ CASE 3 ------------ //

        Map<String, Object> params_case3 = new HashMap<>();
        params_case3.put("DEDUP_RESULT_ROW", 0);

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        Map<String, Map<String, Object>> params = new HashMap<>();
        params.put("case1", params_case1);
        params.put("case2", params_case2);
        params.put("case3", params_case3);

        JSONObject jsonObject = new JSONObject(params);
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request = new Request.Builder()
                .url(URL_CHECK_DEDUP)
                .post(body)
                .addHeader("content-type", "application/json; charset=utf-8")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("debug_failure", e.getMessage());

                final String msg = e.getMessage();
                if(progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Dialogs.showDialog(mHandler, getActivity(), "Info", msg, false);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                Log.d("debug_response", result);

                if(response.code() == 200) {
                    try {
                        JSONObject json = new JSONObject(result);
                        Integer row = json.getInt("DEDUP_RESULT_ROW");

                        if(row > 0) {
                            Map<String, Object> params_dedup = new HashMap<>();
                            params_dedup.put("user_id", strUser);
                            params_dedup.put("order_id", strOrder);
                            params_dedup.put("no_ktp", strKtp);
                            params_dedup.put("nama_nasabah", strNama);
                            params_dedup.put("tgl_lahir", strTgl);
                            params_dedup.put("nama_ibu", strNamaIbu);
                            params_dedup.put("latitude", latitude);
                            params_dedup.put("longitude", longitude);
                            params_dedup.put("current_time", currentTime);

                            saveDedup(params_dedup, json.toString());
                        }
                        else {
                            Dialogs.showDialog(mHandler, getActivity(), "Info", "Tidak Ada Data", false);
                            if(progressDialog != null && progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("debug_exception", e.getMessage());
                    }
                }
                else {
                    if(progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Dialogs.showDialog(mHandler, getActivity(), "Info", "Silahkan Coba Lagi", false);
                }
            }
        });
    }

    /**
     *
     * save form pencarian dedup (MySQL)
     *
     **/
    public void saveDedup(final Map<String, Object> param, String jsonResponse) {

        final String jsonResponses = jsonResponse;

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        JSONObject jsonObject = new JSONObject(param);
        Log.d("debug_save_dedup", jsonObject.toString());

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request = new Request.Builder()
                .url(URL_SAVE_DEDUP)
                .post(body)
                .addHeader("content-type", "application/json; charset=utf-8")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("debug_insert_failure", e.getMessage());

                final String msg = e.getMessage();
                if(progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Dialogs.showDialog(mHandler, getActivity(), "Info", msg, false);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

                final String result = response.body().string();
                Log.d("debug_insert_response", result);

                if(response.code() == 200) {
                    try {
                        JSONObject json = new JSONObject(result);
                        Integer success = json.getInt("success");
                        String message = json.getString("message");
                        Integer insertedId = json.getInt("inserted_id");

                        Log.d("debug_insert_response", String.valueOf(success) + " - " + message);

                        if(success != 0) {

                            insertToMktOrder(param, insertedId);

                            Intent i = new Intent(getActivity(), ListDedupActivity.class);
                            i.putExtra("JSON_RESPONSE", jsonResponses);
                            i.putExtra("INSERTED_ID", insertedId);
                            startActivity(i);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     *
     * save form pencarian dedup (SQLite)
     *
     **/
    public void insertToMktOrder(Map<String, Object> param, Integer insertedId) {
        DatabaseHelper db = new DatabaseHelper(getContext());
        Log.d("debug_insert_SQLite", "Inserting");

        String order_id = param.get("order_id").toString();
        String user_id = param.get("user_id").toString();
        String no_ktp = param.get("no_ktp").toString();
        String nama_nasabah = param.get("nama_nasabah").toString();
        String tgl_lahir = param.get("tgl_lahir").toString();
        String nama_ibu = param.get("nama_ibu").toString();
        Double lat_lok = Double.parseDouble(param.get("latitude").toString());
        Double long_lok = Double.parseDouble(param.get("longitude").toString());
        String sent_time = param.get("current_time").toString();

        MktOrder mktOrder = new MktOrder(
                insertedId,
                order_id,
                user_id,
                no_ktp,
                nama_nasabah,
                tgl_lahir,
                nama_ibu,
                lat_lok,
                long_lok,
                sent_time);

        db.insertMktOrder(mktOrder);
    }

    /**
     *
     * get waktu device sekarang
     *
     **/
    public String getCurrentTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        return simpleDateFormat.format(new Date());
    }

    /**
     *
     * get koordinat latitude & longitude device
     *
     **/
    public Location getLocation() {
        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);

        if(permissionCheck == PackageManager.PERMISSION_GRANTED) {
            if(isNetworkEnabled) {
                locMan.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 10, this);
                if(locMan != null) {
                    lokasi = locMan.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }
            }
            else if(isGPSEnabled) {
                if(lokasi == null) {
                    locMan.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, this);
                    if(locMan != null) {
                        lokasi = locMan.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    }
                }
            }
        }

        return lokasi;
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

    /**
     *
     * get Objek dari service
     *
     **/
    public void getObject(String URL) {
        progressDialog = new ProgressDialog(getActivity(), R.style.AppTheme_Dark_Dialog);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        mHandler = new Handler(Looper.getMainLooper());

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(URL)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("debug_failure", e.getMessage());

                final String msg = e.getMessage();
                if(progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Dialogs.showDialog(mHandler, getActivity(), "Info", msg, false);
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
                        resultList.clear();
                        for(int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            String objCode = jsonObject.getString("obj_code");
                            String objDesc = jsonObject.getString("obj_desc");
                            resultList.add(new SearchResultList("Kode", objCode, "Objek", objDesc, "", "", "", "", "", "", "", ""));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Intent intent = new Intent(getActivity(), SearchResultActivity.class);
                    intent.putParcelableArrayListExtra("SEARCH_DATA", (ArrayList<? extends Parcelable>) resultList);
                    intent.putExtra("SEARCH_KEY", "Objek");
                    intent.putExtra("SEARCH_REQUEST_CODE", REQUEST_CODE_GET_OBJEK);
                    startActivityForResult(intent, REQUEST_CODE_GET_OBJEK);
                }
                else {
                    if(progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Dialogs.showDialog(mHandler, getActivity(), "Info", "Silahkan Coba Lagi", false);
                }
            }
        });
    }

    /**
     *
     * get Merek dari service
     *
     **/
    public void getObjectBrand(String URL) {
        progressDialog = new ProgressDialog(getActivity(), R.style.AppTheme_Dark_Dialog);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        mHandler = new Handler(Looper.getMainLooper());

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        Map<String, String> params = new HashMap<>();
        params.put("obj_group_code", objGroupCode);

        JSONObject parameter = new JSONObject(params);
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON, parameter.toString());
        Request request = new Request.Builder()
                .url(URL)
                .post(body)
                .addHeader("content-type", "application/json; charset=utf-8")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("debug_failure", e.getMessage());

                final String msg = e.getMessage();
                if(progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Dialogs.showDialog(mHandler, getActivity(), "Info", msg, false);
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
                        resultList.clear();
                        for(int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            String objBrandCode = jsonObject.getString("obj_brand_code");
                            String objBrand = jsonObject.getString("obj_brand_desc");
                            String objGroupCode = jsonObject.getString("obj_group_code");
                            String objMadeCode = jsonObject.getString("obj_made_code");
                            resultList.add(new SearchResultList("Brand Code", objBrandCode, "Brand", objBrand, "Group Code", objGroupCode, "Made Code", objMadeCode, "", "", "", ""));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Intent intent = new Intent(getActivity(), SearchResultActivity.class);
                    intent.putParcelableArrayListExtra("SEARCH_DATA", (ArrayList<? extends Parcelable>) resultList);
                    intent.putExtra("SEARCH_KEY", "Merek");
                    intent.putExtra("SEARCH_REQUEST_CODE", REQUEST_CODE_GET_OBJEK_BRAND);
                    startActivityForResult(intent, REQUEST_CODE_GET_OBJEK_BRAND);
                }
                else {
                    if(progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Dialogs.showDialog(mHandler, getActivity(), "Info", "Silahkan Coba Lagi", false);
                }
            }
        });
    }

    /**
     *
     * get Model dari service
     *
     **/
    public void getObjectModel(String URL) {
        progressDialog = new ProgressDialog(getActivity(), R.style.AppTheme_Dark_Dialog);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        mHandler = new Handler(Looper.getMainLooper());

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        Map<String, String> params = new HashMap<>();
        params.put("OBJ_BRAND_CODE", objectBrandCode);
        params.put("OBJ_GROUP_CODE", objGroupCode);
        params.put("OBJ_MODEL_DESC", "");
        params.put("OBJ_MODEL", "");
        params.put("OBJ_TYPE_CODE", "");

        JSONObject parameter = new JSONObject(params);
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON, parameter.toString());
        Request request = new Request.Builder()
                .url(URL)
                .post(body)
                .addHeader("content-type", "application/json; charset=utf-8")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("debug_failure", e.getMessage());

                final String msg = e.getMessage();
                if(progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Dialogs.showDialog(mHandler, getActivity(), "Info", msg, false);
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
                        resultList.clear();
                        for(int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            String modelCode = jsonObject.getString("OBJ_MODEL");
                            String modelDesc = jsonObject.getString("OBJ_MODEL_DESC");
                            String typeCode = jsonObject.getString("OBJ_TYPE_CODE");
                            String typeDesc = jsonObject.getString("OBJ_TYPE_DESC");
                            resultList.add(new SearchResultList("Model Code", modelCode, "Model", modelDesc, "Type Code", typeCode, "Type", typeDesc, "", "", "", ""));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Intent intent = new Intent(getActivity(), SearchResultActivity.class);
                    intent.putParcelableArrayListExtra("SEARCH_DATA", (ArrayList<? extends Parcelable>) resultList);
                    intent.putExtra("SEARCH_KEY", "Model");
                    intent.putExtra("SEARCH_REQUEST_CODE", REQUEST_CODE_GET_OBJEK_MODEL);
                    startActivityForResult(intent, REQUEST_CODE_GET_OBJEK_MODEL);
                }
                else {
                    if(progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Dialogs.showDialog(mHandler, getActivity(), "Info", "Silahkan Coba Lagi", false);
                }
            }
        });
    }

    /**
     *
     * get Objek dari service
     *
     **/
    public void getChannel(String URL) {
        progressDialog = new ProgressDialog(getActivity(), R.style.AppTheme_Dark_Dialog);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        mHandler = new Handler(Looper.getMainLooper());

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(URL)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("debug_failure", e.getMessage());

                final String msg = e.getMessage();
                if(progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Dialogs.showDialog(mHandler, getActivity(), "Info", msg, false);
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
                        JSONObject jObject = new JSONObject(result);

                        JSONArray jsonArray = jObject.getJSONArray("data5");
                        resultList.clear();
                        for(int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            String chCode = jsonObject.getString("channel_code");
                            String chDesc = jsonObject.getString("channel_desc");
                            String chDeleted = jsonObject.getString("deleted");
                            String chLogId = jsonObject.getString("log_id");
                            resultList.add(new SearchResultList("Kode", chCode, "Channel", chDesc, "", "", "", "", "", "", "", ""));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Intent intent = new Intent(getActivity(), SearchResultActivity.class);
                    intent.putParcelableArrayListExtra("SEARCH_DATA", (ArrayList<? extends Parcelable>) resultList);
                    intent.putExtra("SEARCH_KEY", "Sales Channel");
                    intent.putExtra("SEARCH_REQUEST_CODE", REQUEST_CODE_GET_CHANNEL);
                    startActivityForResult(intent, REQUEST_CODE_GET_CHANNEL);
                }
                else {
                    if(progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Dialogs.showDialog(mHandler, getActivity(), "Info", "Silahkan Coba Lagi", false);
                }
            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {
        longitude = location.getLongitude();
        latitude = location.getLatitude();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
