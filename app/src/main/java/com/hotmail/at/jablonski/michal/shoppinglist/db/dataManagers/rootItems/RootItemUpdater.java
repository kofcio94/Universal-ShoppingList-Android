package com.hotmail.at.jablonski.michal.shoppinglist.db.dataManagers.rootItems;

import android.os.AsyncTask;

import com.hotmail.at.jablonski.michal.shoppinglist.db.dao.RootDao;
import com.hotmail.at.jablonski.michal.shoppinglist.ui.main.listAdapter.RootItem;

public class RootItemUpdater extends AsyncTask<Void, Void, Void> {

    private RootItem rootItem;
    private RootItemUpdaterListener listener;

    public RootItemUpdater(RootItem rootItem, RootItemUpdaterListener listener) {
        this.rootItem = rootItem;
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        RootDao dao = new RootDao();
        dao.update(rootItem.toEntity());
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        listener.onUpdated();
    }

    public interface RootItemUpdaterListener {
        void onUpdated();
    }
}
