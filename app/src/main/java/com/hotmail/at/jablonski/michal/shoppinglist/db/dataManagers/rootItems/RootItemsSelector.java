package com.hotmail.at.jablonski.michal.shoppinglist.db.dataManagers.rootItems;

import android.os.AsyncTask;

import com.hotmail.at.jablonski.michal.shoppinglist.db.dao.RootDao;
import com.hotmail.at.jablonski.michal.shoppinglist.db.entities.RootEntity;
import com.hotmail.at.jablonski.michal.shoppinglist.ui.main.listAdapter.RootItem;

import java.util.ArrayList;
import java.util.List;

public class RootItemsSelector extends AsyncTask<Void, Void, List<RootItem>> {

    private RootItemsSelectorListener listener;
    private boolean archived;

    public RootItemsSelector(boolean archived, RootItemsSelectorListener listener) {
        this.listener = listener;
        this.archived = archived;
    }

    @Override
    protected List<RootItem> doInBackground(Void... voids) {
        return selectRootItems();
    }

    @Override
    protected void onPostExecute(List<RootItem> rootListsItems) {
        listener.onDataSelected(rootListsItems);
    }

    private List<RootItem> selectRootItems() {
        RootDao dao = new RootDao();
        List<RootEntity> entities = dao.select(archived);
        List<RootItem> items = new ArrayList<>();
        for (RootEntity entity : entities) {
            RootItem rootItem = new RootItem();
            rootItem.setId(entity.getId());
            rootItem.setArchived(entity.isArchived());
            rootItem.setLiked(entity.isLiked());
            rootItem.setName(entity.getName());
            rootItem.setTimestamp(entity.getTimestamp());
            items.add(rootItem);
        }
        return items;
    }

    public interface RootItemsSelectorListener {
        void onDataSelected(List<RootItem> rootListsItems);
    }
}
