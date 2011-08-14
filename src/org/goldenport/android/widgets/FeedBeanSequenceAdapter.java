package org.goldenport.android.widgets;

import org.goldenport.android.GModel;
import org.goldenport.android.feed.GAEntry;
import org.goldenport.android.models.BeanSequenceRepository;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;


/**
 * @since   Aug. 13, 2011
 * @version Aug. 13, 2011
 * @author  ASAMI, Tomoharu
 */
public class FeedBeanSequenceAdapter extends BeanSequenceAdapter<GAEntry> {
    public FeedBeanSequenceAdapter(Context context, GModel<?, ?> model, BeanSequenceRepository<GAEntry> repo) {
        super(context, model, repo);
    }

    @Override
    protected View make_View(GAEntry item, int position, View convertView,
            ViewGroup parent) {
        EntryView view;
        if (convertView instanceof EntryView) {
            view = (EntryView)convertView;
            view.setEntry(item);
        } else {
            view = new EntryView(context);
            view.setEntry(item);
        }
        return view;
    }
}
