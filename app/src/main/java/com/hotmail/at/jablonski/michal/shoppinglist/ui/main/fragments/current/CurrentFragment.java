package com.hotmail.at.jablonski.michal.shoppinglist.ui.main.fragments.current;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hotmail.at.jablonski.michal.shoppinglist.R;
import com.hotmail.at.jablonski.michal.shoppinglist.ui.base.BaseFragment;
import com.hotmail.at.jablonski.michal.shoppinglist.ui.main.fragments.CallbackInterface;
import com.hotmail.at.jablonski.michal.shoppinglist.ui.main.fragments.ReverseCallbackInterface;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CurrentFragment extends BaseFragment
        implements CurrentPresenter.ViewController, ReverseCallbackInterface {

    private CallbackInterface callbackInterface;

    private CurrentPresenter presenter;
    private ViewHolder viewHolder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //casting to implemented context as instance of activity:
        //https://stackoverflow.com/a/9891449/7213349
        callbackInterface = (CallbackInterface) context;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_current, container, false);
        initViewHolder(root);
        initPresenter();

        callbackInterface.setUpCurrentReverseCallbackInterface(this);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (callbackInterface != null)
            callbackInterface.setUpRecyclerView();
    }

    private void initPresenter() {
        presenter = new CurrentPresenter();
        presenter.onLoad(this);
    }

    private void initViewHolder(View root) {
        viewHolder = new ViewHolder(root);
    }

    @Override
    public void initView() {
        callbackInterface.setUpRecyclerView();
    }

    @Override
    public RecyclerView getRecycler() {
        return viewHolder.recyclerView;
    }

    static class ViewHolder {
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        @BindView(R.id.recycler)
        RecyclerView recyclerView;
    }
}
