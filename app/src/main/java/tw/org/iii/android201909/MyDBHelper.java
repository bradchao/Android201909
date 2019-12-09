package tw.org.iii.android201909;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDBHelper extends SQLiteOpenHelper {
    private String createTable =
            "CREATE TABLE travel (id INTEGER PRIMARY KEY AUTOINCREMENT,tname TEXT,tel TEXT,addr TEXT, lat REAL, lng REAL)";
    public MyDBHelper(@Nullable Context context, @Nullable String name, @Nullable
            SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
