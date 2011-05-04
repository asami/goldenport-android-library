package org.goldenport.android.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.common.io.Closeables;

import android.content.Context;
import android.util.Log;

/**
 * 
 * @since   Mar. 10, 2010
 * @version May.  4, 2011
 * @author  ASAMI, Tomoharu
 */
public class AndroidIos {
    public static JSONObject loadJsonFromFile(String filename, Context context) throws IOException, JSONException {
        return new JSONObject(loadStringFromFile(filename, context));
    }

    public static JSONObject loadJsonFromHttpResponse(HttpResponse httpResponse) throws JSONException, IOException {
        String string = loadStringFromHttpResponse(httpResponse);
        if (StringUtils.isBlank(string)) {
            return new JSONObject();
        } else {
            return new JSONObject(string);
        }
    }

    public static String loadStringFromHttpResponse(HttpResponse httpResponse) throws IOException {
        if (httpResponse.getStatusLine().getStatusCode() == 204) { // NO CONTENT
            return "";
        }
        HttpEntity entity = httpResponse.getEntity();
        if (entity == null) {
            return "";
        }
        InputStream in = null;
        try {
             in = entity.getContent();
        } catch (IllegalStateException e) {
            return ""; // NO CONTENT
        }
        try {
            Header header = entity.getContentEncoding();
            String encoding = "UTF-8";
            if (header != null) {
                encoding = header.getValue();
            }
            return loadStringFromInputStream(in, encoding);
        } catch (IOException e) {
            throw e;
        } catch (Throwable e) {
            throw new InvocationTargetIOException(e);
        } finally {
            Closeables.closeQuietly(in);
        }
    }

    public static void saveJsonFromFile(String filename, JSONObject json, Context context) throws IOException, JSONException {
        OutputStream out = null;
        try {
            out = context.openFileOutput(filename, Context.MODE_PRIVATE);
            OutputStreamWriter writer = new OutputStreamWriter(out, "utf-8");
            writer.append(json.toString(2));
            writer.flush();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {}
            }
        }
    }

    public static String loadStringFromFile(String filename, Context context) throws IOException {
        InputStream in = null;
        try {
            in = context.openFileInput(filename);
            return loadStringFromInputStream(in, "utf-8");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {}
            }
        }
    }

    public static String loadStringFromInputStream(InputStream in, String encoding)
            throws UnsupportedEncodingException, IOException {
        InputStreamReader reader = new InputStreamReader(in, encoding);
        StringBuilder builder = new StringBuilder();
        char[] buf = new char[4096];
        int size;
        while ((size = reader.read(buf)) != -1) {
            builder.append(buf, 0, size);
        }
        return builder.toString();
    }

    public static void saveStringToFile(String string, String filename, Context context) throws IOException {
        OutputStream out = null;
        Writer writer = null;
        try {
            out = context.openFileOutput(filename, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out, "utf-8");
            writer.append(string);
            writer.flush();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                } else if (out != null) {
                    out.close();
                }
            } catch (IOException ee) {}
        }
    }

    public static void saveInputStreamToFile(InputStream in, String filename, Context context) throws IOException {
        OutputStream out = null;
        try {
            out = context.openFileOutput(filename, Context.MODE_PRIVATE);
            byte[] buf = new byte[8192];
            int size;
            while ((size = in.read(buf)) != -1) {
                out.write(buf, 0, size);
            }
            out.flush();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ee) {}
        }
    }

    public static void saveResourceToFile(int resourceId, String filename, Context context) throws IOException {
        InputStream in = null;
        try {
            in = context.getResources().openRawResource(resourceId);
            saveInputStreamToFile(in, filename, context);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ee) {}
        }
    }

    public static void truncateFile(String filename, Context context) throws IOException {
        OutputStream out = null;
        try {
            out = context.openFileOutput(filename, Context.MODE_PRIVATE);
            out.write(new byte[0]); // XXX needs check
            out.flush();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {}
            }
        }        
    }
}
