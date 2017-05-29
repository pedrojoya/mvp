package es.iessaladillo.pedrojoya.pr209.utils;

import android.support.annotation.LayoutRes;

public abstract class SingleLayoutBindingRecyclerAdapter extends BindingRecyclerAdapter {

    private final int layoutId;

    public SingleLayoutBindingRecyclerAdapter(@LayoutRes int layoutId) {
        this.layoutId = layoutId;
    }

    @Override
    protected int getLayoutIdForPosition(int position) {
        return layoutId;
    }

}
