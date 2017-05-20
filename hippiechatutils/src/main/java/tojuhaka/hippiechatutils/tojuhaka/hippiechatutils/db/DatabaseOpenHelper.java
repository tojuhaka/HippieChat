package tojuhaka.hippiechatutils.tojuhaka.hippiechatutils.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by tojuhaka on 5/20/2017.
 */

public class DatabaseOpenHelper extends SQLiteOpenHelper {
    public static final String DATABASE = "hipchat.db";
    public static final String TABLE = "message";
    public static final int VERSION = 1;


    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE message(id INTEGER PRIMARY KEY, target TEXT, text TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
