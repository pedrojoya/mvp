package es.iessaladillo.pedrojoya.pr158.detalle;

import android.text.TextUtils;

import es.iessaladillo.pedrojoya.pr158.db.entities.Alumno;
import es.iessaladillo.pedrojoya.pr158.repository.Repository;

public class DetallePresenter implements DetalleContract.Presenter {

    private DetalleContract.View mView;
    private final DetalleContract.Usecase mUseCase;

    public DetallePresenter(DetalleContract.View view) {
        this.mView = view;
        this.mUseCase = new Repository();
    }

    @Override
    public void onViewAttach(DetalleContract.View view) {
        mView = view;
    }

    @Override
    public void onViewDetach() {
        mView = null;
    }

    @Override
    public void onDestroy() {
        mUseCase.onDestroy();
    }

    @Override
    public DetalleContract.View getView() {
        return mView;
    }

    @Override
    public void loadAlumno(String idAlumno) {
        if (!TextUtils.isEmpty(idAlumno)) {
            mView.showAlumno(mUseCase.getAlumno(idAlumno));
        } else {
            mView.showNewAlumno();
        }
    }

    @Override
    public void doGuardar(String idAlumno, Alumno alumno, String nombre, String direccion,
            String urlFoto) {
        if (TextUtils.isEmpty(idAlumno)) {
            alumno.setNombre(nombre);
            alumno.setDireccion(direccion);
            alumno.setUrlFoto(urlFoto);
            mUseCase.addAlumno(alumno);
        } else {
            mUseCase.updateAlumno(alumno, nombre, direccion, urlFoto);
        }
    }

    @Override
    public void selectAsignaturas(String idAlumno, Alumno mAlumno) {
        mView.navigateToAsignaturasAlumnoActivity(idAlumno);
    }

}
