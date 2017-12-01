package com.hotmail.at.jablonski.michal.shoppinglist.db.dataManagers.shoppingItems;

import android.os.AsyncTask;

import com.hotmail.at.jablonski.michal.shoppinglist.db.dao.ChildDao;
import com.hotmail.at.jablonski.michal.shoppinglist.ui.details.listAdapter.ShoppingItem;

public class ShoppingItemDeleter extends AsyncTask<Void, Void, Void> {

    private ShoppingItem shoppingItem;
    private ShoppingItemDeleterListener listener;

    public ShoppingItemDeleter(ShoppingItem shoppingItem, ShoppingItemDeleterListener listener) {
        this.shoppingItem = shoppingItem;
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        ChildDao dao = new ChildDao();
        dao.delete(shoppingItem.toChildEntity());
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        listener.deleted();
    }

    public interface ShoppingItemDeleterListener {
        void deleted();
    }
}
