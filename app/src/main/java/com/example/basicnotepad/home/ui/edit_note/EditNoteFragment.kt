package com.example.basicnotepad.home.ui.edit_note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.basicnotepad.R
import com.example.basicnotepad.core.utils.hideKeyboard
import com.example.basicnotepad.home.HomeSharedViewModel
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_edit_note.*
import kotlinx.android.synthetic.main.layout_dafault_toolbar.view.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class EditNoteFragment : Fragment() {
    private val sharedViewModel by sharedViewModel<HomeSharedViewModel>()

    private val args by navArgs<EditNoteFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupButtons()
        observe()
        loadData()
    }

    private fun setupViews() {
        if (args.noteBackgroundColor != 0) {
            edtTxtNote.setBackgroundColor(args.noteBackgroundColor)
        }

        requireActivity().toolbar.titleLabel.text =
            getString(R.string.title_label_add_note_fragment)
    }

    private fun setupButtons() {
        fabSaveNote.setOnClickListener {
            hideKeyboard()
            val description = edtTxtNote.text.toString()
            val color = ContextCompat.getColor(requireContext(), R.color.redLabel)
            sharedViewModel.save(args.isNew, args.noteId, description, color)
            requireActivity().onBackPressed()
        }
        fabSetRed.setOnClickListener {
            val color = resources.getColor(R.color.redLabel)
            edtTxtNote.setBackgroundColor(color)
        }
        fabSetGreen.setOnClickListener {
            val color = resources.getColor(R.color.greenLabel)
            edtTxtNote.setBackgroundColor(color)
        }
        fabSetYellow.setOnClickListener {
            val color = resources.getColor(R.color.yellowLabel)
            edtTxtNote.setBackgroundColor(color)
        }
        fabSetBlue.setOnClickListener {
            val color = resources.getColor(R.color.blueLabel)
            edtTxtNote.setBackgroundColor(color)
        }
    }

    private fun loadData() {
        if (args.isNew.not()) {
            val id = args.noteId
            sharedViewModel.loadNote(id)
        }
    }

    private fun observe() {
        sharedViewModel.saveNote.observe(viewLifecycleOwner, Observer {
            if (it) {
                Toast.makeText(requireContext(), "Sucesso :D !", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Falha! D: !", Toast.LENGTH_SHORT).show()
            }
        })
        sharedViewModel.note.observe(viewLifecycleOwner, Observer {
            if (it.description.isNullOrEmpty()) {
                edtTxtNote.setText("")
            } else {
                edtTxtNote.setText(it.description)
            }
        })
    }
}