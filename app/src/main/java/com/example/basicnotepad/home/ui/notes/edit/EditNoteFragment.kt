package com.example.basicnotepad.home.ui.notes.edit

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.basicnotepad.R
import com.example.basicnotepad.core.utils.hideKeyboard
import com.example.basicnotepad.repository.NotesRepository
import com.example.basicnotepad.repository.dao.NotesDAO
import com.example.basicnotepad.repository.model.NoteModel
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_edit_note.*
import kotlinx.android.synthetic.main.layout_dafault_toolbar.view.*
import java.text.SimpleDateFormat
import java.util.*


class EditNoteFragment : Fragment(R.layout.fragment_edit_note) {

    companion object {
        const val IS_NEW_TAG = "isNew"
        const val NOTE_ID_TAG = "noteId"
    }

    private lateinit var repository: NotesDAO
    private var colorId: Int? = null
    private var isNew = true
    private var noteId: Int? = null
    private var title = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getArgs()
        setupViews()
        setupButtons()
    }

    private fun setupViews() {
        edtTxtNote.requestFocus()
        requireActivity().toolbar.titleLabel.text =
            getString(R.string.title_label_add_note_fragment)
        editNoteTitleLabel.setText(title)
        if (!isNew) {
            edtTxtNote.setText(repository.getById(noteId!!).content)
            edtTxtNestedScrollView.setBackgroundColor(repository.getById(noteId!!).colorId)
        }
    }

    private fun getArgs() {
        isNew = arguments?.getBoolean("isNew") ?: true
        noteId = arguments?.getInt(NOTE_ID_TAG) ?: id
        try {
            repository = NotesRepository.getDatabase(requireContext()).notesDAO()
            colorId = repository.getById(noteId!!).colorId
            title = repository.getById(noteId!!).title
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun saveNote(note: NoteModel) {
        repository.save(
            NoteModel(
                noteId!!,
                note.title,
                note.date,
                note.content,
                note.colorId
            )
        )
    }

    private fun setupButtons() {
        fabSaveNote.setOnClickListener {
            val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            saveNote(
                NoteModel(
                    noteId!!,
                    editNoteTitleLabel.text.toString(),
                    formatter.format(Date()),
                    edtTxtNote.text.toString(),
                    colorId ?: ContextCompat.getColor(requireContext(), R.color.light_gray)
                )
            )
            hideKeyboard()
            requireActivity().onBackPressed()
        }
        fabSetRed.setOnClickListener {
            colorId = ContextCompat.getColor(requireContext(), R.color.redLabel)
            edtTxtNestedScrollView.setBackgroundColor(
                colorId!!
            )
        }
        fabSetGreen.setOnClickListener {
            colorId = ContextCompat.getColor(requireContext(), R.color.greenLabel)
            edtTxtNestedScrollView.setBackgroundColor(
                colorId!!
            )
        }
        fabSetYellow.setOnClickListener {
            colorId = ContextCompat.getColor(requireContext(), R.color.yellowLabel)
            edtTxtNestedScrollView.setBackgroundColor(
                colorId!!
            )
        }
        fabSetBlue.setOnClickListener {
            colorId = ContextCompat.getColor(requireContext(), R.color.blueLabel)
            edtTxtNestedScrollView.setBackgroundColor(
                colorId!!
            )
        }
    }
}