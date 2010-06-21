/*
 * Copyright (C) 2009-2010 Aubort Jean-Baptiste (Rorist)
 * Licensed under GNU's GPL 2, see README
 */

package info.lamatricexiste.network.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;
import android.util.Log;

// TODO: Do not create databases like that, but provides final SQLite binary files

public class Db extends SQLiteOpenHelper {
    private final static String TAG = "Db";
    private Context ctxt;

    protected static String DB_NAME = "";
    public static String DB_TABLE = "";
    protected static String DROP_TABLE = "";
    public static int DB_TABLE_RES = -1;
    protected static int DB_VERSION = 1;

    public Db(Context context, String name, int version) {
        super(context, name, null, version);
        ctxt = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Log.v(TAG, "onCreate");
        // createTable(db, DB_TABLE, DB_TABLE_RES);
    }

    public void onOpen(SQLiteDatabase db) {
        // Log.v(TAG, "onOpen");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int Old, int New) {
        Log.v(TAG, "onUpgrade");
        db.execSQL(DROP_TABLE);
        // onCreate(db);
        createTable(db, DB_TABLE, DB_TABLE_RES);
    }

    public void createTable(SQLiteDatabase db, String table_name, int resource) {
        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='"
                + table_name + "'", null);
        try {
            if (c.getCount() == 0) {
                // Initial table creation and insert
                Log.v(TAG, "createTable " + table_name);
                InputStream stream = ctxt.getResources().openRawResource(resource);
                InputStreamReader is = new InputStreamReader(stream);
                BufferedReader in = new BufferedReader(is, 4);
                String str;
                while ((str = in.readLine()) != null) {
                    if (!str.equals("BEGIN TRANSACTION;") && !str.equals("COMMIT;")
                            && !str.equals("")) {
                        db.execSQL(str);
                    }
                }
                // Set Nic DB as installed
                Editor edit = PreferenceManager.getDefaultSharedPreferences(ctxt).edit();
                edit.putInt(Prefs.KEY_RESET_SERVICESDB, ctxt.getPackageManager().getPackageInfo(
                        "info.lamatricexiste.network", 0).versionCode);
                edit.commit();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLiteException e) {
            e.printStackTrace();
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        } finally {
            c.close();
        }
    }
}