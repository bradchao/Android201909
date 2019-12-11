package tw.org.iii.android201909;

import androidx.annotation.Nullable;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.util.Map;

public class BradByteRequest extends Request<byte[]> {
    private Response.Listener<byte[]> listener;
    private Map<String,String> responseHeader;

    public BradByteRequest(int method,
                           String url,
                           Response.Listener<byte[]> listener,
                           @Nullable Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.listener = listener;
    }

    @Override
    protected Response<byte[]> parseNetworkResponse(NetworkResponse response) {
        responseHeader = response.headers;
        return Response.success(response.data, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(byte[] response) {
        listener.onResponse(response);
    }
}
