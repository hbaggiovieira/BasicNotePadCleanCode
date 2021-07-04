package com.example.basicnotepad.home.ui.notes.edit

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.basicnotepad.R
import com.example.basicnotepad.core.utils.hideKeyboard
import com.example.basicnotepad.repository.NotesRepository
import com.example.basicnotepad.repository.model.NoteModel
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_edit_note.*
import kotlinx.android.synthetic.main.layout_dafault_toolbar.view.*
import java.text.SimpleDateFormat
import java.util.*


class EditNoteFragment : Fragment(R.layout.fragment_edit_note) {

    private var colorId: Int? = null
    private var isNew = true
    private var title = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getArgs()
        setupViews()
        setupButtons()
    }

    private fun setupViews() {
        val repository = NotesRepository.getDatabase(requireContext()).notesDAO()
        requireActivity().toolbar.titleLabel.text =
            getString(R.string.title_label_add_note_fragment)
        editNoteTitleLabel.setText(title)
        edtTxtNote.setText(repository.getByTitle(title).title)
        edtTxtLayout.setBackgroundColor(repository.getByTitle(title).colorId)
    }

    private fun getArgs () {
        isNew = arguments?.getBoolean("isNew") ?: true
        title = arguments?.getString("title") ?: ""
    }

    private fun saveNote(note: NoteModel) {
        val notesRepository = NotesRepository.getDatabase(requireContext()).notesDAO()

        notesRepository.save(
            NoteModel(
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
                    editNoteTitleLabel.text.toString(),
                    formatter.format(Date()),
                    edtTxtNote.text.toString(),
                    colorId ?: getDefaultColor()
                )
            )
            hideKeyboard()
            requireActivity().onBackPressed()
        }
        fabSetRed.setOnClickListener {
            colorId = R.color.redLabel
            edtTxtNote.setBackgroundColor(ContextCompat.getColor(requireContext(), colorId as Int))
        }
        fabSetGreen.setOnClickListener {
            colorId = R.color.greenLabel
            edtTxtNote.setBackgroundColor(ContextCompat.getColor(requireContext(), colorId as Int))
        }
        fabSetYellow.setOnClickListener {
            colorId = R.color.yellowLabel
            edtTxtNote.setBackgroundColor(ContextCompat.getColor(requireContext(), colorId as Int))
        }
        fabSetBlue.setOnClickListener {
            colorId = R.color.blueLabel
            edtTxtNote.setBackgroundColor(ContextCompat.getColor(requireContext(), colorId as Int))
        }
    }

    private fun getDefaultColor(): Int {
        var color: Int = Color.TRANSPARENT
        val background = edtTxtNote.background
        if (background is ColorDrawable) color = background.color
        return color
    }
}