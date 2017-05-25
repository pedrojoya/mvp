package es.iessaladillo.pedrojoya.pr209.main;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import es.iessaladillo.pedrojoya.pr209.databinding.ActivityMainItemBinding;
import es.iessaladillo.pedrojoya.pr209.db.entities.Alumno;

public class AlumnosAdapter extends RecyclerView.Adapter<AlumnosAdapter.ViewHolder> {

    private List<Alumno> mDatos = new ArrayList<>();
    private final OnItemClickListener onItemClickListener;
    private final OnEmptyStateListener onEmptyStateListener;

    public AlumnosAdapter(OnItemClickListener onItemClickListener,
            OnEmptyStateListener onEmptyStateListener) {
        this.onItemClickListener = onItemClickListener;
        this.onEmptyStateListener = onEmptyStateListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ActivityMainItemBinding itemBinding = ActivityMainItemBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(itemBinding);
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

        private final ActivityMainItemBinding binding;

        public ViewHolder(ActivityMainItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Alumno alumno) {
            binding.setAlumno(alumno);
            itemView.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, mDatos.get(getAdapterPosition()),
                            getAdapterPosition());
                }
            });
            binding.imgAsignaturas.setOnClickListener(
                    v -> onItemClickListener.onItemIconClick(v, mDatos.get(getAdapterPosition()),
                            getAdapterPosition()));
            binding.executePendingBindings();
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
