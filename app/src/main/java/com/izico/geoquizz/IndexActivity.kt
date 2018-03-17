/*
 * Created by Salomon ROSSELL on 17/03/18 14:44
 * Copyright (c) 2018. All rights reserved.
 *
 * Last modified 17/03/18 14:44
 */

package com.izico.geoquizz

import android.databinding.BindingAdapter
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import com.izico.geoquizz.adapters.CountriesAdapter
import com.izico.geoquizz.databinding.ActivityIndexBinding
import com.izico.geoquizz.helpers.DatabasesHelper
import com.izico.geoquizz.model.Country

// Custom binding
@BindingAdapter("flagResource")
fun setImageViewResource(imageView: ImageView, resource: Int) {
    imageView.setImageResource(resource)
}

class IndexActivity: AppCompatActivity() {

    private lateinit var countriesRecyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // DATA dataBinding
        val dataBinding = DataBindingUtil.setContentView<ActivityIndexBinding>(this, R.layout.activity_index)
        dataBinding?.executePendingBindings()

        viewManager = LinearLayoutManager(this)
        viewAdapter = CountriesAdapter(retrieveCountries())

        countriesRecyclerView = dataBinding.root.findViewById<RecyclerView>(R.id.countries_recycler_view).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter

        }
    }

    private fun retrieveCountries(): MutableList<Any> {
        val pultus = DatabasesHelper.openDatabase(this)
        val countries = pultus.find(Country())
        pultus.close()

        return countries
    }
}