package tw.org.iii.android201909;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private ImageView img;
    private Bitmap bitmap;
    private UIHandler uiHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        uiHandler = new UIHandler();
        img = findViewById(R.id.img);
    }

    public void test1(View view) {
        new Thread(){
            @Override
            public void run() {
                gotoIII();
            }
        }.start();
    }

    private void gotoIII(){
        try {
            URL url = new URL("https://www.iii.org.tw");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connect();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ( (line = reader.readLine()) != null){
                Log.v("brad", line);
            }
            reader.close();

        }catch (Exception e){
            Log.v("brad", e.toString());
        }
    }

    public void test2(View view) {
        new Thread(){
            @Override
            public void run() {
                fetchImage();
            }
        }.start();
    }

    private void fetchImage(){
        try {
            URL url = new URL("https://www.iii.org.tw/assets/images/information-news/image005.jpg");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connect();

            bitmap = BitmapFactory.decodeStream(conn.getInputStream());
            uiHandler.sendEmptyMessage(0);
        }catch (Exception e){
            Log.v("brad", e.toString());
        }
    }

    public void test3(View view) {


    }

    private class UIHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            img.setImageBitmap(bitmap);
        }
    }
}
