package com.hotmail.at.jablonski.michal.shoppinglist.db.dataManagers.shoppingItems;

import com.hotmail.at.jablonski.michal.shoppinglist.db.dao.ChildDao;
import com.hotmail.at.jablonski.michal.shoppinglist.ui.details.listAdapter.ShoppingItem;

public class ShoppingItemInserter {

    public Long insert(ShoppingItem item) {
        ChildDao dao = new ChildDao();
        return dao.insert(item.toChildEntity());
    }
}
