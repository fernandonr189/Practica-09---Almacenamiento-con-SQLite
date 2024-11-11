package com.example.practica09_almacenamientoconsqlite.sql

import android.provider.BaseColumns

object ParkContract {
    object ParkEntry : BaseColumns {
        const val TABLE_NAME = "parks"
        const val COLUMN_NAME_NAME = "park_name"
        const val COLUMN_NAME_SIZE = "park_size"
        const val COLUMN_NAME_LONGITUDE = "park_longitude"
        const val COLUMN_NAME_LATITUDE = "park_latitude"
    }
}