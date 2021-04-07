package com.example.basicnotepad.home.recycler_view

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.basicnotepad.R
import com.example.basicnotepad.core.services.model.Notes
import kotlinx.android.synthetic.main.note_list_item.view.*

class NotesListAdapter : RecyclerView.Adapter<NotesListAdapter.ViewHolder>() {

    private var mNotes: List<Notes> = arrayListOf()
    private lateinit var mListener: NotesListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.note_list_item, parent, false)
        return ViewHolder(view, mListener)
    }

    override fun getItemCount(): Int = mNotes.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val note = mNotes[position]
        holder.bind(note)
    }

    fun update(notes: List<Notes>) {
        mNotes = notes
        notifyDataSetChanged()
    }

    fun attachListener(listener: NotesListener) {
        mListener = listener
    }


    inner class ViewHolder(itemView: View, private val listener: NotesListener) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(note: Notes) {
            itemView.txtNoteDescription.text = note.description
            //ToDo Melhorar visualizacao
            itemView.setBackgroundColor(note.colorId)

            itemView.txtNoteDescription.setOnClickListener {
                listener.onClick(note.id)
            }
            itemView.txtNoteDescription.setOnLongClickListener {
                itemView.setBackgroundResource(R.color.brand_2)
                AlertDialog.Builder(itemView.context)
                    .setTitle(R.string.alert_dialog_delete_title)
                    .setMessage(R.string.alert_dialog_delete_message)
                    .setPositiveButton(R.string.alert_dialog_button_remove) { dialog, which ->
                        listener.onDelete(note.id)
                    }
                    .setNeutralButton(R.string.alert_dialog_button_cancel) { _, _ ->
                        itemView.setBackgroundResource(R.color.shadow)
                    }
                    .show()
                true
            }
        }
    }
}


