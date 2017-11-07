package edu.orangecoastcollege.cs273.petprotector;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "PetProtector";
    private static final String DATABASE_TABLE = "Pets";
    private static final int DATABASE_VERSION = 1;

    private static final String[] FIELD_NAMES = {"_id", "name", "details", "phone", "image"};
    private static final String[] FIELD_TYPES = {"INTEGER PRIMARY KEY", "TEXT", "TEXT", "TEXT", "TEXT"};

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Creates the database by creating the table with the fields
     *
     * @param sqLiteDatabase database
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        StringBuilder sql = new StringBuilder("CREATE TABLE ");
        sql.append(DATABASE_TABLE).append("(");
        for (int i = 0; i < FIELD_NAMES.length; i++)
            sql.append(FIELD_NAMES[i]).append(" ").append(FIELD_TYPES[i]).append((i < FIELD_NAMES.length - 1) ? "," : ")");
        sqLiteDatabase.execSQL(sql.toString());
    }

    /**
     * Upgrades the database by dropping the table and creating a new table
     * @param sqLiteDatabase database
     * @param i old version
     * @param i1 new version
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(sqLiteDatabase);
    }

    /**
     * Adds a new pet to the database
     * @param pet Pet to be added
     * @return the id of the database entry
     */
    public int addPet(Pet pet) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(FIELD_NAMES[1], pet.getName());
        values.put(FIELD_NAMES[2], pet.getDetails());
        values.put(FIELD_NAMES[3], pet.getPhone());
        values.put(FIELD_NAMES[4], pet.getImage().toString());

        int id = (int) database.insert(DATABASE_TABLE, null, values);

        database.close();
        return id;
    }

    /**
     * Fetches a list of all the pets that are in the database
     * @return all pets
     */
    public ArrayList<Pet> getAllPets() {
        ArrayList<Pet> petsList = new ArrayList<>();

        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.query(DATABASE_TABLE, FIELD_NAMES, null, null, null, null, null);

        if (cursor.moveToFirst())
            do
                petsList.add(new Pet(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), Uri.parse(cursor.getString(4))));
            while (cursor.moveToNext());

        cursor.close();
        database.close();
        return petsList;
    }

    /**
     * Deletes all the pets that are in the database
     */
    public void deleteAllPets() {
        onUpgrade(getWritableDatabase(), 1, 1);
    }
}
