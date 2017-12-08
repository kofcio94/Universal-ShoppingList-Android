package com.hotmail.at.jablonski.michal.shoppinglist.db.dataManagers.rootItems;

import com.hotmail.at.jablonski.michal.shoppinglist.db.dao.RootDao;
import com.hotmail.at.jablonski.michal.shoppinglist.ui.main.listAdapter.RootItem;

public class RootItemArchiver {

    public Integer archive(RootItem item) {
        RootDao dao = new RootDao();
        item.setArchived(true);
        return dao.update(item.toEntity());
    }
}
