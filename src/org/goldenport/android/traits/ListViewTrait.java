package org.goldenport.android.traits;

import org.goldenport.android.GActivityTrait;
import org.goldenport.android.R;

import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * @since   Apr. 28, 2011
 * @version Aug. 14, 2011
 * @author  ASAMI, Tomoharu
 */
public class ListViewTrait extends GActivityTrait {
    private ListView _list_view;

    @Override public void setContentView() {
        _list_view = (ListView)findViewById(R.id.g_list);
        if (_list_view == null) {
            _list_view = (ListView)findViewById(android.R.id.list);
        }
        if (_list_view == null) {
            throw new RuntimeException("(1)Missing R.id.g_list and android.R.id.list, (2)invoke addTrait after setContentView.");
        }
    }

    @Override
    public boolean setListAdapter(ListAdapter adapter) {
        if (_list_view == null) {
            throw new RuntimeException("(1)Missing R.id.g_list and android.R.id.list, (2)invoke addTrait after setContentView.");
        }
        _list_view.setAdapter(adapter);
        return true;
    }
}
