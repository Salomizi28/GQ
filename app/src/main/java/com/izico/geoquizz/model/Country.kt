
/*
 * Created by Salomon ROSSELL on 05/01/18 22:18
 * Copyright (c) 2018. All rights reserved.
 *
 * Last modified 05/01/18 19:50
 */

package com.izico.geoquizz.model

import ninja.sakib.pultusorm.annotations.AutoIncrement
import ninja.sakib.pultusorm.annotations.PrimaryKey

/**
 * Implementation of a Country
 */

class Country {
    @PrimaryKey
    @AutoIncrement
    var id: Int = 0

    var name: String? = null
    var capital: String? = null
    var code2: String? = null
    var code3: String? = null

}
