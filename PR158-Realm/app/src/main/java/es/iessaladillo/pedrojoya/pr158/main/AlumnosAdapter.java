package es.iessaladillo.pedrojoya.pr158.main;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import es.iessaladillo.pedrojoya.pr158.R;
import es.iessaladillo.pedrojoya.pr158.db.entities.Alumno;
import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.RealmResults;

public class AlumnosAdapter extends RecyclerView.Adapter<AlumnosAdapter.ViewHolder> implements
        OrderedRealmCollectionChangeListener<RealmResults<Alumno>> {

    private final RealmResults<Alumno> mDatos;
    private OnItemClickListener onItemClickListener;
    private OnEmptyStateListener onEmptyStateListener;

    public AlumnosAdapter(RealmResults<Alumno> datos) {
        mDatos = datos;
        mDatos.addChangeListener(this);
        // Se establece que cada item tiene un id único.
        //setHasStableIds(true);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_main_item, parent, false));
    }

    @Override
    public void onBindViewHolder(AlumnosAdapter.ViewHolder holder, int position) {
        holder.bind(mDatos.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatos.size();
    }

    // Retorna el id único correspondiente al elemento de una posición.
    // Debemos sobrescribirlo para que se realicen las animaciones correctamente,
    // ya que al insertar o eliminar no especificamos posición insertada o eliminada
    // porque Realm lo gestiona automáticamente.
    // Además el adaptador debe tener rv.setHasStableIds(true).
    @Override
    public long getItemId(int position) {
        return mDatos.get(position).getTimestamp();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public void setOnEmptyStateListener(OnEmptyStateListener listener) {
        this.onEmptyStateListener = listener;
    }

    @Override
    public void onChange(RealmResults<Alumno> collection, OrderedCollectionChangeSet changeSet) {
        // `null`  means the async query returns the first time.
        if (changeSet == null) {
            notifyDataSetChanged();
            onEmptyStateListener.onEmptyState(mDatos.isEmpty());
            return;
        }
        // For deletions, the adapter has to be notified in reverse order.
        OrderedCollectionChangeSet.Range[] deletions = changeSet.getDeletionRanges();
        for (int i = deletions.length - 1; i >= 0; i--) {
            OrderedCollectionChangeSet.Range range = deletions[i];
            notifyItemRangeRemoved(range.startIndex, range.length);
        }

        OrderedCollectionChangeSet.Range[] insertions = changeSet.getInsertionRanges();
        for (OrderedCollectionChangeSet.Range range : insertions) {
            notifyItemRangeInserted(range.startIndex, range.length);
        }

        OrderedCollectionChangeSet.Range[] modifications = changeSet.getChangeRanges();
        for (OrderedCollectionChangeSet.Range range : modifications) {
            notifyItemRangeChanged(range.startIndex, range.length);
        }
        // Se notifica al listener.
        onEmptyStateListener.onEmptyState(mDatos.isEmpty());
    }

    public void onDestroy() {
        mDatos.removeChangeListener(this);
    }

    @SuppressWarnings("unused")
    class ViewHolder extends RecyclerView.ViewHolder {

        // El contenedor de vistas para un elemento de la lista debe contener...
        @BindView(R.id.lblNombre)
        public TextView lblNombre;
        @BindView(R.id.lblDireccion)
        public TextView lblDireccion;
        @BindView(R.id.imgFoto)
        public CircleImageView imgAvatar;
        @BindView(R.id.imgAsignaturas)
        public ImageView imgAsignaturas;

        // El constructor recibe la vista-fila.
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        // Escribe el alumno en las vistas.
        public void bind(Alumno alumno) {
            // Se escriben los mDatos en la vista.
            lblNombre.setText(alumno.getNombre());
            lblDireccion.setText(alumno.getDireccion());
            String url = alumno.getUrlFoto();
            Picasso.with(imgAvatar.getContext()).load(url).placeholder(R.drawable.ic_user).error(
                    R.drawable.ic_user).into(imgAvatar);
            itemView.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, mDatos.get(getAdapterPosition()),
                            getAdapterPosition());
                }
            });
            imgAsignaturas.setOnClickListener(
                    v -> onItemClickListener.onItemIconClick(v, mDatos.get(getAdapterPosition()),
                            getAdapterPosition()));
        }

        private String getCadenaAsignaturas(ArrayList<String> nombresAsignaturas) {
            if (nombresAsignaturas.size() > 0) {
                return TextUtils.join(", ", nombresAsignaturas);
            } else {
                return itemView.getContext().getString(R.string.ninguna);
            }
        }

    }

    // Interfaz que debe implementar el listener para cuando se haga click sobre un elemento.
    @SuppressWarnings("UnusedParameters")
    public interface OnItemClickListener {
        void onItemClick(View view, Alumno alumno, int position);

        void onItemIconClick(View view, Alumno alumno, int position);
    }

    public interface OnEmptyStateListener {
        void onEmptyState(boolean empty);
    }

}
