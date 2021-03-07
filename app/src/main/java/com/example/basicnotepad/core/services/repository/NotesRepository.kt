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

    fun get(id: Int): Notes? {
        var notes: Notes? = null
        return try {
            val db = mNoteDataBaseHelper.readableDatabase

            val projection = arrayOf(DataBaseConstants.NOTE.COLUMNS.DESCRIPTION)
            val selection = DataBaseConstants.NOTE.COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())

            val cursor = db.query(
                DataBaseConstants.NOTE.TABLE_NAME,
                projection,
                selection,
                args,
                null, null, null, null
            )

            if (cursor != null && cursor.count > 0) {
                cursor.moveToFirst()

                val description =
                    cursor.getString(cursor.getColumnIndex(DataBaseConstants.NOTE.COLUMNS.DESCRIPTION))
                notes = Notes(description, id)
            }
            cursor?.close()
            notes
        } catch (e: Exception) {
            notes
        }
    }


    fun save(notes: Notes): Boolean {
        return try {
            val db = mNoteDataBaseHelper.writableDatabase

            val contentValues = ContentValues()
            contentValues.put(DataBaseConstants.NOTE.COLUMNS.DESCRIPTION, notes.description)
            db.insert(DataBaseConstants.NOTE.TABLE_NAME, null, contentValues)
            true
        } catch (e: Exception) {
            false
        }
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
        } catch (e: Exception) {
            false
        }
    }

    fun delete(id: Int): Boolean {
        return try {
            val db = mNoteDataBaseHelper.writableDatabase
            val selection = DataBaseConstants.NOTE.COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())

            db.delete(DataBaseConstants.NOTE.TABLE_NAME, selection, args)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun getAll(): List<Notes> {
        val list: MutableList<Notes> = ArrayList()
        return try {
            val db = mNoteDataBaseHelper.readableDatabase

            val projection = arrayOf(
                DataBaseConstants.NOTE.COLUMNS.DESCRIPTION,
                DataBaseConstants.NOTE.COLUMNS.ID
            )

            val cursor = db.query(
                DataBaseConstants.NOTE.TABLE_NAME,
                projection,
                null,
                null,
                null, null, null, null
            )

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.NOTE.COLUMNS.ID))
                    val description =
                        cursor.getString(cursor.getColumnIndex(DataBaseConstants.NOTE.COLUMNS.DESCRIPTION))
                    val notes = Notes(description, id)
                    list.add(notes)
                }
            }
            cursor?.close()
            list
        } catch (e: Exception) {
            list
        }
    }
}