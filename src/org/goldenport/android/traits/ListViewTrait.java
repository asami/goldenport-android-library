package org.goldenport.android.traits;

import org.goldenport.android.GActivityTrait;
import org.goldenport.android.R;
import org.goldenport.android.widgets.GBaseAdapter;
import org.goldenport.android.widgets.MoreReadView;
import org.goldenport.android.widgets.UpdateView;

import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * @since   Apr. 28, 2011
 * @version Aug. 26, 2011
 * @author  ASAMI, Tomoharu
 */
public class ListViewTrait extends GActivityTrait {
    private ListView _list_view;
    private ListAdapter _list_adapter;
    private GBaseAdapter<?> _g_adapter;
    private UpdateView _header;
    private MoreReadView _footer;

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
        if (adapter == null) {
            throw new IllegalArgumentException("Null ListAdapter");
        }
        if (_list_view == null) {
            throw new RuntimeException("(1)Missing R.id.g_list and android.R.id.list, (2)invoke addTrait after setContentView.");
        }
        _list_adapter = adapter;
        if (adapter instanceof GBaseAdapter<?>) {
            _g_adapter = (GBaseAdapter<?>)adapter;
        }
        return true;
    }

    @Override
    public boolean updateViewFgDo() {
        if (_g_adapter != null) {
            if (_g_adapter.isActive()) {
                _list_view.setAdapter(_g_adapter);
                return true;
            } else {
                return false;
            }
        } else {
            _list_view.setAdapter(_list_adapter);
            return true;
        }
    }

    @Override
    public Object updateViewBgDo() {
        if (_g_adapter != null) {
            _g_adapter.fill();
        }
        return null;
    }

    @Override
    public void updateViewBgEpilogue(Object object) {
        _list_view.setAdapter(_list_adapter);
    }

    @Override
    public void updateViewBgException(Throwable throwable) {
        super.updateViewBgException(throwable);
    }
}
