package com.example.basicnotepad.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.basicnotepad.core.services.model.Notes
import com.example.basicnotepad.core.services.repository.NotesRepository

class HomeSharedViewModel(application: Application) : AndroidViewModel(application) {
    private val mContext = application.applicationContext
    private val mNotesRepository: NotesRepository = NotesRepository.getInstance(mContext)

    private var mSaveNote = MutableLiveData<Boolean>()
    val saveNote: LiveData<Boolean> = mSaveNote

    private val mNoteList = MutableLiveData<List<Notes>>()
    val noteList: LiveData<List<Notes>> = mNoteList

    private var mNote = MutableLiveData<Notes>()
    val note: LiveData<Notes> = mNote

    fun save(isNew: Boolean, id: Int, description: String) {
        val note = Notes(description, id)
        if (isNew) {
            mSaveNote.value = mNotesRepository.save(note)
        } else {
            mSaveNote.value = mNotesRepository.update(note)
        }
    }

    fun delete (id: Int) {
        mNotesRepository.delete(id)
    }

    fun load() {
        mNoteList.value = mNotesRepository.getAll()
    }

    fun loadNote(id: Int) {
        mNote.value = mNotesRepository.get(id)
    }
}