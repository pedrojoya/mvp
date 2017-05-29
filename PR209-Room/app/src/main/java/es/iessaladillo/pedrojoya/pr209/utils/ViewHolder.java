package es.iessaladillo.pedrojoya.pr209.utils;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

import com.android.databinding.library.baseAdapters.BR;

public class ViewHolder extends RecyclerView.ViewHolder {

    private final ViewDataBinding binding;

    public ViewHolder(ViewDataBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Object bindingModel) {
        binding.setVariable(BR.bm, bindingModel);
        binding.executePendingBindings();
    }

}