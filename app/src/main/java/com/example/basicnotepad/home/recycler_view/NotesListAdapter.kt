package com.example.basicnotepad.home.recycler_view

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.basicnotepad.core.models.Notes
import com.example.basicnotepad.databinding.NoteListItemBinding

class NotesListAdapter() : ViewBindingRecyclerAdapter<Notes, NoteListItemBinding>() {

    private var mList: MutableList<Notes> = arrayListOf()


    override fun bindItem(binding: NoteListItemBinding, item: Notes) {
        with(binding){
            txtNoteDescription.text = item.note
        }
    }

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup): NoteListItemBinding {
        return NoteListItemBinding.inflate(inflater, parent, false)
    }

    fun updateList (list: MutableList<Notes>) {
        mList = list
        notifyDataSetChanged()
    }

    fun addList (note: Notes) {
        mList.add(note)
    }
}
