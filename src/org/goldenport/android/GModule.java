package org.goldenport.android;

import org.goldenport.android.controllers.NullController;

import android.content.Context;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

/**
 * @since   Apr. 30, 2011
 * @version May. 14, 2011
 * @author  ASAMI, Tomoharu
 */
public abstract class GModule extends AbstractModule {
    protected final Context context;
    protected final GContext gcontext;

    public GModule(GContext context) {
        this(context.getContext(), context);
    }

    public GModule(Context context, GContext gcontext) {
        this.context = context;
        this.gcontext = gcontext; 
    }

    @Override
    protected void configure() {
        configure_context();
/*
        bind(GErrorModel.class).to(GErrorModel.class).in(Singleton.class);
        bind(GModel.class).to(GModel.class).in(Singleton.class);
        bind(GAgent.class).to(GAgent.class).in(Singleton.class);
        bind(GController.class).to(GController.class);
*/
    }

    protected final void configure_context() {
        bind(Context.class).toInstance(context);
        bind(GContext.class).toInstance(gcontext);
    }
/*
    protected abstract Class<? extends GContext> context_Class();
    protected abstract Class<? extends GErrorModel<?>> errormodel_Class();
    protected abstract Class<? extends GModel<?, ?>> model_Class();
    protected abstract Class<? extends GAgent<?, ?, ?>> agent_Class();
    protected abstract Class<? extends GController<?, ?, ?, ?>> controller_Class();
*/
    @Provides
    public NullController provideNullController() {
        return NullController.get();
    }
}
