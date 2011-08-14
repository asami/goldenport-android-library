package org.goldenport.android;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @since   Apr. 29, 2011
 * @version Aug. 14, 2011
 * @author  ASAMI, Tomoharu
 */
@Singleton
public class GModel<C extends GContext, E extends GErrorModel<C>> {
    @Inject
    protected Context context;

    @Inject
    public C gcontext;

    @Inject
    protected E gerrors;

    protected SharedPreferences preferences;

    /*
     * Debug switches
     */
    public boolean isDebugMoreReadError() {
        return false;
    }

    public void open() {
        preferences = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
    }

    public void close() {
    }

    protected String get_pref_server_url() {
        return preferences.getString("server", gcontext.getConfigServerUrl());
    }
}
