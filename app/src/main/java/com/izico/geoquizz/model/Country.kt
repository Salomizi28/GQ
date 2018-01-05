package com.izico.geoquizz.model

import ninja.sakib.pultusorm.annotations.AutoIncrement
import ninja.sakib.pultusorm.annotations.PrimaryKey


/**
 * Implementation of a Country
 */

class Country {
    var name: String? = null
    var capitalCity: String? = null
    var code: String? = null
}
