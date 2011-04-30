package org.goldenport.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * @since   Apr. 28, 2011
 * @version Apr. 30, 2011
 * @author  ASAMI, Tomoharu
 */
public abstract class GActivityTrait {
    protected Activity activity;
    protected IGActivity gactivity;

    public final void setActivity(IGActivity activity) {
        gactivity = activity;
        this.activity = (Activity)activity;
    }

    public final View findViewById(int id) {
        return activity.findViewById(id);
    }

    public final String getString(int resId) {
        return activity.getString(resId);
    }

    public final String getString(int resId, Object... formatArgs) {
        return activity.getString(resId, formatArgs);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    public void onCreate(Bundle savedInstanceState) {
    }

    public void onPostCreate(Bundle savedInstanceState) {
    }

    public void onStart() {
    }

    public void onRestart() {
    }

    public void onResume() {
    }

    public void onPostResume() {
    }

    public void onPause() {
    }

    public void onStop() {
    }

    public void onDestroy() {
    }

    public void onSaveInstanceState(Bundle outState) {
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
    }

    public void onUpdate() {
    }
}
