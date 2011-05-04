package org.goldenport.android.models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.goldenport.android.util.InvocationTargetIOException;
import org.goldenport.android.util.LooseArrayList;
import com.google.common.base.Predicate;

import android.location.Location;

// TimelineRepository
// RecordSequenceRepository
// BaseSequenceRepository
/**
 * @since   May.  3, 2011
 * @version May.  4, 2011
 * @author  ASAMI, Tomoharu
 */
public abstract class BeanSequenceRepository<DATA> {
    private static final int DEFAULT_CHUNK_SIZE = 100;
    private static final int MAX_DATA_SIZE = 1000;
    private static final long TTL = 1000 * 60 * 10; // 10 minutes
    private final long _timestamp = System.currentTimeMillis();
    private final List<DATA> _seq = new LooseArrayList<DATA>();
    private final int _chunk_size;
    private boolean _is_complete = false;
    public final Map<String, Object> properties = new HashMap<String, Object>();
    public Location location;
    private boolean _now_loading = false;
    private List<BeanSequenceRepositoryListener> _agents = new CopyOnWriteArrayList<BeanSequenceRepositoryListener>();
    private Throwable _exception = null;

    public BeanSequenceRepository() {
        this(DEFAULT_CHUNK_SIZE);
    }

    public BeanSequenceRepository(int chunkSize) {
        _chunk_size = chunkSize;
    }

    /**
     * 
     * @param start
     * @param count
     * @return data list. In case of using indicator, List size would be greater than count.
     * @throws IOException
     */
    protected abstract List<DATA> load_Data(int start, int count) throws IOException;

    public Record<DATA> get(int position) throws IOException {
        _exception = null;
        try {
            if (!_is_complete) {
                if (_seq.size() <= position) {
                    int rsize = calc_rsise(position);
                    _now_loading = true;
                    List<DATA> data = load_Data(_seq.size(), rsize);
                    _now_loading = false;
                    _seq.addAll(data);
                    if (data.size() < rsize || _seq.size() >= MAX_DATA_SIZE) {
                        _is_complete  = true;
                    }
                }
            }
            if (_seq.size() <= position) {
                _post_complete();
                return null;
            }
            Record<DATA> r = new Record<DATA>(_seq.get(position), _seq.size(), _is_complete);
            _post_complete();
            return r;
        } catch (IOException e) {
            _post_error(e);
            throw e;
        } catch (Throwable e) {
            _post_error(e);
            throw new InvocationTargetIOException(e);
        } finally {
        }
    }

    private void _post_complete() {
        synchronized (this) {
            for (BeanSequenceRepositoryListener p: _agents) {
                p.onComplete();
            }
            _agents.clear();
        }
    }

    private void _post_error(Throwable e) {
        _exception = e;
        synchronized (this) {
            for (BeanSequenceRepositoryListener p: _agents) {
                p.onError(e);
            }
            _agents.clear();
            _now_loading = false;
        }
    }

    public boolean execute(BeanSequenceRepositoryListener p) {
        if (_now_loading) {
            p.onWaiting();
            synchronized (this) {
                _agents.add(p);
            }
            return false;
        } else {
            p.onNoWait();
            return true;
        }
    }

    private int calc_rsise(int position) {
        int read_end = ((position / _chunk_size) * _chunk_size) + _chunk_size;
        read_end = Math.min(read_end, MAX_DATA_SIZE);
        return read_end - _seq.size();
    }

    public int getLengthHint() {
        return _seq.size();
    }

    public Integer getLength() {
        if (_is_complete) {
            return _seq.size();
        } else {
            return null;
        }
    }

    public boolean isLengthFixed() {
        return _is_complete;        
    }

    public boolean isAvailable() {
        if (_exception != null) {
            return false;
        }
        if (TTL == -1) {
            return true;
        } else {
            return _timestamp + TTL > System.currentTimeMillis();
        }
    }
    
    public boolean isActive() {
        return isAvailable() && (getLength() != null || _seq.size() >= _chunk_size);
    }

    public synchronized void remove(Predicate<DATA> predicate) {
        List<DATA> result = new ArrayList<DATA>();
        for (DATA d: _seq) {
            if (!predicate.apply(d)) {
                result.add(d);
            }
        }
        if (result.size() != _seq.size()) {
            _seq.clear();
            _seq.addAll(result);
        }
    }

    public void clear() {
        _seq.clear();
        _is_complete = false;
    }

    public List<DATA> getShadow() {
        return new ArrayList<DATA>(_seq);
    }

    public boolean isException() {
        return _exception != null;
    }
}
