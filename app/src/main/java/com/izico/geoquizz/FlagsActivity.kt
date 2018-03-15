/*
 * Created by Salomon ROSSELL on 15/03/18 22:40
 * Copyright (c) 2018. All rights reserved.
 *
 * Last modified 15/03/18 22:40
 */

package com.izico.geoquizz

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import com.izico.geoquizz.databinding.ActivityFlagsBinding
import com.izico.geoquizz.helpers.DatabasesHelper
import com.izico.geoquizz.model.Country

class FlagsActivity: AppCompatActivity() {

    private var countries = mutableListOf<Any>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // DATA BINDING
        val binding = DataBindingUtil.setContentView<ActivityFlagsBinding>(this, R.layout.activity_flags)
        binding.answerHandler = this
        binding.executePendingBindings()

        retrieveCountries()
    }

    fun checkAnswer(view: View) {
        if (view is Button) {
            /*val answer = view.text

            if (answer.equals(this.countryChosen?.capital)) {
                handleSuccess()
            } else {
                handleError()
            }*/
        }
    }

    private fun retrieveCountries() {
        val pultus = DatabasesHelper.openDatabase(this)
        this.countries = pultus.find(Country())
        pultus.close()
    }
}