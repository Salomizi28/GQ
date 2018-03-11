
/*
 * Created by Salomon ROSSELL on 05/01/18 22:18
 * Copyright (c) 2018. All rights reserved.
 *
 * Last modified 05/01/18 22:06
 */

package com.izico.geoquizz

import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.izico.geoquizz.helpers.DatabasesHelper

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // no data binding
        //setContentView(R.layout.activity_home)

        /*setSupportActionBar(toolbar)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }*/

        // DATA BINDING
        val binding = DataBindingUtil.setContentView<ViewDataBinding>(this, R.layout.content_home)
        binding.setVariable(BR.buttonHandler, this)
        binding.executePendingBindings()

        DatabasesHelper.init(this)
    }

    fun onGameChosen(view: View) {
        when (view.id) {
            R.id.capitals_game_button -> launchCapitalCityGame()
        }
    }

    private fun launchCapitalCityGame() {
        this.startActivity(Intent(this, CapitalCitiesActivity::class.java))
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_WRITE_PERMISSION -> validatePermissions(grantResults)
            else -> {
                finish()
            }
        }
    }

    fun validatePermissions(grantResults: IntArray) {
        if (grantResults.isNotEmpty() && grantResults[0] == PermissionChecker.PERMISSION_GRANTED) {
            DatabasesHelper.init(this)
        } else {
            finish()
        }
    }*/
}
