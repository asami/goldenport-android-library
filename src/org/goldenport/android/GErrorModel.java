package org.goldenport.android;

import java.io.IOException;

import org.goldenport.android.util.HttpIOException;
import org.goldenport.android.util.InvocationTargetIOException;

import android.content.Context;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @since   Apr. 30, 2011
 * @version Aug. 30, 2011
 * @author  ASAMI, Tomoharu
 */
@Singleton
public class GErrorModel<C extends GContext> {
    private static final int NETWORK_NOT_ENABLED = 0;
    private static final int NETWORK_UNAVAILABLE = 0;
    private static final int LOCATION_NOT_ENABLED = 0;
    private static final int LOCATION_UNAVAILABLE = 0;
    private static final int NETWORK_COMMUNICATION_ERROR = 0;
    private static final int SYSTEM_FAILURE = 0;
    private static final int SYSTEM_FAILURE_OUT_OF_MEMORY = 0;
    private static final int APPLICATION_DEFECT = 0;
    private static final int SYSTEM_STORAGE_UNAVAILABLE = 0;
    private static final int APPLICATION_DEFECT_NOT_REACH = 0;
    private static final int SYSTEM_ACTION_VIEW = 0;
    private static final int SYSTEM_PHONE_CALL = 0;

    @Inject
    protected Context context;

    @Inject
    protected C gcontext;

    private GErrorModel<?> _parent;

    public void setParent(GErrorModel<?> parent) {
        _parent = parent;
    }

    protected final void log_debug(String message) {
        gcontext.logDebug(message);
    }

    protected final void log_debug(String message, Throwable e) {
        gcontext.logDebug(message, e);
    }

    public boolean isApplicationFailure(Throwable e) {
        return isSystemFailure(e) || isApplicationDefect(e);
    }

    public boolean isSystemFailure(Throwable e) {
        if (e instanceof InvocationTargetIOException) {
            return isSystemFailure(e.getCause());
        }
        return !(e instanceof StackOverflowError) &&
               !(e instanceof InternalError) &&
               !(e instanceof UnsupportedOperationException) &&
               e instanceof OutOfMemoryError ||
               e instanceof Error;
    }

    public boolean isApplicationDefect(Throwable e) {
        if (e instanceof InvocationTargetIOException) {
            return isApplicationDefect(e.getCause());
        }
        return e instanceof StackOverflowError ||
            e instanceof InternalError ||
            e instanceof UnsupportedOperationException ||
            e instanceof NullPointerException ||
            e instanceof ClassCastException;
    }

    public boolean isUserError(Throwable e) {
        return false;
    }

    protected final Throwable normalize_exception(Throwable e) {
        if (e instanceof InvocationTargetIOException) {
            if (e.getCause() instanceof Exception) {
                return (Exception)e.getCause();
            } else {
                return e;
            }
        } else {
            return e;
        }
    }

    protected final IOException normalize_io_exception(IOException e) {
        if (e instanceof InvocationTargetIOException) {
            if (e.getCause() instanceof IOException) {
                return (IOException)e.getCause();
            } else {
                return e;
            }
        } else {
            return e;
        }
    }

    protected final Throwable normalize_throwable(Throwable e) {
        if (e instanceof InvocationTargetIOException) {
            return e.getCause();
        } else {
            return e;
        }
    }

    protected final String get_string(int resId) {
        return context.getString(resId);
    }

    protected final String string_code(int code) {
        return String.format("%04d", code);
    }

    protected String string_Down_Title() {
        return get_string(R.string.g_down_title);
    }

    public GIOException networkNotEnabled() {
        GIOException me = new GIOException(
                string_Down_Title(),
                string_Down_Network_Not_Enabled_Message(),
                string_code_Network_Not_Enabled()).withNotify().withForceMessage();
        log_debug("newtorkNotEnabled", me);
        return me;
    }

    protected String string_Down_Network_Not_Enabled_Message() {
        return get_string(R.string.g_network_not_enabled_message);
    }

    protected String string_code_Network_Not_Enabled() {
        return string_code(code_Network_Not_Enabled());
    }

    protected int code_Network_Not_Enabled() {
        return NETWORK_NOT_ENABLED;
    }

    public GIOException networkUnavailable() {
        GIOException me = new GIOException(
                string_Down_Title(),
                string_Down_Network_Unavailable_Message(),
                string_code_Network_Unavailable()).withRetry().withForceMessage();
        log_debug("networkUnavailable", me);
        return me;
    }

    protected String string_Down_Network_Unavailable_Message() {
        return get_string(R.string.g_network_unavailable_message);
    }

    protected String string_code_Network_Unavailable() {
        return string_code(code_Network_Unavailable());
    }

    protected int code_Network_Unavailable() {
        return NETWORK_UNAVAILABLE;
    }

    public GIOException locationNotEnabled() {
        GIOException me = new GIOException(
                string_Down_Title(),
                string_Location_Not_Enabled_Message(),
                string_code_Location_Not_Enabled()).withNotify().withForceMessage();
        log_debug("locationNotEnabled", me);
        return me;
    }

    protected String string_Location_Not_Enabled_Message() {
        return get_string(R.string.g_location_not_enabled_message);
    }

    protected String string_code_Location_Not_Enabled() {
        return string_code(LOCATION_NOT_ENABLED);
    }

    public GIOException locationUnavailable() {
        GIOException me = new GIOException(
                string_Location_Unavailable_Title(),
                string_Location_Unavailable_Message(),
                string_code_Location_Unavailable()).withRetry().withForceMessage();
        log_debug("locationUnavailable", me);
        return me;
    }

    protected String string_Location_Unavailable_Title() {
        return get_string(R.string.g_location_unavailable_title);
    }

    protected String string_Location_Unavailable_Message() {
        return get_string(R.string.g_location_unavailable_message);
    }

    protected String string_code_Location_Unavailable() {
        return string_code(LOCATION_UNAVAILABLE);
    }

    public GIOException communicationError(Throwable e) {
        GIOException me;
        if (e instanceof GIOException) {
            return (GIOException)e;
        } else if (e instanceof HttpIOException) {
            String[] messages = _http_error_message((HttpIOException)e);
            me = new GIOException(messages[0], messages[1], messages[2], e);
//        } else if (e instanceof TwitterException) {
//            String[] messages = _twitter_error_message((TwitterException)e);
//            me = new GIOException(messages[0], messages[1], messages[2], e);
        } else if (e instanceof InvocationTargetIOException) {
            Throwable cause = ((InvocationTargetIOException)e).getCause();
            String[] messages = _communication_error_message(cause);
            me = new GIOException(messages[0], messages[1], messages[2], cause);
        } else {
            String[] messages = _communication_error_message(e);
            me = new GIOException(messages[0], messages[1], messages[2], e);
        }
        me.setKindRetry();
        log_debug("GErrorModel#communicationError", e);
        return me;
    }

    private String[] _communication_error_message(Throwable e) {
        String title = string_Network_Communication_Error_Title();
        String message = string_Network_Communication_Error_Message();
        String code = string_code_communication_error();
        return new String[] { title, message, code };
    }

    protected String string_Network_Communication_Error_Title() {
        return get_string(R.string.g_network_communication_error_title);
    }

    protected String string_Network_Communication_Error_Message() {
        return get_string(R.string.g_network_communication_error_message);
    }

    private String[] _http_error_message(HttpIOException e) {
        String title = string_Network_Communication_Error_Title();
        String message = string_Network_Communication_Error_Message();
        String code = string_code_communication_error(e);
        return new String[] { title, message, code };
    }    

    protected String string_code_communication_error() {
        return string_code(NETWORK_COMMUNICATION_ERROR);
    }

    protected String string_code_communication_error(HttpIOException e) {
        return string_code(NETWORK_COMMUNICATION_ERROR); // XXX
    }

//    private String[] _twitter_error_message(TwitterException e) {
//        return new String[] { title, message, _error_code(ERROR_TWITTER_CODE) }; 
//    }

    public GIOException applicationFailure(Throwable e) {
        if (isApplicationDefect(e)) {
            return defect(e);
        } else {
            return failure(e);
        }
    }

    /**
     * システム故障(system failure)
     */
    public GIOException failure(Throwable e) {
        e = normalize_throwable(e);
        GIOException me;
        if (e instanceof OutOfMemoryError) {
            String title = string_Out_Of_Memory_Error_Title();
            String message = string_Out_Of_Memory_Error_Message();
            String code = string_code_Out_Of_Memory();
            me = new GIOException(title, message, code, e);
        } else {
            String title = string_Defect_Title();
            String message = string_Defect_Message();
            String code = string_code_Defect();
            me = new GIOException(title, message, code, e);
        }
        me.setKindTerminate();
        log_debug("GErrorModel#failure", e);
        return me;
    }

    protected String string_Out_Of_Memory_Error_Title() {
        return get_string(R.string.g_system_failure_out_of_memory_title);
    }

    protected String string_Out_Of_Memory_Error_Message() {
        return get_string(R.string.g_system_failure_out_of_memory_message);
    }

    protected String string_code_Out_Of_Memory() {
        return string_code(SYSTEM_FAILURE_OUT_OF_MEMORY);
    }

    protected String string_Defect_Title() {
        return get_string(R.string.g_error_defect_title);
    }

    protected String string_Defect_Message() {
        return get_string(R.string.g_error_defect_message);
    }

    protected String string_code_Defect() {
        return string_code(SYSTEM_FAILURE);
    }

    /**
     * アプリケーション障害(application defect)
     */
    public GIOException defect(Throwable e) {
        String title = string_defect_title();
        String message = string_defect_message();
        String code = string_code_defect();
        GIOException me = new GIOException(title, message, code, e);
        me.setKindTerminate();
        log_debug("GErrorModel#defect", e);
        return me;
    }

    protected String string_defect_title() {
        return get_string(R.string.g_application_defect_title);
    }

    protected String string_defect_message() {
        return get_string(R.string.g_application_defect_message);
    }

    protected String string_code_defect() {
        return string_code(APPLICATION_DEFECT);
    }
    
    public GIOException defect(Object object) {
        String title = string_defect_title();
        String message = string_defect_message();
        String code = string_code_defect();
        GIOException me = new GIOException(title, message, code);
        me.setKindTerminate();
        log_debug("GErrorModel#defect", new Throwable("[" + object.getClass() + "]" + object));
        return me;
    }

    public GIOException externalStorageUnavailable() {
        String title = string_External_Storage_Unavailable_Title();
        String message = string_External_Storage_Unavailable_Message();
        String code = string_code_External_Storage_Unavailable();
        GIOException me = new GIOException(title, message, code);
        me.setKindRetry();
        log_debug("GErrorModel#externalStorageUnavailable");
        return me;
    }

    protected String string_External_Storage_Unavailable_Title() {
        return get_string(R.string.g_system_storage_unavailable_title);
    }

    protected String string_External_Storage_Unavailable_Message() {
        return get_string(R.string.g_system_storage_unavailable_message);
    }

    protected String string_code_External_Storage_Unavailable() {
        return string_code(SYSTEM_STORAGE_UNAVAILABLE);
    }

    public GIOException requestCameraApplicationFailure(Throwable e) {
        // 現状ではSoftbank機(GALAPAGOS 2.2など)で、SDカードの有無が判定できないケースが対象。
        log_debug("GErrorModel#requestCameraApplicationFailure", e);
        return externalStorageUnavailable();
    }

    public GIOException requestGallalyApplicationFailure(Throwable e) {
        log_debug("GErrorModel#requestGallalyApplicationFailure", e);
        return applicationFailure(e);
    }

    public GIOException notReach() {
        String title = string_Not_Reach_Title();
        String message = string_Not_Reach_Message();
        String code = string_code_Not_Reach();
        GIOException me = new GIOException(title, message, code);
        me.setKindTerminate();
        log_debug("GErrorModel#notReach", new Throwable());
        return me;
    }

    protected String string_Not_Reach_Title() {
        return get_string(R.string.g_defect_not_reach_title);
    }

    protected String string_Not_Reach_Message() {
        return get_string(R.string.g_defect_not_reach_message);
    }

    protected String string_code_Not_Reach() {
        return string_code(APPLICATION_DEFECT_NOT_REACH);
    }

    // Web browser
    public GIOException actionView(Throwable e, Runnable retry) {
        log_debug("GErrorModel#actionView", e);
        return new GIOException(
                string_System_Action_View_Title(),
                string_System_Action_View_Message(),
                string_code_System_Action_View(),
                e).withRetry(retry);
    }

    protected String string_System_Action_View_Title() {
        return get_string(R.string.g_system_action_view_title);
    }

    protected String string_System_Action_View_Message() {
        return get_string(R.string.g_system_action_view_message);
    }

    protected String string_code_System_Action_View() {
        return string_code(SYSTEM_ACTION_VIEW);
    }

    // Phone
    public GIOException phoneCall(Throwable e, Runnable retry) {
        log_debug("GErrorModel#phoneCall", e);
        return new GIOException(
                string_System_Phone_Call_Title(),
                string_System_Phone_Call_Message(),
                string_code_System_Phone_Call(),
                e).withRetry(retry);
    }

    protected String string_System_Phone_Call_Title() {
        return get_string(R.string.g_system_phone_call_title);
    }

    protected String string_System_Phone_Call_Message() {
        return get_string(R.string.g_system_phone_call_message);
    }

    protected String string_code_System_Phone_Call() {
        return string_code(SYSTEM_PHONE_CALL);
    }

/*
    // View
    public View getErrorDialogPanel(String message) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View panel = inflater.inflate(R.layout.error_dialog, null);
        TextView tm = (TextView)panel.findViewById(R.id.error_dialog_message);
        tm.setText(message);
        LinearLayout pc = (LinearLayout)panel.findViewById(R.id.error_dialog_code_plate);
        pc.setVisibility(View.GONE);
        return panel;
    }

    public View getErrorDialogPanel(String message, String code) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View panel = inflater.inflate(R.layout.error_dialog, null);
        TextView tm = (TextView)panel.findViewById(R.id.error_dialog_message);
        tm.setText(message);
        TextView tc = (TextView)panel.findViewById(R.id.error_dialog_code);
        tc.setText(code);
        // エラーコードは表示しない。
        LinearLayout pc = (LinearLayout)panel.findViewById(R.id.error_dialog_code_plate);
        pc.setVisibility(View.GONE);
        return panel;
    }
*/
}
