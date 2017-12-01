package com.hotmail.at.jablonski.michal.shoppinglist.core;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.hotmail.at.jablonski.michal.shoppinglist.db.instance.DataBaseInstance;

public class Core extends Application {

    private DataBaseInstance dataBaseInstance;
    public static DataBaseCallback dbCallback;

    public interface DataBaseCallback {
        SQLiteDatabase obtainWritableDataBase();

        SQLiteDatabase obtainReadableDataBase();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        dataBaseInstance = new DataBaseInstance(getApplicationContext());
        initDbInterface();
    }

    private void initDbInterface() {
        dbCallback = new DataBaseCallback() {
            @Override
            public synchronized SQLiteDatabase obtainWritableDataBase() {
                return dataBaseInstance.getWritableDatabase();
            }

            @Override
            public synchronized SQLiteDatabase obtainReadableDataBase() {
                return dataBaseInstance.getReadableDatabase();
            }
        };
    }
}
