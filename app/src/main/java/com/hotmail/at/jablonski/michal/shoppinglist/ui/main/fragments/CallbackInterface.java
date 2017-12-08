package com.hotmail.at.jablonski.michal.shoppinglist.ui.main.fragments;

public interface CallbackInterface {
    void setUpRecyclerView();

    void setUpRecyclerViewForArchivedData();

    void setUpCurrentReverseCallbackInterface(ReverseCallbackInterface reverseCallbackInterface);

    void setUpArchivedReverseCallbackInterface(ReverseCallbackInterface reverseCallbackInterface);

}
