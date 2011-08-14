package org.goldenport.android;

import android.app.Application;

import com.google.inject.Module;

/**
 * @since   May.  5, 2011
 * @version Aug. 14, 2011
 * @author  ASAMI, Tomoharu
 */
public abstract class GApplication extends Application {
    private GFactory _factory;
    private Module[] _modules;
    private GModel<?, ?> _model;

    @Override
    public void onCreate() {
        _factory = create_Factory(_get_modules());
        _model = _factory.createModel();
        _model.open();
    }

    private Module[] _get_modules() {
        if (_modules == null) {
            _modules = create_Modules();
        }
        return _modules;
    }

    protected abstract Module[] create_Modules();

    protected GFactory create_Factory(Module[] modules) {
        return new GFactory(modules);
    }

    @Override
    public void onTerminate() {
        _model.close();
    }

    public GFactory getFactory() {
        return _factory;
    }
}
