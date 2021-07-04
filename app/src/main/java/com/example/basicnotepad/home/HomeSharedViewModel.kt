package com.example.basicnotepad.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.basicnotepad.repository.NotesRepository
import com.example.basicnotepad.repository.model.NoteModel

class HomeSharedViewModel() : ViewModel() {
    private val mRepository = NotesRepository


    private val mList = MutableLiveData<List<NoteModel>>()
    var notes: LiveData<List<NoteModel>> = mList

    fun list(context: Context) {
        mList.postValue(mRepository.getDatabase(context).notesDAO().getAll())
    }
}