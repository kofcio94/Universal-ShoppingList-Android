package com.hotmail.at.jablonski.michal.shoppinglist.db.dataManagers.rootItems;

import android.os.AsyncTask;

import com.hotmail.at.jablonski.michal.shoppinglist.db.dao.RootDao;
import com.hotmail.at.jablonski.michal.shoppinglist.ui.main.listAdapter.RootItem;

public class RootItemInserter extends AsyncTask<Void, Void, Long> {

    private RootItemInserterListener listener;
    private RootItem item;

    public RootItemInserter(RootItem rootListsItem, RootItemInserterListener listener) {
        this.listener = listener;
        this.item = rootListsItem;
    }

    @Override
    protected Long doInBackground(Void... voids) {
        RootDao dao = new RootDao();
        return dao.insert(item.toEntity());
    }

    @Override
    protected void onPostExecute(Long row) {
        listener.onInserted(row);
    }

    public interface RootItemInserterListener {
        void onInserted(Long id);
    }
}
