package com.hotmail.at.jablonski.michal.shoppinglist.db.dataManagers.shoppingItems;

import android.os.AsyncTask;

import com.hotmail.at.jablonski.michal.shoppinglist.db.dao.ChildDao;
import com.hotmail.at.jablonski.michal.shoppinglist.ui.details.listAdapter.ShoppingItem;

public class ShoppingItemUpdater extends AsyncTask<Void, Void, Void> {

    private ShoppingItemUpdaterListener listener;
    private ShoppingItem shoppingItem;

    public ShoppingItemUpdater(ShoppingItem shoppingItem, ShoppingItemUpdaterListener listener) {
        this.listener = listener;
        this.shoppingItem = shoppingItem;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        ChildDao dao = new ChildDao();
        dao.update(shoppingItem.toChildEntity());
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        listener.onUpdated();
    }

    public interface ShoppingItemUpdaterListener {
        void onUpdated();
    }
}
