package es.iessaladillo.pedrojoya.pr209;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.facebook.stetho.Stetho;

import es.iessaladillo.pedrojoya.pr209.db.AppDatabase;
import es.iessaladillo.pedrojoya.pr209.db.MigrationFrom0To1;
import es.iessaladillo.pedrojoya.pr209.db.entities.Asignatura;
import es.iessaladillo.pedrojoya.pr209.repository.RepositoryImpl;

public class App extends Application {

    private AppDatabase db;
    private RepositoryImpl repository;

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        buildDatabase();
        buildRepository();
    }

    private void buildDatabase() {
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "instituto.db")
                .allowMainThreadQueries()
                .addMigrations(new MigrationFrom0To1())
                .build();
        addInitialAsignaturas();
    }

    private void addInitialAsignaturas() {
        db.beginTransaction();
        try {
            db.asignaturaDao().insert(new Asignatura("PMDMO", "Android"));
            db.asignaturaDao().insert(new Asignatura("PSPRO", "Multihilo"));
            db.asignaturaDao().insert(new Asignatura("HLC", "Horas de Libre Configuración"));
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    private void buildRepository() {
        repository = new RepositoryImpl(db);
    }

    public RepositoryImpl getRepository() {
        return repository;
    }

}
