package com.example.basicnotepad.ui.home.recycler

import com.example.basicnotepad.repository.model.NoteModel

interface NotesListener {

    fun onClick(noteModel: NoteModel)
    fun onDelete(noteModel: NoteModel)
}