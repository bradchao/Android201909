package tw.org.iii.android201909;

import android.app.Application;
import android.content.SharedPreferences;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MainApp extends Application {
    public static RequestQueue queue;
    public static SharedPreferences sp;
    public static SharedPreferences.Editor speditor;

    @Override
    public void onCreate() {
        super.onCreate();

        sp = getSharedPreferences("config", MODE_PRIVATE);
        speditor = sp.edit();
        queue = Volley.newRequestQueue(this);
    }
}
