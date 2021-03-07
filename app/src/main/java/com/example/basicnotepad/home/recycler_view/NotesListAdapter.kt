package com.example.basicnotepad.home.recycler_view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.basicnotepad.R
import com.example.basicnotepad.core.services.model.Notes
import kotlinx.android.synthetic.main.note_list_item.view.*

class NotesListAdapter(
    private val context: Context,
    private val notes: MutableList<Notes> = mutableListOf(),
    var noteClick: (note: Notes) -> Unit = {}
) : RecyclerView.Adapter<NotesListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.note_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notes[position]
        holder.setNote(note)
    }

    fun update(notes: List<Notes>) {
        notifyItemRangeRemoved(0, this.notes.size)
        this.notes.clear()
        this.notes.addAll(notes)
        notifyItemRangeInserted(0, this.notes.size)
    }


    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private lateinit var note: Notes

        init {
            itemView.setOnClickListener {
                if (::note.isInitialized) noteClick(note)
            }
        }

        fun setNote(note: Notes) {
            this.note = note
            itemView.txtNoteDescription.text = note.description
        }
    }
}


