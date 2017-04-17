package es.iessaladillo.pedrojoya.pr205.editalumno;

import es.iessaladillo.pedrojoya.pr205.model.Alumno;

public class EditAlumnoPresenter implements EditAlumnoContract.Presenter {

    private EditAlumnoContract.View mView;

    private final Alumno mAlumno;
    private final boolean mMostrarDatosIniciales;

    public EditAlumnoPresenter(EditAlumnoContract.View view, Alumno alumno,
            boolean mostrarDatosIniciales) {
        mView = view;
        mAlumno = alumno;
        mMostrarDatosIniciales = mostrarDatosIniciales;
    }

    @Override
    public void doRetornarDatos(String nombre, int edad) {
        mView.returnIntent(EditAlumnoActivity.createResultIntent(nombre, edad));
    }

    @Override
    public void onViewAttach(EditAlumnoContract.View view) {
        mView = view;
        if (mMostrarDatosIniciales) {
            mView.showDatos(mAlumno);
        }
    }

    @Override
    public void onViewDetach() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public EditAlumnoContract.View getView() {
        return mView;
    }

}
