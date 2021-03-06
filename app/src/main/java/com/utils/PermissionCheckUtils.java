package com.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by Chant on 2018/7/8.
 */

public class PermissionCheckUtils {
    /**
     *  读写权限  自己可以添加需要判断的权限
     */
    public static String[]permissionsREAD={
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE };
    /**
     * 判断是否缺少权限
     */
    public static boolean lacksPermission(Context mContexts) {
        return ContextCompat.checkSelfPermission(mContexts,  Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_DENIED;
//        return ContextCompat.checkSelfPermission(mContexts, permission) ==
//                PackageManager.PERMISSION_DENIED;
    }
//    /**
//     * 判断权限集合
//     * permissions 权限数组
//     * return true-表示没有改权限  false-表示权限已开启
//     */
//    public static boolean lacksPermissions(Context mContexts, permissions) {
//        for (String permission : permissions) {
//            if (lacksPermission(mContexts,permission)) {
//                return true;
//            }
//        }
//        return false;
//    }
    //读写权限
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    /**
     * 检查应用程序是否有权写入设备存储
     * 如果应用程序没有权限，则会提示用户授予权限
     *
     * @param activity 所在的Activity
     */
    public static boolean  verifyStoragePermissions(Activity activity) {
        boolean isHas=true;
        //检查应用程序是否有权写入设备存储
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            isHas=false;
            //如果应用程序没有权限，则会提示用户授予权限
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
        return  isHas;
    }


}
