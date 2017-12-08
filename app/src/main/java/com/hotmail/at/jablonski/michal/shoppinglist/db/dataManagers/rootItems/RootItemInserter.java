package com.hotmail.at.jablonski.michal.shoppinglist.db.dataManagers.rootItems;

import com.hotmail.at.jablonski.michal.shoppinglist.db.dao.RootDao;
import com.hotmail.at.jablonski.michal.shoppinglist.ui.main.listAdapter.RootItem;

public class RootItemInserter {

    public Long insert(RootItem item) {
        RootDao dao = new RootDao();
        return dao.insert(item.toEntity());
    }
}
