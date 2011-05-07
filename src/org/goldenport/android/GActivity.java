package org.goldenport.android;

import java.util.concurrent.CopyOnWriteArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListAdapter;

/**
 * @since   Apr. 28, 2011
 * @version May.  7, 2011
 * @author  ASAMI, Tomoharu
 */
public abstract class GActivity<C extends GController<?, ?, ?, ?>> extends Activity implements IGActivity {
    private CopyOnWriteArrayList<GActivityTrait> _traits = new CopyOnWriteArrayList<GActivityTrait>();
    protected GApplication gapplication;
    protected GFactory gfactory;
    protected C gcontroller;

    public GActivity() {
    }

    protected abstract Class<C> controller_Class();

    public void addTrait(GActivityTrait trait) {
        trait.setActivity(this);
        _traits.add(trait);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gapplication = (GApplication)getApplication();
        gfactory = gapplication.getFactory();
        gcontroller = gfactory.createController(controller_Class());
        gcontroller.setActivity(this);
        _traits.add(gcontroller);
        for (GActivityTrait trait: _traits) {
            trait.onCreate(savedInstanceState);
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
            if (trait.handleSetListAdapter(adapter)) {
                return;
            }
        }
        throw new RuntimeException(String.format(
                "(1)ListViewTrait is not weaved." +
                "(2)ListView does not located in the layout for %s. Check org.goldenport.android.Rid.g_list or android.R.id.list.",
                getClass().getName()));
    }
}
