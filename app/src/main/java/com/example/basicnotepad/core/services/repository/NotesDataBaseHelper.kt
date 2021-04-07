package com.example.basicnotepad.core.services.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.basicnotepad.core.services.constants.DataBaseConstants

class NotesDataBaseHelper (context: Context) : SQLiteOpenHelper (context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_NOTE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "Notes.db"

        private const val CREATE_TABLE_NOTE = ("create table " + DataBaseConstants.NOTE.TABLE_NAME + " ("
                + DataBaseConstants.NOTE.COLUMNS.ID + " integer primary key autoincrement, "
                + DataBaseConstants.NOTE.COLUMNS.DESCRIPTION + " text, "
                + DataBaseConstants.NOTE.COLUMNS.COLOR_ID + " integer);")
    }
}