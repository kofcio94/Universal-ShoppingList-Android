package com.hotmail.at.jablonski.michal.shoppinglist.db.dataManagers.shoppingItems;

import com.hotmail.at.jablonski.michal.shoppinglist.db.dao.ChildDao;
import com.hotmail.at.jablonski.michal.shoppinglist.db.entities.ChildEntity;
import com.hotmail.at.jablonski.michal.shoppinglist.ui.details.listAdapter.ShoppingItem;
import com.hotmail.at.jablonski.michal.shoppinglist.ui.main.listAdapter.RootItem;

import java.util.ArrayList;
import java.util.List;

public class ShoppingItemSelector {

    public List<ShoppingItem> select(RootItem rootItem) {
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
}
