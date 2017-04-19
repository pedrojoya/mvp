package es.iessaladillo.pedrojoya.pr011.main;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mView;

    public MainPresenter(MainContract.View view) {
        this.mView = view;
    }

    @Override
    public void onViewAttach(MainContract.View view) {
        mView = view;
    }

    @Override
    public void onViewDetach() {
        mView = null;
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public MainContract.View getView() {
        return mView;
    }

    @Override
    public void doAgregar(String nombre) {
        mView.agregarAlumno(nombre.trim());
    }

    @Override
    public void doOnAlumnoClicked(String alumno) {
        mView.eliminarAlumno(alumno);
    }
}
