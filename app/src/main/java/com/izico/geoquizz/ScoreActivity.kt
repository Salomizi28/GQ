/*
 * Created by Salomon ROSSELL on 13/01/18 20:30
 * Copyright (c) 2018. All rights reserved.
 *
 * Last modified 13/01/18 20:30
 */

package com.izico.geoquizz

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.izico.geoquizz.helpers.GameHelper

class ScoreActivity : AppCompatActivity() {

    private var dataBinding: ViewDataBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // DATA dataBinding
        this.dataBinding = DataBindingUtil.setContentView<ViewDataBinding>(this, R.layout.activity_score)

        // retrieve score
        val score = this.intent?.extras?.get(GameHelper.SCORE_EXTRA) as Int

        this.dataBinding?.setVariable(BR.comment, computeComment(score))
        this.dataBinding?.setVariable(BR.finalScore, score)
        this.dataBinding?.executePendingBindings()
    }

    private fun computeComment(score: Int): String {
        val maxScore = GameHelper.getQuestionsCount()

        return when (score) {
            in 0 until (maxScore / 10).toInt() -> this.resources.getString(R.string.score_lowest)
            in (maxScore / 10).toInt() until (maxScore / 2).toInt() -> this.resources.getString(R.string.score_low)
            in (maxScore / 2).toInt() until (maxScore / 1.25).toInt() ->  this.resources.getString(R.string.score_average)
            in (maxScore / 1.25).toInt() until maxScore - 1 ->  this.resources.getString(R.string.score_high)
            maxScore ->  this.resources.getString(R.string.score_highest)
            else -> this.resources.getString(R.string.score_default)
        }
    }
}
