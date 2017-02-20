package com.muf.mymuf.mobilesurvey.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.muf.mymuf.mobilesurvey.activity.LoginActivity;

import java.util.HashMap;

/**
 * Created by 16003041 on 03/02/2017.
 */

public class SessionManager {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "MobilSurveyPreference";
    private static final String IS_LOGIN = "IsLoggedIn";

    public static final String KEY_NIK = "nik";
    public static final String KEY_FULLNAME = "fullname";
    public static final String KEY_PARENT_NIK = "parent_nik";
    public static final String KEY_PORTOFOLIO_DESC = "portofolio_desc";
    public static final String KEY_LAST_LOGIN_DATE = "last_login_date";

    public static final String KEY_BRANCH_CODE = "branch_code";
    public static final String KEY_BRANCH_NAME = "branch_name";
    public static final String KEY_LOCATION_CODE = "location_code";
    public static final String KEY_LOCATION_NAME = "location_name";

    public static final String KEY_JOB_CODE = "job_code";
    public static final String KEY_JOB_DESC = "job_desc";

    public static final String KEY_ROLE_CODE = "role_code";

    public SessionManager(Context con) {
        this.context = con;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createLoginSession(String nik, String fullname, String branchCode) {

        editor.putBoolean(IS_LOGIN, true);

        editor.putString(KEY_NIK, nik);
        editor.putString(KEY_FULLNAME, fullname);
        editor.putString(KEY_BRANCH_CODE, branchCode);

        editor.commit();
    }

    public HashMap<String, String> getUserProfile() {
        HashMap<String, String> user = new HashMap<String, String>();

        user.put(KEY_NIK, sharedPreferences.getString(KEY_NIK, null));
        user.put(KEY_FULLNAME, sharedPreferences.getString(KEY_FULLNAME, null));
        user.put(KEY_PARENT_NIK, sharedPreferences.getString(KEY_PARENT_NIK, null));
        user.put(KEY_PORTOFOLIO_DESC, sharedPreferences.getString(KEY_PORTOFOLIO_DESC, null));
        user.put(KEY_LAST_LOGIN_DATE, sharedPreferences.getString(KEY_LAST_LOGIN_DATE, null));

        return user;
    }

    public HashMap<String, String> getUserLocation() {
        HashMap<String, String> user = new HashMap<String, String>();

        user.put(KEY_BRANCH_CODE, sharedPreferences.getString(KEY_BRANCH_CODE, null));
        user.put(KEY_BRANCH_NAME, sharedPreferences.getString(KEY_BRANCH_NAME, null));
        user.put(KEY_LOCATION_CODE, sharedPreferences.getString(KEY_LOCATION_CODE, null));
        user.put(KEY_LOCATION_NAME, sharedPreferences.getString(KEY_LOCATION_NAME, null));

        return user;
    }

    public HashMap<String, String> getUserJob() {
        HashMap<String, String> user = new HashMap<String, String>();

        user.put(KEY_JOB_CODE, sharedPreferences.getString(KEY_JOB_CODE, null));
        user.put(KEY_JOB_DESC, sharedPreferences.getString(KEY_JOB_DESC, null));

        return user;
    }

    public HashMap<String, String> getUserRole() {
        HashMap<String, String> user = new HashMap<String, String>();

        user.put(KEY_ROLE_CODE, sharedPreferences.getString(KEY_ROLE_CODE, null));

        return user;
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(IS_LOGIN, false);
    }

    public void checkLogin() {
        if(!this.isLoggedIn()) {
            Intent i = new Intent(context, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }

    public void logoutUser() {
        editor.clear();
        editor.commit();
        Intent i = new Intent(context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

}
