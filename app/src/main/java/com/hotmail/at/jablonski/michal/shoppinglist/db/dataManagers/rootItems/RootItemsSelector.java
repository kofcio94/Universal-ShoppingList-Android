package com.hotmail.at.jablonski.michal.shoppinglist.db.dataManagers.rootItems;

import com.hotmail.at.jablonski.michal.shoppinglist.db.dao.RootDao;
import com.hotmail.at.jablonski.michal.shoppinglist.db.entities.RootEntity;
import com.hotmail.at.jablonski.michal.shoppinglist.ui.main.listAdapter.RootItem;

import java.util.ArrayList;
import java.util.List;

public class RootItemsSelector {

    public List<RootItem> select(boolean archived) {
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


}
