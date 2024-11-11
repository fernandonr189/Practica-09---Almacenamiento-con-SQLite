package com.example.practica09_almacenamientoconsqlite.sql

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.example.practica09_almacenamientoconsqlite.sql.ParksDbHelper.Companion.DATABASE_VERSION

class RangerDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){


    private val SQL_CREATE_ENTRIES = "CREATE TABLE ${RangerContract.RangerEntry.TABLE_NAME} (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "${RangerContract.RangerEntry.COLUMN_NAME_NAME} TEXT," +
            "${RangerContract.RangerEntry.COLUMN_NAME_AGE} INTEGER," +
            "${RangerContract.RangerEntry.COLUMN_NAME_TENURE} INTEGER," +
            "${RangerContract.RangerEntry.COLUMN_NAME_GENDER} TEXT)"

    private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${RangerContract.RangerEntry.TABLE_NAME}"

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "Rangers.db"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }
}