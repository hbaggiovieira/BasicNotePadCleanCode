package com.example.basicnotepad.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.basicnotepad.R
import com.example.basicnotepad.home.HomeSharedViewModel
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_add_note.*
import kotlinx.android.synthetic.main.layout_dafault_toolbar.view.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class AddNoteFragment : Fragment() {
    private val sharedViewModel by sharedViewModel<HomeSharedViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupButtons()
        observe()
    }

    private fun setupButtons() {
        fabSaveNote.setOnClickListener{
            val description = edtTxtNote.text.toString()
            sharedViewModel.save(description)
            Toast.makeText(requireContext(), "Anotação salva com sucesso!", Toast.LENGTH_SHORT)
                .show()
            requireActivity().onBackPressed()
        }
    }

    private fun setupViews() {
        requireActivity().toolbar.titleLabel.text = getString(R.string.title_label_add_note_fragment)
    }

    private fun observe() {
        sharedViewModel.saveNote.observe(requireActivity(), Observer {
            if (it) {
                Toast.makeText(requireContext(), "Sucesso :D !", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Falha! D: !", Toast.LENGTH_SHORT).show()
            }
        })
    }
}