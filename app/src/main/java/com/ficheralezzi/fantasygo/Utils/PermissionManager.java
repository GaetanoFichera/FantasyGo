package com.ficheralezzi.fantasygo.Utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by gaetano on 24/08/17.
 */

public class PermissionManager {

    private static final String TAG = "PermissionManager";
    private static final int PERMISSION_CODE_LOCATION = 101;
    private static final int PERMISSION_CODE_CAMERA = 102;

    //Location Permissions
    public static void askPermissionsLocation(Activity current_activity){
        int MyVersion = Build.VERSION.SDK_INT;
        if (MyVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (!checkIfAlreadyhavePermissionLocation(current_activity)) {
                requestForSpecificPermissionLocation(current_activity);
            }
        }
    }

    public static boolean checkIfAlreadyhavePermissionLocation(Activity current_activity) {
        int result = ContextCompat.checkSelfPermission(current_activity, Manifest.permission.ACCESS_COARSE_LOCATION);
        int result2 = ContextCompat.checkSelfPermission(current_activity, Manifest.permission.ACCESS_FINE_LOCATION);
        if (result == PackageManager.PERMISSION_GRANTED && result2 == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private static void requestForSpecificPermissionLocation(Activity current_activity) {
        ActivityCompat.requestPermissions(current_activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_CODE_LOCATION);
        //ActivityCompat.requestPermissions(current_activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_CODE_LOCATION);
    }

}
