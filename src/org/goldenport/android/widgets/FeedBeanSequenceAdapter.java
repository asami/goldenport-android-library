package org.goldenport.android.widgets;

import org.goldenport.android.GModel;
import org.goldenport.android.activities.EntryViewActivity;
import org.goldenport.android.feed.GAEntry;
import org.goldenport.android.models.BeanSequenceRepository;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;


/**
 * @since   Aug. 13, 2011
 * @version Sep.  3, 2011
 * @author  ASAMI, Tomoharu
 */
public class FeedBeanSequenceAdapter extends BeanSequenceAdapter<GAEntry> {
    private Activity _activity = null;
    private int _request_code = 0;

    public FeedBeanSequenceAdapter(Context context, GModel<?, ?> model, BeanSequenceRepository<GAEntry> repo) {
        super(context, model, repo);
    }

    public void setActivity(Activity activity) {
        _activity = activity;
    }

    public void setRequestCode(int code) {
        _request_code = code;
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
        view.setOnClickListener(callback_click(item));
        return view;
    }

    protected OnClickListener callback_click(final GAEntry item) {
        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                command_Click(v, item);
            }
        };
    }

    protected void command_Click(View v, GAEntry item) {
        if (_activity != null) {
            Intent intent = new Intent(_activity, get_Target_Activity());
            intent.putExtra(INTENT_EXTRA_ENTRY, item);
            _activity.startActivityForResult(intent, _request_code);
        } else {
            Intent intent = new Intent(context, get_Target_Activity());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(INTENT_EXTRA_ENTRY, item);
            context.startActivity(intent);
        }
    }

    private Class<?> get_Target_Activity() {
        return EntryViewActivity.class;
    }
}
