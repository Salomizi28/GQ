/*
 * Created by Salomon ROSSELL on 17/03/18 14:53
 * Copyright (c) 2018. All rights reserved.
 *
 * Last modified 17/03/18 14:53
 */

package com.izico.geoquizz.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.izico.geoquizz.databinding.CountryIndexItemBinding
import com.izico.geoquizz.model.Country

/**
 * Adapter that handles countries items
 */
class CountriesAdapter(private val countriesDataSet: MutableList<Any>) :
        RecyclerView.Adapter<CountriesAdapter.CountryItemViewHolder>() {

    /**
     * Create new views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountriesAdapter.CountryItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val dataBinding = CountryIndexItemBinding.inflate(layoutInflater, parent, false)
        return CountryItemViewHolder(dataBinding)
    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: CountryItemViewHolder, position: Int) {
        // get element from your dataset at this position
        // replace the contents of the view with that element
        holder.bind(countriesDataSet[position] as Country)
    }

    /**
     * Return the size of your dataSet (invoked by the layout manager)
     */
    override fun getItemCount() = countriesDataSet.size

    /**
     * Provide a reference to the views for each data item
     */
    class CountryItemViewHolder(private val binding: CountryIndexItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private val TAG = CountryItemViewHolder::class.java.simpleName
        private var viewBinding: CountryIndexItemBinding = binding

        fun bind(country: Country) {
            viewBinding.country = country
        }
    }
}