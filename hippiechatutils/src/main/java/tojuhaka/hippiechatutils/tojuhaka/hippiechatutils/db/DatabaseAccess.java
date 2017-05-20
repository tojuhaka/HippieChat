package tojuhaka.hippiechatutils.tojuhaka.hippiechatutils.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import tojuhaka.hippiechatutils.Message;

/**
 * Created by tojuhaka on 5/20/2017.
 */
public class DatabaseAccess {
    private SQLiteDatabase database;
    private DatabaseOpenHelper openHelper;
    private static volatile DatabaseAccess instance;

    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    public static synchronized DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    public void save(Message message) {
        ContentValues values = new ContentValues();
        values.put("target", message.getTarget());
        values.put("text", message.getText());
        database.insert(DatabaseOpenHelper.TABLE, null, values);
    }

    public void update(Message message) {
        ContentValues values = new ContentValues();
        values.put("target", message.getTarget());
        values.put("text", message.getText());
        String id = Integer.toString(message.getId());
        database.update(DatabaseOpenHelper.TABLE, values, "id = ?", new String[]{id});
    }

    public void delete(Message message) {
        String id = Integer.toString(message.getId());
        database.delete(DatabaseOpenHelper.TABLE, "id = ?", new String[]{id});
    }

    public List getAllMessages() {
        List items = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * From message ORDER BY id DESC", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Integer id = cursor.getInt(0);
            String target = cursor.getString(1);
            String text = cursor.getString(2);
            Message msg = new Message(target, text);
            msg.setId(id);
            items.add(msg);
            cursor.moveToNext();
        }
        cursor.close();
        return items;
    }
}

