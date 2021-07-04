package com.example.basicnotepad.home.recycler

import com.example.basicnotepad.repository.model.NoteModel

interface NotesListener {

    fun onClick(noteModel: NoteModel)
    fun onDelete(noteModel: NoteModel)
}