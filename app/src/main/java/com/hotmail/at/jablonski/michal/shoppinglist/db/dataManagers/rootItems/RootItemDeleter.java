package com.hotmail.at.jablonski.michal.shoppinglist.db.dataManagers.rootItems;

import android.os.AsyncTask;

import com.hotmail.at.jablonski.michal.shoppinglist.db.dao.RootDao;
import com.hotmail.at.jablonski.michal.shoppinglist.ui.main.listAdapter.RootItem;

public class RootItemDeleter extends AsyncTask<Void, Void, Void> {

    private RootItem item;
    private RootItemDeleterListener listener;

    public RootItemDeleter(RootItem rootListsItem, RootItemDeleterListener listener) {
        this.item = rootListsItem;
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        RootDao dao = new RootDao();
        dao.delete(item.toEntity());
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        listener.deleted();
    }

    public interface RootItemDeleterListener {
        void deleted();
    }
}
