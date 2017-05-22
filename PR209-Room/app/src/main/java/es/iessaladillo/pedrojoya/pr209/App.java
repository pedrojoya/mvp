package es.iessaladillo.pedrojoya.pr209;

import android.app.Application;
import android.arch.persistence.room.Room;

import es.iessaladillo.pedrojoya.pr209.db.AppDatabase;
import es.iessaladillo.pedrojoya.pr209.db.MigrationFrom0To1;

public class App extends Application {

    private AppDatabase db;

    @Override
    public void onCreate() {
        super.onCreate();
        buildDatabase();
    }

    private void buildDatabase() {
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "instituto.db")
                .addMigrations(new MigrationFrom0To1())
                .build();
    }

    public AppDatabase getDb() {
        return db;
    }

}
