package org.goldenport.android;

import java.util.concurrent.CopyOnWriteArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * @since   Apr. 28, 2011
 * @version Apr. 29, 2011
 * @author  ASAMI, Tomoharu
 */
public abstract class AbstractActivity extends Activity implements IActivity {
    private CopyOnWriteArrayList<ActivityTrait> _traits = new CopyOnWriteArrayList<ActivityTrait>();

    void addTrait(ActivityTrait trait) {
    	trait.setActivity(this);
    	_traits.add(trait);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for (ActivityTrait trait: _traits) {
            trait.onCreate(savedInstanceState);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        for (ActivityTrait trait: _traits) {
            trait.onPostCreate(savedInstanceState);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        for (ActivityTrait trait: _traits) {
            trait.onStart();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        for (ActivityTrait trait: _traits) {
            trait.onRestart();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        for (ActivityTrait trait: _traits) {
            trait.onResume();
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        for (ActivityTrait trait: _traits) {
            trait.onPostResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        for (ActivityTrait trait: _traits) {
            trait.onPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        for (ActivityTrait trait: _traits) {
            trait.onStop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (ActivityTrait trait: _traits) {
            trait.onDestroy();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        for (ActivityTrait trait: _traits) {
            trait.onSaveInstanceState(outState);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        for (ActivityTrait trait: _traits) {
            trait.onRestoreInstanceState(savedInstanceState);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (ActivityTrait trait: _traits) {
            trait.onActivityResult(requestCode, resultCode, data);
        }
    }
}
