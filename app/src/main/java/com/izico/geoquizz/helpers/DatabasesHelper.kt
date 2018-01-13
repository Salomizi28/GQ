
/*
 * Created by Salomon ROSSELL on 05/01/18 22:18
 * Copyright (c) 2018. All rights reserved.
 *
 * Last modified 05/01/18 22:18
 */

package com.izico.geoquizz.helpers

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.izico.geoquizz.model.Country
import ninja.sakib.pultusorm.core.PultusORM
import org.json.JSONArray
import java.io.IOException
import java.nio.charset.Charset
import java.sql.SQLException

/**
 * Helper to do all the initialization actions
 */

object DatabasesHelper {

    private val FIRST_LAUNCH_KEY = "firstLaunch"
    private val COUNTRY_DATABASE = "country.db"

    fun init(context: Context)  {
        val sharedPrefs = context.getSharedPreferences(null, Context.MODE_PRIVATE)

        val isFirstLaunchDone = sharedPrefs?.getBoolean(FIRST_LAUNCH_KEY, false)

        if (!isFirstLaunchDone!!) {
            // First launch of the app
            val jsonString = loadJSONFromAssets(context)
            val jsonArray = JSONArray(jsonString)
            val pultus = openDatabase(context)

            try {
                for (index in 0..(jsonArray.length() - 1)) {
                    val newCountry = Gson().fromJson(jsonArray.getString(index), Country::class.java)
                    pultus.save(newCountry)
                }
                pultus.close()
                finalizeInitialization(context)

            } catch (e: SQLException) {
                e.printStackTrace()
            }
        } else {
            /*val pultusORM = openDatabase(context)
            val countries = pultusORM.find(Country())
            for (it in countries) {
                val country = it as Country
                println(country.capital)
                println()
            }*/

            Log.i("INIT", "Database already exists")
        }

    }

    /**
     * Open a database where the records will be saved
     */
    fun openDatabase(context: Context): PultusORM {
        var databasePath = context.getDatabasePath(COUNTRY_DATABASE).toString()
        databasePath = databasePath.substring(0, databasePath.lastIndexOf(("/")))

        /*try {
            return PultusORM(COUNTRY_DATABASE, databasePath)
        } catch (e: SQLException) {
            e.printStackTrace()*/
            databasePath = context.filesDir.absolutePath
            return PultusORM(COUNTRY_DATABASE, databasePath)
        //}
    }

    /**
     * Load the assets' file and parse it
     */
    private fun loadJSONFromAssets(context: Context): String {
        var json = ""
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

    /**
     * Persist the first launch indicator
     */
    private fun finalizeInitialization(context: Context) {
        val sharedPrefs = context.getSharedPreferences(null, Context.MODE_PRIVATE)
        val editor = sharedPrefs?.edit();
        editor?.putBoolean(FIRST_LAUNCH_KEY, true)
        editor?.commit()
    }
}
