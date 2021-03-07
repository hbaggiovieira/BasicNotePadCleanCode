package com.example.basicnotepad.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.basicnotepad.core.database.dao.NoteDAO
import com.example.basicnotepad.core.services.model.Notes

private const val DATABASE_NAME = "notes.db"

@Database(entities = [Notes::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    abstract val noteDAO: NoteDAO

    companion object {
        fun getInstance(context: Context): AppDataBase {
            return Room.databaseBuilder(context, AppDataBase::class.java, DATABASE_NAME).build()
        }
    }
}