package org.goldenport.android.controllers;

import org.goldenport.android.GAgent;
import org.goldenport.android.GContext;
import org.goldenport.android.GController;
import org.goldenport.android.GErrorModel;
import org.goldenport.android.GModel;

/**
 * @since   May.  1, 2011
 * @version May.  1, 2011
 * @author  ASAMI, Tomoharu
 */
public class NullController extends GController<GContext,
                                                GErrorModel<GContext>,
                                                GModel<GContext, GErrorModel<GContext>>,
                                                GAgent<GContext, GErrorModel<GContext>,
                                                       GModel<GContext, GErrorModel<GContext>>>> {
}
