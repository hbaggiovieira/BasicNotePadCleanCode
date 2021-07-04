package com.example.basicnotepad.home.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.basicnotepad.R
import com.example.basicnotepad.core.utils.hideKeyboard
import com.example.basicnotepad.home.recycler.NotesListAdapter
import com.example.basicnotepad.home.recycler.NotesListener
import com.example.basicnotepad.repository.NotesRepository
import com.example.basicnotepad.repository.model.NoteModel
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.layout_dafault_toolbar.view.*

class HomeFragment : Fragment() {
    private lateinit var mListener: NotesListener
    private lateinit var notesRepository: NotesRepository
    private lateinit var mAdapter: NotesListAdapter

    private val navigator get() = findNavController()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notesRepository = NotesRepository.getDatabase(requireContext())
        setupButtons()
        setupViews()
    }

    override fun onResume() {
        super.onResume()
        setupRecycler()
    }

    private fun setupViews() {
        requireActivity().toolbar.titleLabel.text = getString(R.string.title_label_home_fragment)
    }

    private fun updateViews() {
        setupRecycler()
    }

    private fun setupButtons() {
        fab.setOnClickListener {
            hideKeyboard()
            navigator.navigate(HomeFragmentDirections.actionHomeFragmentToAddNoteFragment(true, id))
        }
    }

    private fun setupRecycler() {
        //obter a recycler
        val recyclerView = recyclerNotes
        val repository = NotesRepository.getDatabase(requireContext()).notesDAO()
        mAdapter = NotesListAdapter(repository.getAll())
        //definir um adapter
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }

        mListener = object : NotesListener {
            override fun onClick(noteModel: NoteModel) {
                hideKeyboard()
                val bundle = bundleOf(
                    "isNew" to false,
                    "title" to noteModel.title
                )
                navigator.navigate(R.id.editNoteFragment, bundle)
            }

            override fun onDelete(noteModel: NoteModel) {
                notesRepository.notesDAO().delete(noteModel)
                updateViews()
            }
        }
        mAdapter.attachListener(mListener)
    }
}