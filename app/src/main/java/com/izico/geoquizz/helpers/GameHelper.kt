/*
 * Created by Salomon ROSSELL on 11/01/18 21:20
 * Copyright (c) 2018. All rights reserved.
 *
 * Last modified 11/01/18 21:20
 */

package com.izico.geoquizz.helpers

/**
 * Helper that exposes settings variables
 */

object GameHelper {

    val SCORE_EXTRA = "score"

    private var questionsCount = 10
    private var propositionsNumber = 4

    fun getPropositionsNumber(): Int {
        return propositionsNumber
    }

    fun getQuestionsCount(): Int {
        return questionsCount
    }

}