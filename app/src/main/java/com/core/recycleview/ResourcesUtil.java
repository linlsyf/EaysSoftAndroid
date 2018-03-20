package com.core.recycleview;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.core.CoreApplication;

public class ResourcesUtil {

    public static float getResourcesFloat(Context context, int id) {
        return Float.parseFloat(context.getResources().getString(id));
    }

    public static float getResourcesFloat( int id) {
        return Float.parseFloat(CoreApplication.getInstance().getResources().getString(id));
    }

    public static String getResourcesString(Context context, int id) {
        return context.getResources().getString(id);
    }

    public static String getResourcesString(int id) {
        return CoreApplication.getInstance().getResources().getString(id);
    }

    public static int getResourcesColor(Context context, int id) {
        return context.getResources().getColor(id);
    }

    public static float getResourcesDimen(Context context, int id) {
        return context.getResources().getDimension(id);
    }

    public static Drawable getResourcesDrawable(Context context, int id) {
        return context.getResources().getDrawable(id);
    }
}