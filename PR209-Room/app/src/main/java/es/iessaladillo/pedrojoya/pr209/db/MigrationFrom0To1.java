package es.iessaladillo.pedrojoya.pr209.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.migration.Migration;

public class MigrationFrom0To1 extends Migration {

    public MigrationFrom0To1() {
        super(0, 1);
    }

    @Override
    public void migrate(SupportSQLiteDatabase database) {
        database.execSQL("INSERT INTO ASIGNATURA VALUES('PMDMO', 'Android'");
        database.execSQL("INSERT INTO ASIGNATURA VALUES('PSPRO', 'Multihilo'");
        database.execSQL("INSERT INTO ASIGNATURA VALUES('HLC', 'Horas de libre configuración'");
    }

}
