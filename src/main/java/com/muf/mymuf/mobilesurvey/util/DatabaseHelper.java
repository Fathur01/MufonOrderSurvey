package com.muf.mymuf.mobilesurvey.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.muf.mymuf.mobilesurvey.model.MktOrder;

/**
 * Created by 16003041 on 02/02/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "muforder_db";

    // Table Names
    private static final String TABLE_MKT_ORDER = "mkt_order";

    // Column names
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_ORDER_ID = "order_id";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_NO_KTP = "no_ktp";
    private static final String COLUMN_NAMA_NASABAH = "nama_nasabah";
    private static final String COLUMN_TGL_LAHIR = "tgl_lahir";
    private static final String COLUMN_NAMA_IBU = "nama_ibu";
    private static final String COLUMN_LAT = "lat";
    private static final String COLUMN_LONG = "long";
    private static final String COLUMN_SENT_TIME = "sent_time";

    // Table Create Statements
    private static final String CREATE_TABLE_MKT_ORDER =
            "CREATE TABLE IF NOT EXISTS " + TABLE_MKT_ORDER + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY ,"
                    + COLUMN_ORDER_ID + " TEXT,"
                    + COLUMN_USER_ID + " TEXT,"
                    + COLUMN_NO_KTP + " TEXT,"
                    + COLUMN_NAMA_NASABAH + " TEXT,"
                    + COLUMN_TGL_LAHIR + " TEXT,"
                    + COLUMN_NAMA_IBU + " TEXT,"
                    + COLUMN_LAT + " DOUBLE,"
                    + COLUMN_LONG + " DOUBLE,"
                    + COLUMN_SENT_TIME + " TEXT"
                    + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // creating required tables
        sqLiteDatabase.execSQL(CREATE_TABLE_MKT_ORDER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // on upgrade drop older tables
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_MKT_ORDER);

        onCreate(sqLiteDatabase);
    }

    public void insertMktOrder(MktOrder mktOrder) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, mktOrder.getId());
        values.put(COLUMN_ORDER_ID, mktOrder.getOrderID());
        values.put(COLUMN_USER_ID, mktOrder.getUserID());
        values.put(COLUMN_NO_KTP, mktOrder.getNoKTP());
        values.put(COLUMN_NAMA_NASABAH, mktOrder.getNamaNasabah());
        values.put(COLUMN_TGL_LAHIR, mktOrder.getTglLahir());
        values.put(COLUMN_NAMA_IBU, mktOrder.getNamaIbu());
        values.put(COLUMN_LAT, mktOrder.getLatitude());
        values.put(COLUMN_LONG, mktOrder.getLongitude());
        values.put(COLUMN_SENT_TIME, mktOrder.getSentTime());

        db.insert(TABLE_MKT_ORDER, null, values);
        db.close();
    }

    public void updateOrderIdMktOrder(Integer id, Integer orderId) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ORDER_ID, orderId);
        db.update(TABLE_MKT_ORDER, values, COLUMN_ID + " = ?", new String[] { String.valueOf(id) });
    }
}
