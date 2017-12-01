package com.hotmail.at.jablonski.michal.shoppinglist.ui.main.fragments;

import android.support.v7.widget.RecyclerView;

public interface CallbackInterface {
    void setUpRecyclerView(RecyclerView recyclerView);

    void setUpRecyclerViewForArchivedData(RecyclerView recyclerView);
}
