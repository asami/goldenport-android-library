package org.goldenport.android;

import android.content.Context;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

/**
 * @since   Apr. 30, 2011
 * @version May.  4, 2011
 * @author  ASAMI, Tomoharu
 */
public abstract class GModule extends AbstractModule {
    private Context _context;

    public GModule(Context context) {
        _context = context;
    }

    @Override
    protected void configure() {
        bind(Context.class).toInstance(_context);
        bind(GContext.class).to(context_Class()).in(Singleton.class);
        bind(GErrorModel.class).to(errormodel_Class()).in(Singleton.class);
        bind(GModel.class).to(model_Class()).in(Singleton.class);
        bind(GAgent.class).to(agent_Class()).in(Singleton.class);
        bind(GController.class).to(controller_Class());
    }

    protected abstract Class<? extends GContext> context_Class();
    protected abstract Class<? extends GErrorModel<?>> errormodel_Class();
    protected abstract Class<? extends GModel<?, ?>> model_Class();
    protected abstract Class<? extends GController<?, ?, ?, ?>> controller_Class();
    protected abstract Class<? extends GAgent<?, ?, ?>> agent_Class();
}
