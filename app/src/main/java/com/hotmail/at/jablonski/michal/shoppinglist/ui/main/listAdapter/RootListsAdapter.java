package com.hotmail.at.jablonski.michal.shoppinglist.ui.main.listAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.hotmail.at.jablonski.michal.shoppinglist.R;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class RootListsAdapter extends RecyclerView.Adapter<RootListsAdapter.ViewHolder> {

    private List<RootItem> rootListsItems;

    private int lastPosition = -1;

    public RootListsAdapter(List<RootItem> rootListsItems) {
        this.rootListsItems = rootListsItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_root_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RootItem item = rootListsItems.get(position);

        initTitle(holder, item);
        initDate(holder, item);
        initClickListener(holder, item);
        initLike(holder, item, position);
        initLetter(holder, item);

        animateHolder(position, holder);
    }

    private void initClickListener(ViewHolder holder, RootItem item) {
        holder.itemView.setOnClickListener(v -> onClick(item, holder));
    }

    private void initTitle(ViewHolder holder, RootItem item) {
        holder.tvShoppingListTitle.setText(item.getName());
    }

    private void initDate(ViewHolder holder, RootItem item) {
        Date d = new Date(item.getTimestamp());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd\nHH:mm");
        String outputDate = sdf.format(d);
        holder.tvDate.setText(outputDate);
    }

    private void initLike(ViewHolder holder, RootItem item, int position) {
        if (item.isArchived()) {
            holder.likeButton.setVisibility(View.GONE);
        } else {
            holder.likeButton.setLiked(item.isLiked());
            holder.likeButton.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {
                    onLikeClick(item, true);
                    item.setLiked(true);
                    rootListsItems.set(position, item);
                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    onLikeClick(item, false);
                    item.setLiked(true);
                    rootListsItems.set(position, item);
                }
            });
        }
    }

    private void initLetter(ViewHolder holder, RootItem item) {
        String firstLetter = String.valueOf(item.getName().charAt(0));
        ColorGenerator generator = ColorGenerator.MATERIAL;
        TextDrawable drawable =
                TextDrawable.builder().buildRound(firstLetter, generator.getRandomColor());

        holder.letterView.setImageDrawable(drawable);

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
        return rootListsItems.size();
    }

    public List<RootItem> getRootListsItems() {
        return rootListsItems;
    }

    public void setRootListsItems(List<RootItem> rootListsItems) {
        this.rootListsItems = rootListsItems;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        Context getContext() {
            return itemView.getContext();
        }

        @BindView(R.id.shopping_list_title)
        public TextView tvShoppingListTitle;

        @BindView(R.id.shopping_list_date)
        public TextView tvDate;

        @BindView(R.id.text_root)
        public View vRootText;

        @BindView(R.id.like_button)
        public LikeButton likeButton;

        @BindView(R.id.gmailitem_letter)
        public ImageView letterView;

    }

    protected abstract void onClick(RootItem rootItem, ViewHolder itemView);

    protected abstract void onLikeClick(RootItem rootItem, boolean like);
}
