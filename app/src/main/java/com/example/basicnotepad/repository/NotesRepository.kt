package com.example.basicnotepad.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.basicnotepad.repository.dao.NotesDAO
import com.example.basicnotepad.repository.model.NoteModel

@Database(entities = [NoteModel::class], version = 1)
abstract class NotesRepository : RoomDatabase() {

    abstract fun notesDAO(): NotesDAO

    companion object {
        private lateinit var INSTANCE: NotesRepository

        fun getDatabase(context: Context): NotesRepository {
            if (!Companion::INSTANCE.isInitialized) {
                synchronized(NotesRepository::class.java) {
                    INSTANCE = Room.databaseBuilder(context, NotesRepository::class.java, "notes")
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}