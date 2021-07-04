package com.example.basicnotepad.core.database.dao

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

    @Query("SELECT * FROM NoteModel WHERE title = :title")
    fun getByTitle(title: String): NoteModel

    @Query("DELETE FROM NoteModel WHERE title = :title")
    fun deleteByTitle(title: String)

    @Insert(onConflict = REPLACE)
    fun save(note: NoteModel)

    @Delete ()
    fun delete(note: NoteModel)
}