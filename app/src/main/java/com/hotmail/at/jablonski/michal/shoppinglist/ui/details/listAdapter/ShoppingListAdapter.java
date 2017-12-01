package com.hotmail.at.jablonski.michal.shoppinglist.ui.details.listAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.TextView;

import com.hotmail.at.jablonski.michal.shoppinglist.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class ShoppingListAdapter
        extends RecyclerView.Adapter<ShoppingListAdapter.ViewHolder> {

    private final boolean isArchived;
    private List<ShoppingItem> shoppingListItems;

    private int lastPosition = -1;

    public ShoppingListAdapter(List<ShoppingItem> shoppingListItems, boolean isArchived) {
        this.shoppingListItems = shoppingListItems;
        this.isArchived = isArchived;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_shopping_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ShoppingItem item = shoppingListItems.get(position);

        holder.checkBoxIsBought.setChecked(item.isBought());
        holder.textViewIngredientName.setText(item.getItemName());
        if (isArchived) {
            holder.checkBoxIsBought.setEnabled(false);
            holder.delete.setVisibility(View.GONE);
        } else
            setCheckListener(item, holder);

        animateHolder(position, holder);
    }

    private void setCheckListener(ShoppingItem item, ViewHolder holder) {
        holder.checkBoxIsBought.setOnCheckedChangeListener((buttonView, isChecked) ->
                onCheckedItem(isChecked, item));
        holder.delete.setOnClickListener(v -> onDeleteClicked(item));

    }

    private void animateHolder(int position, ViewHolder viewHolder) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils
                    .loadAnimation(viewHolder.getContext(), R.anim.recycler_anim);
            viewHolder.itemView.startAnimation(animation);

            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return shoppingListItems.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public Context getContext() {
            return itemView.getContext();
        }

        @BindView(R.id.isBought)
        CheckBox checkBoxIsBought;

        @BindView(R.id.item_name)
        TextView textViewIngredientName;

        @BindView(R.id.layout_delete_item)
        View delete;

    }

    public void setShoppingList(List<ShoppingItem> shoppingList) {
        this.shoppingListItems = shoppingList;
    }

    public List<ShoppingItem> getShoppingListItems() {
        return shoppingListItems;
    }

    protected abstract void onCheckedItem(boolean checked, ShoppingItem item);

    protected abstract void onDeleteClicked(ShoppingItem item);
}
