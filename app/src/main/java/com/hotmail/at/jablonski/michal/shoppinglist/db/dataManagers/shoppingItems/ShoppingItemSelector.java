package com.hotmail.at.jablonski.michal.shoppinglist.db.dataManagers.shoppingItems;

import android.os.AsyncTask;

import com.hotmail.at.jablonski.michal.shoppinglist.db.dao.ChildDao;
import com.hotmail.at.jablonski.michal.shoppinglist.db.entities.ChildEntity;
import com.hotmail.at.jablonski.michal.shoppinglist.ui.details.listAdapter.ShoppingItem;
import com.hotmail.at.jablonski.michal.shoppinglist.ui.main.listAdapter.RootItem;

import java.util.ArrayList;
import java.util.List;

public class ShoppingItemSelector extends AsyncTask<Void, Void, List<ShoppingItem>> {

    private ShoppingItemSelectorListener listener;
    private RootItem rootItem;

    public ShoppingItemSelector(RootItem rootItem, ShoppingItemSelectorListener listener) {
        this.listener = listener;
        this.rootItem = rootItem;
    }

    @Override
    protected List<ShoppingItem> doInBackground(Void... voids) {
        return getData();
    }

    private List<ShoppingItem> getData() {
        ChildDao dao = new ChildDao();
        List<ChildEntity> entities = dao.select(rootItem.getId());
        List<ShoppingItem> shoppingItems = new ArrayList<>();
        for (ChildEntity entity : entities) {
            ShoppingItem item = new ShoppingItem();
            item.setId(entity.getId());
            item.setRootId(entity.getRootId());
            item.setBought(entity.isBought());
            item.setItemName(entity.getName());

            shoppingItems.add(item);
        }

        return shoppingItems;
    }

    @Override
    protected void onPostExecute(List<ShoppingItem> shoppingItems) {
        listener.onDataSelected(shoppingItems);
    }


    public interface ShoppingItemSelectorListener {
        void onDataSelected(List<ShoppingItem> items);
    }
}
