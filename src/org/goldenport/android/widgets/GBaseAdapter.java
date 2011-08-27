package org.goldenport.android.widgets;

import java.io.IOException;
import java.util.List;

import org.goldenport.android.GContext;
import org.goldenport.android.GModel;
import org.goldenport.android.models.BeanSequenceRepository;
import org.goldenport.android.models.Record;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @since   May.  2, 2011
 * @version Aug. 26, 2011
 * @author  ASAMI, Tomoharu
 */
// XXX most functionality should move BeanSequenceAdapter.
public abstract class GBaseAdapter<T> extends BaseAdapter {
    protected final Context context;
    protected Throwable _exception;
    protected IGBaseAdapterListener adapter_listener;
    protected final float density;
    private static final int CHUNK_SIZE = 100;
    private static final int MARGIN_LENGTH = 0;
    protected final GContext gcontext;
    protected final GModel<?, ?> model;
    protected final BeanSequenceRepository<T> repository;
    private List<T> _cache_shadow;
    private int _length;
    private boolean _is_length_fixed = false;
    protected final LayoutInflater inflater;
    private boolean _filled;
    private boolean _is_updating;

    public GBaseAdapter(Context context, GModel<?, ?> model, BeanSequenceRepository<T> repo) {
        this.context = context;
        density = context.getResources().getDisplayMetrics().density;
        this.model = model;
        this.gcontext = model.gcontext;
        this.repository = repo;
        if (repository.isLengthFixed()) {
            _length = repository.getLengthHint();
            _is_length_fixed = true;
        } else {
            int initsize = CHUNK_SIZE - MARGIN_LENGTH;
            _length = Math.max(initsize, repository.getLengthHint()); 
        }
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        _filled = false;
    }

    public Throwable getException() {
        return _exception;
    }

    public void clearException() {
        _exception = null;
    }

    public void setBaseAdapterListener(IGBaseAdapterListener listener) {
        this.adapter_listener = listener;
    }

    protected final int px_mdpi(int dp) {
        return (int)(dp * density);
    }

    public void fill() {
        if (_is_updating) {
            return;
        }
        if (!_filled) {
            getItem(0);
            _filled = true;
        }
    }

    public boolean isActive() {
        return _filled;
    }

    @Override
    public int getCount() {
        fill();
        return _length;
    }

    @Override
    public T getItem(int position) {
        List<T> shadow = _cache_shadow; // MT
        if (shadow != null) {
            return shadow.get(position);
        }
        if (_is_length_fixed) {
            return get_item_length_fixed(position);
        } else {
            return get_item_length_unfixed_with_prompt(position);
        }
    }
    
    private T get_item_length_fixed(int position) {
        if (repository.getLengthHint() > position) {
            try {
                return repository.get(position).data;
            } catch (IOException e) {
                _exception = e;
                return null;
            }
        } else {
            return null;
        }
    }

    private T get_item_length_unfixed_with_prompt(int position) {
        if (repository.getLengthHint() - MARGIN_LENGTH > position) {
            try {
                if (repository.isLengthFixed()) {
                    _is_length_fixed = true;
                    _length = _calc_length(repository.getLengthHint());
                }
                return repository.get(position).data;
            } catch (IOException e) {
                _exception = e;
                return null;
            }
        } else {
            Record<T> pair;
            try {
                pair = repository.get(position);
            } catch (IOException e) {
                pair = null;
                _exception = e;
                return null;
            }
            if (pair == null) {
                _length = _calc_length(repository.getLengthHint());
                _is_length_fixed = true;
                return null;
            }
            T r = pair.data;
            if (pair.isComplete) {
                _is_length_fixed = true;
                _length = _calc_length(pair.totalLength);
            } else {
                _length = pair.totalLength - MARGIN_LENGTH;
            }
            if (r == null) {
                return null;
            } else {
                return r;
            }
        }
    }

    private int _calc_length(int length) {
        return length - MARGIN_LENGTH;
    }

    protected boolean is_Always_Show_Footer() {
        return false;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public final View getView(int position, View convertView, ViewGroup parent) {
        if (_is_length_fixed) {
            return get_data_view(position, convertView, parent);
        } else {
            return get_data_view(position, convertView, parent);
        }
    }

    private View get_data_view(int position, View convertView, ViewGroup parent) {
        T item = getItem(position);
        if (item == null) {
            return new TextView(context);
        }
        View view = make_View(item, position, convertView, parent);
        if (view == null) {
            return new TextView(context);
        } else if (view instanceof TextView) {
            return view;
//        } else if (view instanceof LinearLayout && XXX)
//        well setted up LinearLayout can be used immediately.            
        } else {
            LinearLayout ll = new LinearLayout(context);
            ll.addView(view);
            return ll;
        }
    }

    protected abstract View make_View(T item, int position, View convertView, ViewGroup parent);
}
