package com.example.practica09_almacenamientoconsqlite.sql

import android.provider.BaseColumns

object RangerContract {
    object RangerEntry: BaseColumns {
        const val TABLE_NAME = "rangers"
        const val COLUMN_NAME_NAME = "ranger_name"
        const val COLUMN_NAME_AGE = "ranger_age"
        const val COLUMN_NAME_TENURE = "ranger_tenure"
        const val COLUMN_NAME_GENDER = "ranger_gender"
    }
}