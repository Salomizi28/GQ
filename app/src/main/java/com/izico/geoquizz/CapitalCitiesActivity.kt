/*
 * Created by Salomon ROSSELL on 08/01/18 13:41
 * Copyright (c) 2018. All rights reserved.
 *
 * Last modified 08/01/18 13:41
 */

package com.izico.geoquizz

import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import com.izico.geoquizz.helpers.DatabasesHelper
import com.izico.geoquizz.helpers.GameHelper
import com.izico.geoquizz.model.Country
import ninja.sakib.pultusorm.core.PultusORM
import java.util.*
import kotlin.collections.ArrayList

class CapitalCitiesActivity : AppCompatActivity() {

    private var dataBinding: ViewDataBinding? = null
    private var countries = mutableListOf<Any>()
    private var countryChosen: Country? = null
    private var questionStart: String? = null
    private var questionsToBeAsked = 0
    private var propositionsToBeMade = 0
    private var questionsAsked = 0
    private var score = 0

    private var alreadyAsked = ArrayList<Country>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //old way
        //setContentView(R.layout.activity_capital_cities)

        // DATA dataBinding
        this.dataBinding = DataBindingUtil.setContentView<ViewDataBinding>(this, R.layout.activity_capital_cities)
        this.dataBinding?.setVariable(BR.answerHandler, this)
        this.dataBinding?.executePendingBindings()

        retrieveCountries()

        this.questionStart = resources.getString(R.string.city_game_instructions)

        this.questionsToBeAsked = GameHelper.getQuestionsCount()
        this.propositionsToBeMade = GameHelper.getPropositionsNumber()

        // Launch game
        createQuestion()
    }

    fun checkAnswer(view: View) {
        if (view is Button) {
            val answer = view.text

            if (answer.equals(this.countryChosen?.capital)) {
                handleSuccess()
            } else {
                handleError()
            }
        }
    }

    private fun handleSuccess() {
        Log.i("ANSWER", "RIGHT")

        ++this.score
        checkRemainingQuestions()
    }

    private fun handleError() {
        Log.i("ANSWER", "WRONG")
        checkRemainingQuestions()
    }

    private fun createQuestion() {
        val random = Random()
        var randomIndex: Int
        var propositionsList = ArrayList<Country>()

        do {
            randomIndex = random.nextInt(this.countries.size)
            val newCountry = this.countries.get(randomIndex) as Country

            val alreadyChosenCountry = this.alreadyAsked.contains(newCountry)
            if (!propositionsList.contains(newCountry) && alreadyChosenCountry) {
                propositionsList.add(newCountry)
            }

        } while (propositionsList.size < this.propositionsToBeMade)
        

        askQuestion(propositionsList)
    }

    private fun askQuestion(propositionList: ArrayList<Country>) {
        val randomIndex = Random().nextInt(propositionList.size)
        this.countryChosen = propositionList.get(randomIndex)
        this.alreadyAsked.add(this.countryChosen as Country)

        this.dataBinding?.setVariable(BR.question, this.questionStart + "\n" + this.countryChosen?.name)
        this.dataBinding?.setVariable(BR.firstProposition, propositionList.get(0))
        this.dataBinding?.setVariable(BR.secondProposition, propositionList.get(1))
        this.dataBinding?.setVariable(BR.thirdProposition, propositionList.get(2))
        this.dataBinding?.setVariable(BR.fourthProposition, propositionList.get(3))
        this.dataBinding?.executePendingBindings()

    }

    private fun checkRemainingQuestions() {
        ++this.questionsAsked

        if (this.questionsAsked < this.questionsToBeAsked) {
            createQuestion()
        } else {
            Log.i("QUESTIONS", "End reached")
            val intent = Intent(this, ScoreActivity::class.java)
            intent.putExtra(GameHelper.SCORE_EXTRA, this.score)
            this.startActivity(intent)
        }
    }

    private fun retrieveCountries() {
        //val pultus = DatabasesHelper.openDatabase(this)

        val databasePath = this.filesDir.absolutePath
        val pultus = PultusORM("country.db", databasePath)
        this.countries = pultus.find(Country())
        pultus.close()
    }
}
