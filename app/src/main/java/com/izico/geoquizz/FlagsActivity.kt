/*
 * Created by Salomon ROSSELL on 15/03/18 22:40
 * Copyright (c) 2018. All rights reserved.
 *
 * Last modified 15/03/18 22:40
 */

package com.izico.geoquizz

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.databinding.BindingAdapter
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ImageButton
import com.izico.geoquizz.databinding.ActivityFlagsBinding
import com.izico.geoquizz.helpers.DatabasesHelper
import com.izico.geoquizz.helpers.GameHelper
import com.izico.geoquizz.model.Country
import com.izico.geoquizz.widget.LifeRemainingView
import java.util.*
import kotlin.collections.ArrayList

// Custom binding
@BindingAdapter("flagResource")
fun setImageButtonResource(imageButton: ImageButton, resource: Int) {
    imageButton.setImageResource(resource)
}

class FlagsActivity: AppCompatActivity() {

    private var dataBinding: ActivityFlagsBinding? = null
    private var countries = mutableListOf<Any>()
    private var countryChosen: Country? = null
    private var remainingLife = 0
    private var propositionsToBeMade = 0
    private var score = 0
    private var lifeRemainingView: LifeRemainingView? = null

    private var alreadyAsked = ArrayList<Country>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.remainingLife = GameHelper.getRemainingLife()
        this.propositionsToBeMade = GameHelper.getPropositionsNumber()

        // DATA BINDING
        this.dataBinding = DataBindingUtil.setContentView<ActivityFlagsBinding>(this, R.layout.activity_flags)
        this.dataBinding?.answerHandler = this
        this.dataBinding?.executePendingBindings()

        retrieveCountries()

        // Keep the lives views
        this.lifeRemainingView =  this.dataBinding?.root?.findViewById<LifeRemainingView>(R.id.lifeRemainingView)

        // Launch game
        createQuestion()
    }

    private fun createQuestion() {
        val random = Random()
        var randomIndex: Int
        var propositionsList = ArrayList<Country>()

        do {
            randomIndex = random.nextInt(this.countries.size)
            val newCountry = this.countries.get(randomIndex) as Country

            val alreadyChosenCountry = this.alreadyAsked.contains(newCountry)
            if (!propositionsList.contains(newCountry) && !alreadyChosenCountry) {
                propositionsList.add(newCountry)
            }

        } while (propositionsList.size < this.propositionsToBeMade)


        askQuestion(propositionsList)
    }

    private fun askQuestion(propositionList: ArrayList<Country>) {
        val randomIndex = Random().nextInt(propositionList.size)
        this.countryChosen = propositionList[randomIndex]
        this.alreadyAsked.add(this.countryChosen as Country)

        this.dataBinding?.question = this.countryChosen?.name
        this.dataBinding?.firstAnswer = propositionList[0]
        this.dataBinding?.secondAnswer = propositionList[1]
        this.dataBinding?.thirdAnswer = propositionList[2]
        this.dataBinding?.fourthAnswer = propositionList[3]
        this.dataBinding?.executePendingBindings()

    }

    private fun checkRemainingLife() {
        --this.remainingLife

        this.lifeRemainingView?.deleteLife()

        if (this.remainingLife >= 0) {
            createQuestion()
        } else {
            Log.i("QUESTIONS", "End reached")
            val intent = Intent(this, ScoreActivity::class.java)
            intent.putExtra(GameHelper.SCORE_EXTRA, this.score)
            this.startActivity(intent)
            finish()
        }
    }

    fun checkAnswer(answer: Country) {
        if (answer.capital == this.countryChosen?.capital) {
            handleSuccess()
        } else {
            handleError()
        }
    }

    private fun handleSuccess() {
        Log.i("ANSWER", "RIGHT")

        ++this.score
        createQuestion()
    }

    private fun handleError() {
        Log.i("ANSWER", "WRONG")
        checkRemainingLife()
    }

    private fun retrieveCountries() {
        val pultus = DatabasesHelper.openDatabase(this)
        this.countries = pultus.find(Country())
        pultus.close()
    }

    override fun onBackPressed() {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setTitle(resources.getString(R.string.dialog_title_exit))
        dialogBuilder.setMessage(resources.getString(R.string.dialog_message_exit))
        dialogBuilder.setNegativeButton(resources.getString(R.string.dialog_default_negative_text), null)
        dialogBuilder.setPositiveButton(resources.getString(R.string.dialog_default_positive_text),
                DialogInterface.OnClickListener { _, _ -> goBackHome() })

        dialogBuilder.show()
    }

    private fun goBackHome() {
        this.startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }
}