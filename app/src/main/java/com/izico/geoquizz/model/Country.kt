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
