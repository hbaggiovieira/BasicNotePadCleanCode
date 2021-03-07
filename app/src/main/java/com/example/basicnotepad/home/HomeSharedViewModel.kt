package com.example.basicnotepad.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.basicnotepad.core.services.model.Notes
import com.example.basicnotepad.core.services.repository.NotesRepository

class HomeSharedViewModel (application: Application) : AndroidViewModel(application){
    private val mContext = application.applicationContext
    private val mNotesRepository: NotesRepository = NotesRepository.getInstance(mContext)

    private var mSaveNote = MutableLiveData<Boolean>()
    val saveNote: LiveData<Boolean> = mSaveNote


    fun save(description: String) {
        val note = Notes(description)
        mNotesRepository.save(note)
    }
}