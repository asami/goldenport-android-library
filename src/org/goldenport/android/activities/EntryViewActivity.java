package org.goldenport.android.activities;

import org.goldenport.android.GActivity;
import org.goldenport.android.GController;
import org.goldenport.android.IntentExtra;
import org.goldenport.android.LayoutView;
import org.goldenport.android.R;
import org.goldenport.android.feed.GAEntry;
import org.goldenport.android.widgets.EntryView;

/**
 * @since   Sep.  3, 2011
 * @version Sep.  4, 2011
 * @author  ASAMI, Tomoharu
 */
public class EntryViewActivity extends GActivity<GController<?, ?, ?, ?>> {
    @IntentExtra(INTENT_EXTRA_ENTRY)
    private GAEntry _entry;
    @LayoutView(R.id.g_entry)
    private EntryView _entry_view;

    @Override
    protected int get_Layout_id() {
        return R.layout.entryviewactivity;
    }

    @Override
    protected void onStart() {
        super.onStart();
        _entry_view.setEntry(_entry);
    }
}
