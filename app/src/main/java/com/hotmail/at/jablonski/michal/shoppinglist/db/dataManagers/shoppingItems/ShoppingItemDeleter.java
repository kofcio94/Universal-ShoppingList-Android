package com.hotmail.at.jablonski.michal.shoppinglist.db.dataManagers.shoppingItems;

import com.hotmail.at.jablonski.michal.shoppinglist.db.dao.ChildDao;
import com.hotmail.at.jablonski.michal.shoppinglist.ui.details.listAdapter.ShoppingItem;

public class ShoppingItemDeleter {

    public Integer delete(ShoppingItem item) {
        ChildDao dao = new ChildDao();
        return dao.delete(item.toChildEntity());
    }
}
