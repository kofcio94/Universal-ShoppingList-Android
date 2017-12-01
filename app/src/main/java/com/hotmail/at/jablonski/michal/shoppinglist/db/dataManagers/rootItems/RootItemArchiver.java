package com.hotmail.at.jablonski.michal.shoppinglist.db.dataManagers.rootItems;

import android.os.AsyncTask;

import com.hotmail.at.jablonski.michal.shoppinglist.db.dao.RootDao;
import com.hotmail.at.jablonski.michal.shoppinglist.ui.main.listAdapter.RootItem;

public class RootItemArchiver extends AsyncTask<Void, Void, Void> {

    private RootItem item;
    private RootItemArchiverListener listener;

    public RootItemArchiver(RootItem rootListsItem, RootItemArchiverListener listener) {
        this.item = rootListsItem;
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        RootDao dao = new RootDao();
        item.setArchived(true);
        dao.update(item.toEntity());
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        listener.onArchived();
    }

    public interface RootItemArchiverListener {
        void onArchived();
    }
}
