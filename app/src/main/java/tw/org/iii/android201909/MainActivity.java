package tw.org.iii.android201909;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private ImageView img;
    private Bitmap bitmap;
    private UIHandler uiHandler;
    private TextView tv;
    private MyDBHelper myDBHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDBHelper = new MyDBHelper(this, "brad", null, 1);
        db = myDBHelper.getReadableDatabase();

        tv = findViewById(R.id.tv);

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
        String url = "https://www.iii.org.tw";
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("brad", response);
                        tv.setText(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("brad", error.toString());
                    }
                });
        MainApp.queue.add(request);

    }

    public void test4(View view) {
        String url = "http://data.coa.gov.tw/Service/OpenData/ODwsv/ODwsvTravelStay.aspx";
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseTest4(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("brad", error.toString());
                    }
                });
        MainApp.queue.add(request);
    }

    private void parseTest4(String json){
        try {
            JSONArray root = new JSONArray(json);
            //Log.v("brad", "count = " + root.length());
            for (int i=0; i<root.length(); i++){
                JSONObject row = root.getJSONObject(i);
                String name = row.getString("Name");
                String tel = row.getString("Tel");
                String address = row.getString("Address");
                String coord = row.getString("Coordinate");
                String[] latlng = coord.split(",");

                // INSERT INTO travel (tname, tel,addr,lat,lng) VALUES ('','','', , );
                ContentValues values = new ContentValues();
                values.put("tname", name);
                values.put("tel", tel);
                values.put("addr", address);
                values.put("lat", latlng[0]);
                values.put("lng", latlng[1]);
                db.insert("travel", null, values);


            }




        }catch (Exception e){
            Log.v("brad", e.toString());
        }
    }

    public void test5(View view) {
        // select * from travel
        Cursor c = db.query(
                "travel",
                new String[]{"id","tname","lat","lng"}, null,null,null,null,null);
//        int count = c.getCount();
//        Log.v("brad", "count = " + count);

        int fieldId = c.getColumnIndex("id");
        int fieldName = c.getColumnIndex("tname");
        int fieldLat = c.getColumnIndex("lat");
        int fieldLng = c.getColumnIndex("lng");

        while (c.moveToNext()){
            String id = c.getString(fieldId);
            String name = c.getString(fieldName);
            String lat = c.getString(fieldLat);
            String lng = c.getString(fieldLng);
            Log.v("brad", id + ":" + name + ":" + lat +":" + lng);
        }


        c.close();
    }

    public void test6(View view) {
        // delete from travel where id = 258 and tname like '飛牛%'
        db.delete("travel","id = ? and tname like ?", new String[]{"258", "飛牛%"});
    }

    public void test7(View view) {
        // UPDATE travel SET tname='小漢山休閒農場',lat=22.456, lng=123.123 WHERE id = 264
        ContentValues values = new ContentValues();
        values.put("tname", "小漢山休閒農場");
        values.put("lat", "22.456");
        values.put("lng", "123.123");
        db.update("travel",values,"id = ?",new String[]{"264"});
    }


    private class UIHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            img.setImageBitmap(bitmap);
        }
    }
}
