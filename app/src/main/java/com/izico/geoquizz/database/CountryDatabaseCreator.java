package com.izico.geoquizz.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Helper use to create and populate the databases at first launch
 */

public class CountryDatabaseCreator extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    private Context context;
    private String fileToOpen;

    // CREATE SCRIPT
    private static final String CREATE_COUNTRY_TABLE = "CREATE TABLE country ( " +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "shortName VARCHAR(2), " +
            "name VARCHAR(64))";

    public CountryDatabaseCreator(Context context, String name, String fileToOpen) {
        super(context, name, null, DATABASE_VERSION);

        this.context = context;
        this.fileToOpen = fileToOpen;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // create countries table
        sqLiteDatabase.execSQL(CREATE_COUNTRY_TABLE);

        InputStream in;
        BufferedReader reader;
        String line;

        try{
            in = this.context.getAssets().open(this.fileToOpen);
            reader = new BufferedReader(new InputStreamReader(in));
            do{
                line = reader.readLine();
                String INSERT_COUNTRY = line;
                sqLiteDatabase.execSQL(INSERT_COUNTRY);
            } while(line != null);
        } catch (Exception e){
            Log.e("Error", "Error inserting country");
        } finally {
            Log.i("Creation", "countries creation");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Not used here
    }
}
