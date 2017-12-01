package com.hotmail.at.jablonski.michal.shoppinglist.db.dataManagers.shoppingItems;

import android.os.AsyncTask;

import com.hotmail.at.jablonski.michal.shoppinglist.db.dao.ChildDao;
import com.hotmail.at.jablonski.michal.shoppinglist.ui.details.listAdapter.ShoppingItem;

public class ShoppingItemInserter extends AsyncTask<Void, Void, Long> {

    private ShoppingItem item;
    private ShoppingItemInserterListener listener;

    public ShoppingItemInserter(ShoppingItem item, ShoppingItemInserterListener listener) {
        this.item = item;
        this.listener = listener;
    }

    @Override
    protected Long doInBackground(Void... voids) {
        ChildDao dao = new ChildDao();
        return dao.insert(item.toChildEntity());
    }

    @Override
    protected void onPostExecute(Long aLong) {
        listener.onInserted(aLong);
    }

    public interface ShoppingItemInserterListener {
        void onInserted(Long id);
    }
}
