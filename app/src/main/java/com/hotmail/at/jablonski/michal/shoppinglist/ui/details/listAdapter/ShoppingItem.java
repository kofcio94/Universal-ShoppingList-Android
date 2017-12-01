package com.hotmail.at.jablonski.michal.shoppinglist.ui.details.listAdapter;

import com.hotmail.at.jablonski.michal.shoppinglist.db.entities.ChildEntity;

public class ShoppingItem {

    private Integer id;
    private Integer rootId;

    private boolean isBought;
    private String itemName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getRootId() {
        return rootId;
    }

    public void setRootId(int rootId) {
        this.rootId = rootId;
    }

    public boolean isBought() {
        return isBought;
    }

    public void setBought(boolean bought) {
        isBought = bought;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public ChildEntity toChildEntity() {
        ChildEntity childEntity = new ChildEntity();
        childEntity.setId(id);
        childEntity.setRootId(rootId);
        childEntity.setBought(isBought);
        childEntity.setName(itemName);
        return childEntity;
    }
}
