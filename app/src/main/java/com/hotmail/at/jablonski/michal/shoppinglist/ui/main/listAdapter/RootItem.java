package com.hotmail.at.jablonski.michal.shoppinglist.ui.main.listAdapter;

import com.hotmail.at.jablonski.michal.shoppinglist.db.entities.RootEntity;

import java.io.Serializable;

public class RootItem implements Serializable {
    private Integer id;

    private String name;
    private boolean isArchived;
    private boolean isLiked;
    private long timestamp;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isArchived() {
        return isArchived;
    }

    public void setArchived(boolean archived) {
        isArchived = archived;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public RootEntity toEntity() {
        RootEntity entity = new RootEntity();
        entity.setId(id);
        entity.setArchived(isArchived);
        entity.setLiked(isLiked);
        entity.setName(name);
        entity.setTimestamp(timestamp);

        return entity;
    }

}
