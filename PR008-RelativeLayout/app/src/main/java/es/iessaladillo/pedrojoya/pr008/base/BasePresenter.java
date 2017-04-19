package es.iessaladillo.pedrojoya.pr008.base;

@SuppressWarnings({"EmptyMethod", "unused"})
public interface BasePresenter<V> {
    void onViewAttach(V view);
    void onViewDetach();
    void onDestroy();
    V getView();
}
