package com.hotmail.at.jablonski.michal.shoppinglist.db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hotmail.at.jablonski.michal.shoppinglist.db.entities.RootEntity;
import com.hotmail.at.jablonski.michal.shoppinglist.db.instance.DbWrapper;

import java.util.ArrayList;
import java.util.List;

public class RootDao {

    private SQLiteDatabase db;

    public List<RootEntity> select(boolean archived) {
        db = DbWrapper.getReadableDb();

        String rawQuery = "SELECT * FROM RootItems WHERE `isArchived` = ? ORDER BY `timestamp` DESC";
        int isArchived = archived ? 1 : 0;
        String[] args = {String.valueOf(isArchived)};
        Cursor cursor = db.rawQuery(rawQuery, args);

        return fromCursor(cursor);
    }

    public long insert(RootEntity entity) {
        db = DbWrapper.getWritableDb();

        ContentValues contentValues = toContentValues(entity);
        return db.insert("RootItems", null, contentValues);
    }

    public int update(RootEntity entity) {
        db = DbWrapper.getWritableDb();

        String where = "id = ?";
        String[] args = {String.valueOf(entity.getId())};

        return db.update("RootItems", toContentValues(entity), where, args);
    }

    public int delete(RootEntity entity) {
        db = DbWrapper.getWritableDb();

        String where = "id = ?";
        String[] args = {String.valueOf(entity.getId())};

        return db.delete("RootItems", where, args);
    }

    private ContentValues toContentValues(RootEntity entity) {
        ContentValues contentValues = new ContentValues();
        if (entity.getId() != null)
            contentValues.put("id", entity.getId());
        contentValues.put("name", entity.getName());
        contentValues.put("isArchived", entity.isArchived());

        int isLiked = entity.isLiked() ? 1 : 0;

        contentValues.put("isLiked", isLiked);
        contentValues.put("timestamp", entity.getTimestamp());

        return contentValues;
    }

    private List<RootEntity> fromCursor(Cursor cursor) {
        List<RootEntity> list = new ArrayList<>();

        try {
            while (cursor.moveToNext()) {
                RootEntity entity = new RootEntity();
                entity.setId(cursor.getInt(cursor.getColumnIndex("id")));
                entity.setName(cursor.getString(cursor.getColumnIndex("name")));
                entity.setArchived(cursor.getInt(cursor.getColumnIndex("isArchived")) > 0);
                entity.setLiked(cursor.getInt(cursor.getColumnIndex("isLiked")) != 0);
                entity.setTimestamp(Long.parseLong(cursor.getString(cursor.getColumnIndex("timestamp"))));

                list.add(entity);
            }
        } finally {
            cursor.close();
        }

        return list;
    }
}
