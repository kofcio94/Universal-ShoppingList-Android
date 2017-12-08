package com.hotmail.at.jablonski.michal.shoppinglist.db.dataManagers.shoppingItems;

import com.hotmail.at.jablonski.michal.shoppinglist.db.dao.ChildDao;
import com.hotmail.at.jablonski.michal.shoppinglist.ui.details.listAdapter.ShoppingItem;

public class ShoppingItemUpdater {

    public Integer update(ShoppingItem shoppingItem) {
        ChildDao dao = new ChildDao();
        return dao.update(shoppingItem.toChildEntity());
    }
}
