package com.hotmail.at.jablonski.michal.shoppinglist.db.instance;

import android.database.sqlite.SQLiteDatabase;

import com.hotmail.at.jablonski.michal.shoppinglist.core.Core;

public class DbWrapper {

    public static synchronized SQLiteDatabase getWritableDb() {
        return Core.dbCallback.obtainWritableDataBase();
    }

    public static synchronized SQLiteDatabase getReadableDb() {
        return Core.dbCallback.obtainReadableDataBase();
    }
}

