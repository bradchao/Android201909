package tw.org.iii.android201909;

import androidx.annotation.Nullable;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;

public class BradByteRequest extends Request<byte[]> {
    private Response.Listener<byte[]> listener;

    public BradByteRequest(int method,
                           String url,
                           Response.Listener<byte[]> listener,
                           @Nullable Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.listener = listener;
    }

    @Override
    protected Response<byte[]> parseNetworkResponse(NetworkResponse response) {
        return null;
    }

    @Override
    protected void deliverResponse(byte[] response) {
        listener.onResponse(response);
    }
}
