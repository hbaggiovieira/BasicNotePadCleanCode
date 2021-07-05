package com.example.basicnotepad.repository.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.basicnotepad.repository.model.NoteModel

@Dao
interface NotesDAO {
    @Query("SELECT * FROM NoteModel ORDER BY date DESC")
    fun getAll(): List<NoteModel>

    @Query("SELECT * FROM NoteModel ORDER BY date ASC")
    fun getAllAsc(): List<NoteModel>

    @Query("SELECT * FROM NoteModel WHERE noteId = :noteId")
    fun getById(noteId: Int): NoteModel

    @Insert(onConflict = REPLACE)
    fun save(note: NoteModel)

    @Delete ()
    fun delete(note: NoteModel)
}