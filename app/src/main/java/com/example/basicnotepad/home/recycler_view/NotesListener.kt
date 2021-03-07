package com.example.basicnotepad.home.recycler_view

interface NotesListener {

    fun onClick(id: Int)
    fun onDelete(id: Int)
}