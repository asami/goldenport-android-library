package org.goldenport.android.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Message;
import android.util.Log;

/**
 * @since   Jan. 14, 2010
 * @version Apr. 29, 2011
 * @author  ASAMI, Tomoharu
 */
public abstract class SpinnerDialogAsyncTask<Param, Result> extends AsyncTask<Param, Void, Message> {
    protected final Context context;
    private final String _message;
    private ProgressDialog _dialog;

    public SpinnerDialogAsyncTask(Context context, String message) {
        this.context = context;
        _message = message;
    }
    
    @Override
    protected final void onPreExecute() {
        on_Pre_Execute();
        _dialog = new ProgressDialog(context);
        _dialog.setMessage(_message);
        _dialog.show();
    }

    protected void on_Pre_Execute() {
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
            _dialog.dismiss();
            if (result.obj instanceof Throwable) {
                on_Exception_Execute((Throwable)result.obj);
            } else {
                on_Post_Execute((Result)result.obj);
            }
        } catch (Throwable e) {
            Log.w("moogli", "SpinnerDialogAsyncTask(" + result.obj + ")", e);
        }
    }

    protected void on_Post_Execute(Result result) {
    }

    protected void on_Exception_Execute(Throwable e) {
    }
}
