package es.iessaladillo.pedrojoya.pr209.main;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import es.iessaladillo.pedrojoya.pr209.R;
import es.iessaladillo.pedrojoya.pr209.db.entities.Alumno;

public class AlumnosAdapter extends RecyclerView.Adapter<AlumnosAdapter.ViewHolder> {

    private List<Alumno> mDatos = Collections.emptyList();
    private OnItemClickListener onItemClickListener;
    private OnEmptyStateListener onEmptyStateListener;

    public AlumnosAdapter(OnItemClickListener onItemClickListener,
            OnEmptyStateListener onEmptyStateListener) {
        this.onItemClickListener = onItemClickListener;
        this.onEmptyStateListener = onEmptyStateListener;
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

    public void setData(@NonNull List<Alumno> alumnos) {
        mDatos = alumnos;
        notifyDataSetChanged();
        onEmptyStateListener.onEmptyState(mDatos.isEmpty());
    }

    public Alumno getItem(int position) {
        return mDatos.get(position);
    }

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
