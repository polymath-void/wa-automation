package com.podconnect.app;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class JobQueue extends SQLiteOpenHelper {

    private static final String DB_NAME = "jobs.db";
    private static final int DB_VERSION = 1;

    public JobQueue(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE jobs (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "phone TEXT," +
                        "message TEXT," +
                        "status INTEGER DEFAULT 0)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    public void processJobs() {
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT id, phone, message FROM jobs WHERE status=0",
                null
        );

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String phone = cursor.getString(1);
            String message = cursor.getString(2);

            try {
                WhatsAppDriver.send(phone, message);
                db.execSQL("UPDATE jobs SET status=1 WHERE id=" + id);
            } catch (Exception e) {
                db.execSQL("UPDATE jobs SET status=2 WHERE id=" + id);
            }
        }

        cursor.close();
    }
}
