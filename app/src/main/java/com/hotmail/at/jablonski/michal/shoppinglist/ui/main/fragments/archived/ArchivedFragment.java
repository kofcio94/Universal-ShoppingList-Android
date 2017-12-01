package com.hotmail.at.jablonski.michal.shoppinglist.ui.main.fragments.archived;

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

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArchivedFragment extends BaseFragment implements ArchivedPresenter.ViewController {

    private CallbackInterface callbackInterface;

    private ArchivedPresenter presenter;
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
        View root = inflater.inflate(R.layout.fragment_archived, container, false);
        initViewHolder(root);
        initPresenter();

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (callbackInterface != null)
            callbackInterface.setUpRecyclerViewForArchivedData(viewHolder.recyclerView);
    }

    private void initPresenter() {
        presenter = new ArchivedPresenter();
        presenter.onLoad(this);
    }

    private void initViewHolder(View root) {
        viewHolder = new ViewHolder(root);
    }

    @Override
    public void initView() {
        callbackInterface.setUpRecyclerViewForArchivedData(viewHolder.recyclerView);
    }

    static class ViewHolder {
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        @BindView(R.id.recycler)
        RecyclerView recyclerView;
    }
}
