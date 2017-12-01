package com.hotmail.at.jablonski.michal.shoppinglist.db.instance;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseInstance extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "shoppingList.db";

    public DataBaseInstance(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ROOT_ITEMS_CREATE_STATEMENT);
        db.execSQL(CHILD_ITEMS_CREATE_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //switch case old version when DB_VERSION is incremented
    }

    private final static String ROOT_ITEMS_CREATE_STATEMENT =
            "CREATE TABLE `RootItems` ( " +
                    "`id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, `name` TEXT NOT NULL, " +
                    "`isArchived` INTEGER NOT NULL, " +
                    "`isLiked` INTEGER NOT NULL, " +
                    "`timestamp` TEXT NOT NULL );";

    private final static String CHILD_ITEMS_CREATE_STATEMENT = "CREATE TABLE `ChildItems` ( " +
            "`id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, " +
            "`rootId` INTEGER NOT NULL, " +
            "`name` TEXT NOT NULL, " +
            "`isBought` INTEGER NOT NULL, " +
            "FOREIGN KEY(`rootId`) REFERENCES `RootItems`(`id`) )";
}
