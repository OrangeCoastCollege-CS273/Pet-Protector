package edu.orangecoastcollege.cs273.petprotector;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "PetProtector";
    private static final String DATABASE_TABLE = "Pets";
    private static final int DATABASE_VERSION = 1;

    private static final String[] FIELD_NAMES = {"_id", "name", "details", "phone", "image"};
    private static final String[] FIELD_TYPES = {"INTEGER PRIMARY KEY", "TEXT", "TEXT", "TEXT", "TEXT"};

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        StringBuilder sql = new StringBuilder("CREATE TABLE ");
        sql.append(DATABASE_TABLE).append("(");
        for (int i = 0; i < FIELD_NAMES.length; i++)
            sql.append(FIELD_NAMES[i]).append(" ").append(FIELD_TYPES[i]).append((i < FIELD_NAMES.length - 1) ? "," : ")");
        sqLiteDatabase.execSQL(sql.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(sqLiteDatabase);
    }

}
