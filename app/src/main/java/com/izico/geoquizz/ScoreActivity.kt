/*
 * Created by Salomon ROSSELL on 13/01/18 20:30
 * Copyright (c) 2018. All rights reserved.
 *
 * Last modified 13/01/18 20:30
 */

package com.izico.geoquizz

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.izico.geoquizz.helpers.GameHelper
import com.izico.geoquizz.databinding.ActivityScoreBinding

class ScoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // DATA dataBinding
        val dataBinding = DataBindingUtil.setContentView<ActivityScoreBinding>(this, R.layout.activity_score)

        // retrieve score
        val score = this.intent?.extras?.get(GameHelper.SCORE_EXTRA) as Int

        dataBinding?.scoreActivityButtonHandler = this
        dataBinding?.commentText = computeComment(score)
        dataBinding?.finalScore = score.toString()
        dataBinding?.executePendingBindings()
    }

    fun onRestartGame(view: View) {
        this.startActivity(Intent(this, CapitalCitiesActivity::class.java))
        finish()
    }

    private fun computeComment(score: Int): String {

        return when (score) {
            in 0 until 10 -> this.resources.getString(R.string.score_lowest)
            in 11 until 16 -> this.resources.getString(R.string.score_low)
            in 17 until 25 ->  this.resources.getString(R.string.score_average)
            in 26 until 37 ->  this.resources.getString(R.string.score_high)
            !in 0 until 37 ->  this.resources.getString(R.string.score_highest)
            else -> this.resources.getString(R.string.score_default)
        }
    }

    override fun onBackPressed() {
        this.startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }
}
