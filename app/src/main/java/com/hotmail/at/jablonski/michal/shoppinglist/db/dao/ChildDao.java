package com.hotmail.at.jablonski.michal.shoppinglist.db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hotmail.at.jablonski.michal.shoppinglist.db.entities.ChildEntity;
import com.hotmail.at.jablonski.michal.shoppinglist.db.instance.DbWrapper;

import java.util.ArrayList;
import java.util.List;

public class ChildDao {

    private SQLiteDatabase db;

    public List<ChildEntity> select(int rootId) {
        db = DbWrapper.getReadableDb();

        String rawQuery = "SELECT * FROM ChildItems WHERE rootId = ?";
        String args[] = {String.valueOf(rootId)};

        Cursor cursor = db.rawQuery(rawQuery, args);
        return fromCursor(cursor);
    }

    public long insert(ChildEntity entity) {
        db = DbWrapper.getWritableDb();

        ContentValues contentValues = toContentValues(entity);
        return db.insert("ChildItems", null, contentValues);
    }

    public int update(ChildEntity entity) {
        db = DbWrapper.getWritableDb();

        String where = "id = ?";
        String[] args = {String.valueOf(entity.getId())};

        return db.update("ChildItems", toContentValues(entity), where, args);
    }

    public int delete(ChildEntity entity) {
        db = DbWrapper.getWritableDb();

        String where = "id = ?";
        String[] args = {String.valueOf(entity.getId())};

        return db.delete("ChildItems", where, args);
    }

    private ContentValues toContentValues(ChildEntity entity) {
        ContentValues contentValues = new ContentValues();
        if (entity.getId() != null)
            contentValues.put("id", entity.getId());
        contentValues.put("rootId", entity.getRootId());
        contentValues.put("name", entity.getName());
        contentValues.put("isBought", entity.isBought());

        return contentValues;
    }

    private List<ChildEntity> fromCursor(Cursor cursor) {
        List<ChildEntity> list = new ArrayList<>();

        try {
            while (cursor.moveToNext()) {
                ChildEntity entity = new ChildEntity();

                entity.setId(cursor.getInt(cursor.getColumnIndex("id")));
                entity.setRootId(cursor.getInt(cursor.getColumnIndex("rootId")));
                entity.setName(cursor.getString(cursor.getColumnIndex("name")));
                entity.setBought(cursor.getInt(cursor.getColumnIndex("isBought")) > 0);

                list.add(entity);
            }
        } finally {
            cursor.close();
        }

        return list;
    }
}
