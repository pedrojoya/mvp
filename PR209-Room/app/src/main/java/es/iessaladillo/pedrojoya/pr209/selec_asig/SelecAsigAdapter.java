package es.iessaladillo.pedrojoya.pr209.selec_asig;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import java.util.List;

import es.iessaladillo.pedrojoya.pr209.db.entities.SelecAsigTuple;

public class SelecAsigAdapter extends ArrayAdapter<SelecAsigTuple> {

    private final List<SelecAsigTuple> mData;

    public SelecAsigAdapter(@NonNull Context context, List<SelecAsigTuple> data) {
        super(context, android.R.layout.simple_list_item_multiple_choice, data);
        mData = data;
    }

    public void setData(List<SelecAsigTuple> data) {
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

}
