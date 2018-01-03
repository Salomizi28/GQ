package com.izico.geoquizz.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.izico.geoquizz.helpers.DatabasesHelper
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

/**
 * Helper use to create and populate the databases at first launch
 */
class CountryDatabaseManager(context: Context, fileToOpen: String) : SQLiteOpenHelper(context, "country.db", null, 2) {

    private val context = context
    private val fileToOpen = fileToOpen

    companion object {
        private val TABLE: String = "country"
        private val ID: String = "_id"
        private val SHORTNAME: String = "shortName"
        private val NAME: String = "name"

        val DATABASE_CREATE = "CREATE TABLE if not exists " + TABLE + " (" +
                "${ID} integer PRIMARY KEY autoincrement," +
                "${SHORTNAME} integer," +
                "${NAME} text"+
                ")"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(DATABASE_CREATE)

        val `in`: InputStream
        val reader: BufferedReader
        var line: String?

        try {
            Log.i("Creation", "countries file reading")
            `in` = this.context.getAssets().open(this.fileToOpen)
            reader = BufferedReader(InputStreamReader(`in`))
            do {
                line = reader.readLine()
                if (line != null) {
                    db?.execSQL(line)
                }
            } while (line != null)

            DatabasesHelper.finalizeInitialization(this.context, db?.path)
        } catch (e: Exception) {
            Log.e("Error", "Error inserting country")
            e.printStackTrace()
        } finally {
            Log.i("Creation", "countries creation")
        }
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}