package com.example.basicnotepad.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.basicnotepad.R
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.layout_dafault_toolbar.view.*

class AddNoteFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle()
        setupButtons()
    }

    private fun setupButtons() {}

    private fun setTitle() {
        requireActivity().toolbar.titleLabel.text = getString(R.string.title_label_add_note_fragment)
    }
}