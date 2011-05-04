package org.goldenport.android.widgets;

import org.goldenport.android.GModel;
import org.goldenport.android.models.BeanSequenceRepository;

import android.content.Context;


/**
 * @since   May.  3, 2011
 * @version May.  4, 2011
 * @author  ASAMI, Tomoharu
 */
public abstract class BeanSequenceAdapter<T> extends GBaseAdapter<T> {
    public BeanSequenceAdapter(Context context, GModel<?, ?> model, BeanSequenceRepository<T> repo) {
        super(context, model, repo);
    }
}
