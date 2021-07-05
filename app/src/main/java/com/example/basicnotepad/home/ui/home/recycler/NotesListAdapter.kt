package com.example.basicnotepad.home.ui.home.recycler

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.basicnotepad.R
import com.example.basicnotepad.repository.model.NoteModel
import kotlinx.android.synthetic.main.note_list_item.view.*

class NotesListAdapter(private val dataSet: List<NoteModel>) :
    RecyclerView.Adapter<NotesListAdapter.ViewHolder>() {

    private lateinit var mListener: NotesListener
    private var mList: List<NoteModel> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.note_list_item, parent, false)
        return ViewHolder(view, mListener)
    }

    override fun getItemCount(): Int = dataSet.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val note = dataSet[position]
        holder.bind(note)
    }


    fun attachListener(listener: NotesListener) {
        mListener = listener
    }

    fun updateList(list: List<NoteModel>) {
        mList = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View, private val listener: NotesListener) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(note: NoteModel) {
            with(itemView) {
                this.editNoteTitleLabel.text = note.title
                this.homeDateLabel.text = itemView.context.getString(R.string.last_update_date, note.date)
                this.noteListItemLayout.setBackgroundColor(note.colorId)
                this.txtNoteContent.apply {
                    val substring = if (note.content.length >= 90) {
                        note.content.substring(0, 90)
                    } else {
                        note.content
                    }
                    "${substring}...".also { text = it }
                }
                this.noteListItemLayout.apply {
                    setOnClickListener {
                        listener.onClick(note)
                    }
                    setOnLongClickListener {
                        AlertDialog.Builder(itemView.context)
                            .setTitle(R.string.alert_dialog_delete_title)
                            .setMessage(R.string.alert_dialog_delete_message)
                            .setPositiveButton(R.string.alert_dialog_button_remove) { _, _ ->
                                listener.onDelete(note)
                            }
                            .setNeutralButton(R.string.alert_dialog_button_cancel) { _, _ ->
                            }
                            .show()
                        true
                    }
                }
            }
        }
    }
}


