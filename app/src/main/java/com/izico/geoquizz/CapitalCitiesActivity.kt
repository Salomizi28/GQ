/*
 * Created by Salomon ROSSELL on 08/01/18 13:41
 * Copyright (c) 2018. All rights reserved.
 *
 * Last modified 08/01/18 13:41
 */

package com.izico.geoquizz

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.izico.geoquizz.helpers.DatabasesHelper
import com.izico.geoquizz.helpers.GameHelper
import com.izico.geoquizz.model.Country
import java.util.*
import kotlin.collections.ArrayList

class CapitalCitiesActivity : AppCompatActivity() {

    private var dataBinding: ViewDataBinding? = null
    private var countries = mutableListOf<Any>()
    private var countryChosen: Country? = null
    private var questionStart: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //old way
        //setContentView(R.layout.activity_capital_cities)

        // DATA dataBinding
        this.dataBinding = DataBindingUtil.setContentView<ViewDataBinding>(this, R.layout.activity_capital_cities)

        retrieveCountries()

        this.questionStart = resources.getString(R.string.question_start) +
                resources.getString(R.string.capital_question)

        val questionsToBeAsked = GameHelper.getQuestionsNumber()
        val propositionsToBeMade = GameHelper.getPropositionsNumber()
        var questionsAsked = 0

        while(questionsAsked < questionsToBeAsked) {
            createQuestion(propositionsToBeMade)
            ++questionsAsked
        }

    }

    private fun createQuestion(propositionsNumber: Int) {
        val random = Random()
        var randomIndex = 0
        var propositionsList = ArrayList<Country>()

        do {
            randomIndex = random.nextInt(this.countries.size)
            val newCountry = this.countries.get(randomIndex) as Country

            if (!propositionsList.contains(newCountry)) {
                propositionsList.add(newCountry)
            }

        } while (propositionsList.size < propositionsNumber)
        

        askQuestion(propositionsList)
    }

    private fun askQuestion(propositionList: ArrayList<Country>) {
        val randomIndex = Random().nextInt(propositionList.size)
        this.countryChosen = propositionList.get(randomIndex)

        this.dataBinding?.setVariable(BR.question, this.questionStart + this.countryChosen?.name +  " ?")
        this.dataBinding?.setVariable(BR.firstProposition, propositionList.get(0))
        this.dataBinding?.setVariable(BR.secondProposition, propositionList.get(1))
        this.dataBinding?.setVariable(BR.thirdProposition, propositionList.get(2))
        this.dataBinding?.setVariable(BR.fourthProposition, propositionList.get(3))
        this.dataBinding?.executePendingBindings()

    }

    private fun retrieveCountries() {
        val pultus = DatabasesHelper.openDatabase(this)
        this.countries = pultus.find(Country())
    }
}
