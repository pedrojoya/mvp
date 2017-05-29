package es.iessaladillo.pedrojoya.pr209.utils;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public abstract class BindingRecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {

    private List<Object> mData = new ArrayList<>();
    private OnItemClickListener onItemClickListener;
    private OnEmptyStateListener onEmptyStateListener;

    public void setData(@NonNull List<Object> data) {
        mData = data;
        notifyDataSetChanged();
        if (onEmptyStateListener != null) {
            onEmptyStateListener.onEmptyState(mData.isEmpty());
        }
    }

    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, viewType, parent, false);
        ViewHolder viewHolder = new ViewHolder(binding);
        binding.getRoot().setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, mData.get(viewHolder.getAdapterPosition()),
                        viewHolder.getAdapterPosition());
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mData.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return getLayoutIdForPosition(position);
    }

    protected abstract int getLayoutIdForPosition(int position);

    public interface OnItemClickListener {
        void onItemClick(View view, Object item, int position);
    }

    public interface OnEmptyStateListener {
        void onEmptyState(boolean empty);
    }

}