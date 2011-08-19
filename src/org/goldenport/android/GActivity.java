package org.goldenport.android;

import java.lang.reflect.Field;
import java.util.concurrent.CopyOnWriteArrayList;

import org.goldenport.android.util.GAsyncTask;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ListAdapter;

/**
 * @since   Apr. 28, 2011
 * @version Aug. 13, 2011
 * @author  ASAMI, Tomoharu
 */
public abstract class GActivity<C extends GController<?, ?, ?, ?>> extends Activity implements IGActivity {
    private CopyOnWriteArrayList<GActivityTrait> _traits = new CopyOnWriteArrayList<GActivityTrait>();
    protected GApplication gapplication;
    protected GContext gcontext;
    protected GFactory gfactory;
    protected C gcontroller;

    public GActivity() {
    }

    protected Class<C> controller_Class() {
        return null;
    }

    public void addTrait(GActivityTrait trait) {
        trait.setActivity(this);
        _traits.add(trait);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gapplication = (GApplication)getApplication();
        gfactory = gapplication.getFactory();
        Class<C> cc = controller_Class();
        gcontroller = gfactory.createController(cc);
        gcontroller.setActivity(this);
        _traits.add(gcontroller);
        for (GActivityTrait trait: _traits) {
            trait.onCreate(savedInstanceState);
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        _inject_views();
        for (GActivityTrait trait: _traits) {
            trait.setContentView();
        }
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        _inject_views();
        for (GActivityTrait trait: _traits) {
            trait.setContentView();
        }
    }

    @Override
    public void setContentView(View view, LayoutParams params) {
        super.setContentView(view, params);
        _inject_views();
        for (GActivityTrait trait: _traits) {
            trait.setContentView();
        }
    }

    /*
    @Override
    public void addContentView(View view, LayoutParams params) {
        super.addContentView(view, params);
        _inject_views();
    }
*/
    private void _inject_views() {
        for (Field f: getClass().getDeclaredFields()) {
            if (f.isAnnotationPresent(LayoutView.class)) {
                _inject_view(f);
            } else if (f.isAnnotationPresent(ResourceString.class)) {
                _inject_string(f);
            } else if (f.isAnnotationPresent(ResourceDrawable.class)) {
                _inject_drawable(f);
            } else if (f.isAnnotationPresent(IntentExtra.class)) {
                _inject_extra(f);
            }
        }
    }

    private void _inject_view(Field f) {
        LayoutView a = f.getAnnotation(LayoutView.class);
        int id = a.value();
        Object view = gfactory.createView((Class<View>)f.getType());
        if (view == null) {
            view = findViewById(id);
        }
        try {
            f.setAccessible(true);
            f.set(this, view);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void _inject_string(Field f) {
        ResourceString a = f.getAnnotation(ResourceString.class);
        int id = a.value();
        String string = getString(id);
        Object value = _convert(f.getType(), string);
        try {
            f.setAccessible(true);
            f.set(this, value);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void _inject_drawable(Field f) {
        ResourceDrawable a = f.getAnnotation(ResourceDrawable.class);
        int id = a.value();
        Drawable drawable = getResources().getDrawable(id);
        try {
            f.setAccessible(true);
            f.set(this, drawable);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void _inject_extra(Field f) {
        IntentExtra a = f.getAnnotation(IntentExtra.class);
        String key = a.value();
        Intent intent = getIntent();
        Object value = intent.getExtras().get(key);
        try {
            f.setAccessible(true);
            f.set(this, value);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private Object _convert(Class<?> type, String string) {
        if (type == boolean.class) {
            return Boolean.parseBoolean(string);
        } else if (type == byte.class) {
            return Byte.parseByte(string);
        } else if (type == short.class) {
            return Short.parseShort(string);
        } else if (type == int.class) {
            return Integer.parseInt(string);
        } else if (type == long.class) {
            return Long.parseLong(string);
        } else if (type == float.class) {
            return Float.parseFloat(string);
        } else if (type == double.class) {
            return Double.parseDouble(string);
        } else if (type == Boolean.class) {
            return Boolean.parseBoolean(string);
        } else if (type == Byte.class) {
            return Byte.parseByte(string);
        } else if (type == Short.class) {
            return Short.parseShort(string);
        } else if (type == Integer.class) {
            return Integer.parseInt(string);
        } else if (type == Long.class) {
            return Long.parseLong(string);
        } else if (type == Float.class) {
            return Float.parseFloat(string);
        } else if (type == Double.class) {
            return Double.parseDouble(string);
        } else if (type == String.class) {
            return string;
        } else {
            return string;
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        for (GActivityTrait trait: _traits) {
            trait.onPostCreate(savedInstanceState);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        for (GActivityTrait trait: _traits) {
            trait.onStart();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        for (GActivityTrait trait: _traits) {
            trait.onRestart();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        for (GActivityTrait trait: _traits) {
            trait.onResume();
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        for (GActivityTrait trait: _traits) {
            trait.onPostResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        for (GActivityTrait trait: _traits) {
            trait.onPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        for (GActivityTrait trait: _traits) {
            trait.onStop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (GActivityTrait trait: _traits) {
            trait.onDestroy();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        for (GActivityTrait trait: _traits) {
            trait.onSaveInstanceState(outState);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        for (GActivityTrait trait: _traits) {
            trait.onRestoreInstanceState(savedInstanceState);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (GActivityTrait trait: _traits) {
            trait.onActivityResult(requestCode, resultCode, data);
        }
    }

    protected final void set_list_adapter(ListAdapter adapter) {
        // this method can be used after onCreate.
        for (GActivityTrait trait: _traits) {
            if (trait.setListAdapter(adapter)) {
                return;
            }
        }
        throw new RuntimeException(String.format(
                "(1)ListViewTrait is not weaved." +
                "(2)ListView does not located in the layout for %s. Check org.goldenport.android.Rid.g_list or android.R.id.list.",
                getClass().getName()));
    }

    protected final void update_view() {
        if (!update_view_fg()) {
            update_view_bg();
        }
    }

    protected boolean update_view_fg() {
        if (!update_view_fg_Do()) {
            return false;
        }
        for (GActivityTrait trait: _traits) {
            if (!trait.updateViewFgDo()) {
                return false;
            }
        }
        return true;
    }

    private void update_view_bg() {
        new GAsyncTask<Void, Object[]>(gcontext) {
            @Override
            protected void onPreExecute() {
                Object[] result = new Object[_traits.size() + 1];
                result[0] = update_view_bg_Do();
                int i = 1;
                for (GActivityTrait trait: _traits) {
                    result[i] = trait.updateViewBgDo();
                }
            }

            @Override
            protected Object[] do_In_Background(Void[] params) throws Throwable {
                Object[] result = new Object[_traits.size() + 1];
                try {
                    result[0] = update_view_bg_Do();
                } catch (Throwable e) {
                    result[0] = e;
                }
                int i = 1;
                for (GActivityTrait trait: _traits) {
                    try {
                        result[i] = trait.updateViewBgDo();
                    } catch (Throwable e){
                        result[i] = e;
                    }
                }
                return result;
            }

            @Override
            protected void on_Post_Execute(Object[] result) {
                if (result[0] instanceof Throwable) {
                    update_view_bg_Exception((Throwable)result[0]);
                } else {
                    update_view_bg_Epilogue(result[0]);
                }
                int i = 1;
                for (GActivityTrait trait: _traits) {
                    if (result[i] instanceof Throwable) {
                        trait.updateViewBgException((Throwable)result[i]);
                    } else {
                        trait.updateViewBgEpilogue(result[i]);
                    }
                }
            }

            @Override
            protected void on_Post_Exception(Throwable e) {
                update_view_bg_Exception(e);
                for (GActivityTrait trait: _traits) {
                    trait.updateViewBgException(e);
                }
            }
        };
    }

    protected boolean update_view_fg_Do() {
        return true;
    }

    protected Object update_view_bg_Do() {
        return null;
    }

    protected void update_view_bg_Exception(Throwable throwable) {
    }

    private void update_view_bg_Epilogue(Object object) {
    }
}
