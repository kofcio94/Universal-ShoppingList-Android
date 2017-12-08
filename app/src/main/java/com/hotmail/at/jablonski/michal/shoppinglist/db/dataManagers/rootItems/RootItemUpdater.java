package com.hotmail.at.jablonski.michal.shoppinglist.db.dataManagers.rootItems;

import com.hotmail.at.jablonski.michal.shoppinglist.db.dao.RootDao;
import com.hotmail.at.jablonski.michal.shoppinglist.ui.main.listAdapter.RootItem;

public class RootItemUpdater {

    public Integer update(RootItem item) {
        RootDao dao = new RootDao();
        return dao.update(item.toEntity());
    }
}
