package com.example.basicnotepad.core.services.repository

import android.content.ContentValues
import android.content.Context
import com.example.basicnotepad.core.services.constants.DataBaseConstants
import com.example.basicnotepad.core.services.model.Notes

class NotesRepository private constructor(context: Context) {

    private var mNoteDataBaseHelper: NotesDataBaseHelper = NotesDataBaseHelper(context)

    companion object {
        private lateinit var repository: NotesRepository

        fun getInstance(context: Context): NotesRepository {
            if (::repository.isInitialized.not()) {
                repository = NotesRepository(context)
            }
            return NotesRepository(context)
        }
    }


    fun save(notes: Notes): Boolean {
        return try {
            val db = mNoteDataBaseHelper.writableDatabase

            val contentValues = ContentValues()
            contentValues.put(DataBaseConstants.NOTE.COLUMNS.DESCRIPTION, notes.description)
            db.insert(DataBaseConstants.NOTE.TABLE_NAME, null, contentValues)
            true
        } catch (e: Exception) { false }
    }

    fun update(notes: Notes): Boolean {
        return try {
            val db = mNoteDataBaseHelper.writableDatabase
            val selection = DataBaseConstants.NOTE.COLUMNS.ID + " = ?"
            val args = arrayOf(notes.id.toString())

            val contentValues = ContentValues()
            contentValues.put(DataBaseConstants.NOTE.COLUMNS.DESCRIPTION, notes.description)

            db.update(DataBaseConstants.NOTE.TABLE_NAME, contentValues, selection, args)
            true
        } catch (e: Exception) { false }
    }

    fun delete(id: Int): Boolean {
        return try {
            val db = mNoteDataBaseHelper.writableDatabase
            val selection = DataBaseConstants.NOTE.COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())

            db.delete(DataBaseConstants.NOTE.TABLE_NAME, selection, args)
            true
        } catch (e: Exception) { false }
    }

    fun getAll(): List<Notes> {
        val list: MutableList<Notes> = ArrayList()
        return list
    }
}