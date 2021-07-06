package com.example.basicnotepad.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.basicnotepad.repository.dao.NotesDAO
import com.example.basicnotepad.repository.model.NoteModel
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class NotesRepositoryTest {
    private lateinit var repository: NotesRepository
    private lateinit var notesDAO: NotesDAO

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        repository = Room.inMemoryDatabaseBuilder(
            context, NotesRepository::class.java
        ).build()
        notesDAO = repository.notesDAO()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        repository.close()
    }

    @Test
    fun writeNoteAndReadInDatabase() {
        val noteModel = NoteModel(
            1,
            "Test Title 2",
            "06-07-2021",
            "Test content description",
            1
        )
        notesDAO.save(noteModel)
        val expected = notesDAO.getById(noteModel.noteId)
        Assert.assertEquals(expected, noteModel)
    }
}