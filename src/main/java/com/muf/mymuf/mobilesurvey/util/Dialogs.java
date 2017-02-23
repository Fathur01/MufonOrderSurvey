package com.muf.mymuf.mobilesurvey.util;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AlertDialog;

import com.muf.mymuf.mobilesurvey.R;

/**
 * Created by 16003041 on 02/02/2017.
 */

public class Dialogs {

    public static void showDialog(Handler mHandler, final Activity activity, final String title, final String message, final Boolean isFinish) {

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle(title);
                builder.setMessage(message);
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (isFinish) {
                            activity.finish();
                        }
                        else {
                            // CLOSE
                        }
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }
}
