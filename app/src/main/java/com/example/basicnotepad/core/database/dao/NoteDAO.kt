package com.example.basicnotepad.core.database.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.basicnotepad.core.services.model.Notes

interface NoteDAO {
    @Query("SELECT * FROM Notes ORDER BY id DESC")
    fun getAll(): List<Notes>

    @Insert(onConflict = REPLACE)
    fun save(notes: Notes)

    @Delete
    fun remove(notes: Notes)

    @Query("SELECT * FROM Notes WHERE id = id")
    fun getById(id: Long): Notes?

    @Insert(onConflict = REPLACE)
    fun save(notes: List<Notes>)
}