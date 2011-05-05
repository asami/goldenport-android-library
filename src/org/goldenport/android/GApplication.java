package org.goldenport.android;

import com.google.inject.Module;

import android.app.Application;

/**
 * @since   May.  5, 2011
 * @version May.  5, 2011
 * @author  ASAMI, Tomoharu
 */
public abstract class GApplication extends Application {
    private GFactory _factory;

    @Override
    public void onCreate() {
        _factory = new GFactory(create_Modules());
    }

    @Override
    public void onTerminate() {
    }

    protected abstract Module[] create_Modules();

    public GFactory getFactory() {
        return _factory;
    }
}
