package com.example.basicnotepad.home.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.basicnotepad.R
import com.example.basicnotepad.core.utils.hideKeyboard
import com.example.basicnotepad.home.ui.home.recycler.NotesListAdapter
import com.example.basicnotepad.home.ui.home.recycler.NotesListener
import com.example.basicnotepad.home.ui.notes.edit.EditNoteFragment.Companion.IS_NEW_TAG
import com.example.basicnotepad.home.ui.notes.edit.EditNoteFragment.Companion.NOTE_ID_TAG
import com.example.basicnotepad.repository.NotesRepository
import com.example.basicnotepad.repository.dao.NotesDAO
import com.example.basicnotepad.repository.model.NoteModel
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.layout_dafault_toolbar.view.*


class HomeFragment : Fragment() {
    private lateinit var mListener: NotesListener
    private lateinit var notesRepository: NotesDAO
    private lateinit var mAdapter: NotesListAdapter
    private var isDescending = true

    private val navigator get() = findNavController()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notesRepository = NotesRepository.getDatabase(requireContext()).notesDAO()
        setupButtons()
        setupViews()
    }

    override fun onResume() {
        super.onResume()
        updateViews()
    }

    private fun setupViews() {
        requireActivity().toolbar.titleLabel.text = getString(R.string.title_label_home_fragment)
    }

    private fun updateViews() {
        setupRecycler(notesRepository.getAll())
    }

    private fun setupButtons() {
        fab.setOnClickListener {
            navigator.navigate(HomeFragmentDirections.actionHomeFragmentToAddNoteFragment(true, id))
        }
        buttonFilter.setOnClickListener {
            if (isDescending) {
                it.background = ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_keyboard_arrow_up_24)
                setupRecycler(notesRepository.getAllAsc())
                isDescending = false
            } else {
                it.background = ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_keyboard_arrow_down_24)
                setupRecycler(notesRepository.getAll())
                isDescending = true
            }
        }
    }

    private fun setupRecycler(dataSet: List<NoteModel>) {
        val recyclerView = recyclerNotes
        mAdapter = NotesListAdapter(dataSet)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )
        }

        mListener = object : NotesListener {
            override fun onClick(noteModel: NoteModel) {
                hideKeyboard()
                val bundle = bundleOf(
                    IS_NEW_TAG to false,
                    NOTE_ID_TAG to noteModel.id
                )
                navigator.navigate(R.id.editNoteFragment, bundle)
            }

            override fun onDelete(noteModel: NoteModel) {
                notesRepository.delete(noteModel)
                updateViews()
            }
        }
        mAdapter.attachListener(mListener)
    }
}