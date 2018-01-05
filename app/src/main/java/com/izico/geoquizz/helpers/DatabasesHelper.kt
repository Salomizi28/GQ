package com.izico.geoquizz.helpers

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.izico.geoquizz.database.CountryDatabaseManager
import com.izico.geoquizz.model.Country
import ninja.sakib.pultusorm.core.PultusORM
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.Charset

/**
 * Helper to do all the initialization actions
 */

object DatabasesHelper {

    private val FIRST_LAUNCH_KEY = "firstLaunch"
    private val DATABASES_PATH = "databsesPath"

    private var sharedPrefs: SharedPreferences? = null
    private var countryManager: CountryDatabaseManager? = null

    /*fun init(context: Context) {
        this.sharedPrefs = context.getSharedPreferences(null, Context.MODE_PRIVATE)

        val isFirstLaunchDone = this.sharedPrefs?.getBoolean(FIRST_LAUNCH_KEY, false)

        if (!isFirstLaunchDone!!) {
            this.countryManager = CountryDatabaseManager(context, "countries_fr.txt")
        } else {
            Log.i("Database init", "Initialization already done")

            /*val databasePath = this.sharedPrefs?.getString(DATABASES_PATH, null)
            //val appPath = context.getDatabasePath("country.db").toString()  // Output : /data/data/application_package_name/files/

            if (databasePath != null) {
                val pultusORM: PultusORM = PultusORM("country.db", databasePath)

                val countries = pultusORM.find(Country())

                for (it in countries) {
                    val student = it as Country
                    println(student.name)
                    println()
                }
            }*/
        }
    }*/

    fun init(context: Context)  {
        val jsonString = loadJSONFromAssets(context)

        val jsonArray = JSONArray(jsonString)



    }

    fun loadJSONFromAssets(context: Context): String {
        var json: String? = null
        try {
            val stream = context.assets.open("countries_fr.json")
            val size = stream.available()
            val buffer = ByteArray(size)

            stream.read(buffer)
            stream.close()
            json = String(buffer, Charset.forName("UTF-8"))


        } catch (ex: IOException) {
            ex.printStackTrace()
        } finally {
            return json.toString()
        }
    }

    fun finalizeInitialization(context: Context, dbPath: String?) {
        val editor = this.sharedPrefs?.edit();
        editor?.putBoolean(FIRST_LAUNCH_KEY, true)

        if (dbPath != null) {
            val formattedPath = dbPath.substring(0, dbPath.lastIndexOf("/"))
            editor?.putString(DATABASES_PATH, formattedPath)
        }

        editor?.commit()
    }
}
