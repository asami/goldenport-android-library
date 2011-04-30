package org.goldenport.android;

import com.google.inject.AbstractModule;

/**
 * @since   Apr. 30, 2011
 * @version Apr. 30, 2011
 * @author  ASAMI, Tomoharu
 */
public abstract class GModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(GContext.class).to(context_Class());
        bind(GModel.class).to(model_Class());
        bind(GController.class).to(controller_Class());
        bind(GErrorModel.class).to(errormodel_Class());
    }

    protected Class<? extends GErrorModel> errormodel_Class() {
        return GErrorModel.class;
    }

    protected abstract Class<? extends GContext> context_Class();
    protected abstract Class<? extends GModel> model_Class();
    protected abstract Class<? extends GController> controller_Class();
}
