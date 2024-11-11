package com.example.practica09_almacenamientoconsqlite.sql

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class ParksDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private val SQL_CREATE_ENTRIES = "CREATE TABLE ${ParkContract.ParkEntry.TABLE_NAME} (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "${ParkContract.ParkEntry.COLUMN_NAME_NAME} TEXT," +
            "${ParkContract.ParkEntry.COLUMN_NAME_LATITUDE} DOUBLE," +
            "${ParkContract.ParkEntry.COLUMN_NAME_LONGITUDE} DOUBLE," +
            "${ParkContract.ParkEntry.COLUMN_NAME_SIZE} DOUBLE)"

    private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${ParkContract.ParkEntry.TABLE_NAME}"


    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "Parks.db"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

}