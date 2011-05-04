package org.goldenport.android.widgets;

import java.io.IOException;
import java.util.List;

import org.goldenport.android.GContext;
import org.goldenport.android.GModel;
import org.goldenport.android.models.BeanSequenceRepository;
import org.goldenport.android.models.BeanSequenceRepositoryListener;
import org.goldenport.android.models.Record;
import org.goldenport.android.util.GAsyncTask;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @since   May.  2, 2011
 * @version May.  4, 2011
 * @author  ASAMI, Tomoharu
 */
public abstract class GBaseAdapter<T> extends BaseAdapter implements OnScrollListener {
    protected final Context context;
    protected Throwable _exception;
    protected IGBaseAdapterListener adapter_listener;
    protected final float density;
    private static final int CHUNK_SIZE = 100;
    private static final int MARGIN_LENGTH = 10;
    private static final int MORE_READ_LENGHT = 1;
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
            int initsize = CHUNK_SIZE - MARGIN_LENGTH + MORE_READ_LENGHT
                           + get_Header_Item_Number() + get_Footer_Item_Number();
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

    // OnScrollListener
    private int _scroll_state = 0; //
    private int _max_visible_item_count = 0;
    @SuppressWarnings("unused")
    private int _latest_visible_item_count = 0;
    private int _first_visible_item = -1;
    private boolean _in_update_position = false;
    protected UpdateView _update_header = null;
    @SuppressWarnings("unused")
    private LinearLayout _update_header_holder;
    protected UpdateView _update_footer;

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
            int visibleItemCount, int totalItemCount) {
        _first_visible_item  = firstVisibleItem;
        switch (_scroll_state) {
        case SCROLL_STATE_IDLE:
            break;
        case SCROLL_STATE_TOUCH_SCROLL:
            _max_visible_item_count = Math.max(_max_visible_item_count, visibleItemCount);
            _latest_visible_item_count = visibleItemCount;
            if (_is_in_update_position()) {
                if (!_in_update_position) {
                    header_Selecting();
                    _in_update_position = true;
                }
            } else {
                if (_in_update_position) {
                    header_Unselected();
                }
                _in_update_position = false;
            }
            break;
        case SCROLL_STATE_FLING:
            break;
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
        case SCROLL_STATE_IDLE:
            if (_is_in_update_position()) {
                if (_scroll_state == SCROLL_STATE_FLING) {
                    header_Neutral();
                } else {
                    if (!is_Header_In_Action()) {
                        header_Selected();
                    }
                }
            } else if (_is_in_header_position()) {
                header_Neutral();
            }
            break;
        case SCROLL_STATE_TOUCH_SCROLL:
            break;
        case SCROLL_STATE_FLING:
            break;
        }
        _scroll_state = scrollState;
        _max_visible_item_count = 0;
        _latest_visible_item_count = 0;
    }

    private boolean _is_in_update_position() {
        if (get_Header_Item_Number() == 2) {
            return _first_visible_item == 0;
        } else {
            return false;
        }
    }

    private boolean _is_in_header_position() {
        return _first_visible_item < get_Header_Item_Number();
    }

    protected int get_Header_Item_Number() {
        /*
         * Pull更新を使う場合は2、使わない場合は0にする。
         */
//        return 2;
        return 0;
    }

    protected int get_Footer_Item_Number() {
        return 0;
    }

    protected View make_Header_Item(int index) {
        switch (index) {
        case 0:
            if (_update_header == null) {
                _update_header = new UpdateView(context);
            } else {
                _update_header.initState();
            }
            return _update_header;
        default:
            return new TextView(context);
        }
    }

/*
    protected View make_Prologue_Item0() {
        TextView v = new TextView(_context);
        v.setText("OK");
        return v;
    }

    protected View make_Prologue_Item1() {
        if (_update_header_holder == null) {
            if (_update_header == null) {
                _update_header = new ListViewUpdateHeaderPlate(_context);
            } else {
                _update_header.initState();
            }
            _update_header_holder = new LinearLayout(_context);
            _update_header_holder.setLayoutParams(new ListView.LayoutParams(ListView.LayoutParams.FILL_PARENT, ListView.LayoutParams.WRAP_CONTENT));
            _update_header_holder.addView(_update_header);
        }
        return _update_header_holder;
    }
*/
    protected View make_Footer_Item() {
        return null;
    }

    protected final int px_mdpi(int dp) {
        return (int)(dp * density);
    }

    public void fill() {
        if (_is_updating) {
            return;
        }
        if (!_filled) {
            getItem(0 + get_Header_Item_Number()); // fill cache head chunk
            _filled = true;
        }
    }

    @Override
    public int getCount() {
        fill();
        return _length;
    }

    @Override
    public T getItem(int position) {
        int index = position - get_Header_Item_Number();
        if (index < 0) {
            return null;
        }
        List<T> shadow = _cache_shadow; // MT
        if (shadow != null) {
            return shadow.get(index);
        }
        if (_is_length_fixed) {
            return get_item_length_fixed(index);
        } else {
            return get_item_length_unfixed_with_prompt(index);
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
                if (pair.totalLength == 0) {
                    if (is_Always_Show_Footer()) {
                        _length = get_Footer_Item_Number();
                    } else {
                        _length = 0;
                    }
                } else {
                    _length = _calc_length(pair.totalLength);
                }
            } else {
                _length = pair.totalLength - MARGIN_LENGTH + get_Header_Item_Number() + 1 + get_Footer_Item_Number();
            }
            if (r == null) {
                return null;
            } else {
                return r;
            }
        }
    }

 /* 仕様変更で使うかもしれない。    
    private T get_item_length_unfixed_implicit_read(int position) {
        if (_data.size() - MARGIN_LENGH > position) {
            return _data.get(position);
        } else {
            Record<T> pair;
            try {
                if (position != 0) {
                    get_Data(position + MARGIN_LENGH); // read ahead
                }
                pair = get_Data(position);
            } catch (IOException e) {
                pair = null;
                _exception = e;
            }
            if (pair == null) {
                _length = position;
                return null;
            }
            T r = pair.data;
            if (pair.isComplete) {
                _length = pair.totalLength;
            } else {
                _length = pair.totalLength - MARGIN_LENGH + 1;
            }
            if (r == null) {
                return null;
            } else {
                if (_data.size() == position) {
                    _data.add(r);
                } else if (_data.size() < position) {
                    for (int i = _data.size();i < position - 1;i++) {
                        _data.add(null);
                    }
                    _data.add(r);
                } else {
                    _data.set(position, r);
                }
                return r;
            }
        }
    }

    protected final Record<T> get_Data(int position) throws IOException {
        return repository.get(position);
    }
*/

    protected boolean is_Always_Show_Footer() {
        return false;
    }

    private int _calc_length(int totalLength) {
        if (totalLength == 0) {
            if (is_Always_Show_Footer()) {
                return get_Header_Item_Number() + get_Footer_Item_Number();
            } else {
                return 0;
            }
        } else {
            return totalLength + get_Header_Item_Number() + get_Footer_Item_Number();
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public final View getView(int position, View convertView, ViewGroup parent) {
        if (position < get_Header_Item_Number()) {
            return make_Header_Item(position);
        } else if (_is_length_fixed) {
            if (position == _length - get_Footer_Item_Number()) {
                return make_Footer_Item();
            } else {
                return get_data_view(position, convertView, parent);
            }
        } else {
            if (position == _length - 1 - get_Footer_Item_Number()) {
                return make_more_read_item();
            } else if (position == _length - get_Footer_Item_Number()) { // in case of 0, don't route
                return make_Footer_Item();
            } else {
                return get_data_view(position, convertView, parent);
            }
        }
    }

    private View make_more_read_item() {
        _update_footer = new UpdateView(context);
        if (_set_more_read_item_waiting()) {
            _set_more_read_item_button();
        }
        return _update_footer;
    }

    private boolean _set_more_read_item_waiting() {
        return repository.execute(new BeanSequenceRepositoryListener() {
            @Override
            public void onNoWait() {
            }

            @Override
            public void onWaiting() {
                _more_read_item_start_waiting();
            }
            
            @Override
            public void onComplete() {
                _more_read_item_finish_waiting(null);
            }
            
            @Override
            public void onError(Throwable e) {
                _more_read_item_finish_waiting(e);
            }
        });
    }

    private void _set_more_read_item_button() {
        _update_footer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                _execute_more_read_item();
            }
        });
    }

    private void _execute_more_read_item() {
        _more_read_item_start_waiting();
        new GAsyncTask<Void, Void>(gcontext) {
            @Override
            protected Void do_In_Background(Void[] params)
                    throws Throwable {
                if (model.isDebugMoreReadError()) {
                    throw new IOException("Emulate in More Read");
                }
                get_item_length_unfixed_with_prompt(repository.getLengthHint() + MARGIN_LENGTH);
                System.gc();
                return null;
            }

            @Override
            protected void on_Post_Execute(Void result) {
                if (_exception != null) {
                    _more_read_item_finish_waiting(_exception);
                } else {
                    _more_read_item_finish_waiting(null);
                }
            }

            @Override
            protected void on_Post_Exception(Throwable e) {
                _more_read_item_finish_waiting(e);
            }
        }.execute();
    }

    private void _more_read_item_start_waiting() {
        _update_footer.progressState();
    }

    private void _more_read_item_finish_waiting(Throwable e) {
        remove_control_items();
        if (adapter_listener != null) {
            if (e != null) {
                adapter_listener.finishLoadingException(GBaseAdapter.this, e, new Runnable() {
                    @Override
                    public void run() {
                        _execute_more_read_item();                        
                    }
                });
            } else {
                adapter_listener.finishLoading(GBaseAdapter.this);
            }
        }
    }

    protected boolean is_Header_In_Action() {
        return _is_updating;
    }

    protected void header_Selected() {
        if (_update_header == null) return;
        _update_header.progressState();
        adapter_listener.updating(this);
        synchronized (this) {
            if (_is_updating) return;
            _is_updating = true;
            _cache_shadow = repository.getShadow();
        }
        new GAsyncTask<Void, Void>(gcontext) {
            @Override
            protected void onPreExecute() {
            }

            @Override
            protected Void do_In_Background(Void[] params) throws Throwable {
                try {
                    Thread.sleep(500); // 強制的に更新ボタンを表示するために一旦スリープさせる。
                } catch (InterruptedException e) {
                }
                _is_length_fixed = false;
                _filled = false;
                repository.clear();
                fill();
                System.gc();
                return null;
            }

            @Override
            protected void on_Post_Execute(Void result) {
                synchronized (GBaseAdapter.this) {
                    _cache_shadow = null;
                    _is_updating = false;
                }
                adapter_listener.refreshed(GBaseAdapter.this);
            }

            @Override
            protected void on_Post_Exception(Throwable e) {
            }
        }.execute();
    }

    protected void header_Selecting() {
        if (_update_header == null) return;
        _update_header.trigerState();
    }

    protected void header_Unselected() {
        if (_update_header == null) return;
        _update_header.initState();
    }

    protected void header_Neutral() {
        adapter_listener.refreshed(this);
    }

    private void remove_control_items() {
        notifyDataSetChanged();
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
        } else {
            LinearLayout ll = new LinearLayout(context);
            ll.addView(view);
            return ll;
        }
    }

    protected abstract View make_View(T item, int position, View convertView, ViewGroup parent);

    public int getTopPosition() {
        // キャッシュヒットによる更新時に表示が先頭に戻る現象を回避するため、
        // デフォルトを-1にする試み。(この方法だけでは回避できなかった。)
        // Pull更新実現時は考慮が必要。
//        return super.getTopPosition();
        return get_Header_Item_Number();
    }

    public int getUpdatingPosition() {
        int n = get_Header_Item_Number();
        switch (n) {
        case 0: return 0;
        case 1: return 0;
        default: return n - 1;
        }
    }

    public void setCurrentPosition(int position) {
        repository.properties.put("list_position", new Integer(position));
    }

    public int getCurrentPosition() {
        Integer value = (Integer)repository.properties.get("list_position");
        if (value == null) {
            return getTopPosition();
        } else {
            return value.intValue();
        }
    }
}
