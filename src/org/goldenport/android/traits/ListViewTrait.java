package org.goldenport.android.traits;

import org.goldenport.android.GActivityTrait;
import org.goldenport.android.R;

import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * @since   Apr. 28, 2011
 * @version May.  7, 2011
 * @author  ASAMI, Tomoharu
 */
public class ListViewTrait extends GActivityTrait {
    private ListView _list_view;

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        _list_view = (ListView)findViewById(R.id.g_list);
        if (_list_view == null) {
            _list_view = (ListView)findViewById(android.R.id.list);
        }
    }

    @Override
    public boolean handleSetListAdapter(ListAdapter adapter) {
        _list_view.setAdapter(adapter);
        return true;
    }
}
