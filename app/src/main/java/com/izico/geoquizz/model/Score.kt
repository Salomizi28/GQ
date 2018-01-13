
/*
 * Created by Salomon ROSSELL on 05/01/18 22:18
 * Copyright (c) 2018. All rights reserved.
 *
 * Last modified 03/01/18 19:59
 */

package com.izico.geoquizz.model

import ninja.sakib.pultusorm.annotations.AutoIncrement
import ninja.sakib.pultusorm.annotations.PrimaryKey

/**
 * Implementation of a Score
 */

class Score {
    @PrimaryKey
    @AutoIncrement
    var id: Int = 0

    var userName: String? = null
    var value: Long = 0
}
