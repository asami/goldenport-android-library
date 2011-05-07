package org.goldenport.android;

import android.app.Application;

import com.google.inject.Module;

/**
 * @since   May.  5, 2011
 * @version May.  7, 2011
 * @author  ASAMI, Tomoharu
 */
public abstract class GApplication extends Application {
    private GFactory _factory;
    private Module[] _modules;

    @Override
    public void onCreate() {
        _factory = new GFactory(_get_modules());
    }

    private Module[] _get_modules() {
        if (_modules == null) {
            _modules = create_Modules();
        }
        return _modules;
    }

    protected abstract Module[] create_Modules();

    @Override
    public void onTerminate() {
    }

    public GFactory getFactory() {
        return _factory;
    }
}
