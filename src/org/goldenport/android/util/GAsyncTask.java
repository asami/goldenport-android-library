package org.goldenport.android.util;

import org.goldenport.android.GContext;

import android.os.AsyncTask;
import android.os.Message;

/**
 * @since   Mar.  4, 2011
 * @version Aug. 12, 2011
 * @author  ASAMI, Tomoharu
 */
public abstract class GAsyncTask<Param, Result> extends AsyncTask<Param, Integer, Message> {
    protected final GContext gcontext;

    public GAsyncTask(GContext gcontext) {
        this.gcontext = gcontext;
    }
    
    @Override
    protected final Message doInBackground(Param... params) {
        Message msg = Message.obtain();
        try {
            Result result = do_In_Background(params);
            msg.obj = result;
        } catch (Throwable e) {
            msg.obj = e;
        }
        return msg;
    }

    protected abstract Result do_In_Background(Param[] params) throws Throwable;

    @SuppressWarnings("unchecked")
    @Override
    protected final void onPostExecute(Message result) {
        try {
            on_Post_Execute_prologue(result);
            if (result.obj instanceof Throwable) {
                on_Post_Exception((Throwable)result.obj);
            } else {
                on_Post_Execute((Result)result.obj);
            }
        } catch (Throwable e) {
            log_warn("GAsyncTask#onPostExecute(" + result.obj + ")", e);
        }
    }

    protected void on_Post_Execute_prologue(Message result) {
    }

    protected abstract void on_Post_Execute(Result result);
    protected abstract void on_Post_Exception(Throwable e);

    protected void log_warn(String message, Throwable e) {
        gcontext.logWarn(message, e);
    }
}
