package org.goldenport.android.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.common.io.CharStreams;
import com.google.common.io.Closeables;

/**
 * @since   Jul. 27, 2011
 * @version Aug. 26, 2011
 * @author  ASAMI, Tomoharu
 */
public class UHttpComponents {
    public static String loadStringFromHttpResponse(HttpResponse res) throws IOException {
        String encoding = "utf-8";
        InputStream in = res.getEntity().getContent();
        try {
            InputStreamReader reader = new InputStreamReader(in, encoding);
            return CharStreams.toString(reader);
        } finally {
            Closeables.closeQuietly(in);
        }
    }

    public static JSONObject loadJsonFromHttpResponse(HttpResponse res) throws IOException, JSONException {
        return new JSONObject(loadStringFromHttpResponse(res));
    }
}
