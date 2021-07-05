package com.example.basicnotepad.ui.home

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.basicnotepad.R
import com.example.basicnotepad.ui.home.recycler.NotesListAdapter
import com.example.basicnotepad.ui.home.recycler.NotesListener
import com.example.basicnotepad.ui.edit.EditNoteFragment
import com.example.basicnotepad.repository.NotesRepository
import com.example.basicnotepad.repository.model.NoteModel

class HomeViewModel() : ViewModel() {

    private var _isDescending = MutableLiveData<Boolean>()
    val isDescending: LiveData<Boolean> = _isDescending

    fun toggleFilterByDate(
        context: Context,
        it: View,
        recyclerView: RecyclerView,
        navController: NavController
    ) {
        val notesRepository = NotesRepository.getDatabase(context).notesDAO()

        if (isDescending.value == true) {
            it.background = ContextCompat.getDrawable(
                context,
                R.drawable.ic_baseline_keyboard_arrow_up_24
            )
            setupRecycler(context, notesRepository.getAllAsc(), recyclerView, navController)
            _isDescending.postValue(false)
        } else {
            it.background = ContextCompat.getDrawable(
                context,
                R.drawable.ic_baseline_keyboard_arrow_down_24
            )
            setupRecycler(context, notesRepository.getAll(), recyclerView, navController)
            _isDescending.postValue(true)
        }
    }

    private fun setupRecycler(
        context: Context,
        dataSet: List<NoteModel>,
        recyclerView: RecyclerView,
        navController: NavController
    ) {
        val mAdapter = NotesListAdapter(dataSet)
        val notesRepository = NotesRepository.getDatabase(context).notesDAO()

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }
        val mListener = object : NotesListener {
            override fun onClick(noteModel: NoteModel) {
                val bundle = bundleOf(
                    EditNoteFragment.IS_NEW_TAG to false,
                    EditNoteFragment.NOTE_ID_TAG to noteModel.noteId
                )
                navController.navigate(R.id.editNoteFragment, bundle)
            }

            override fun onDelete(noteModel: NoteModel) {
                notesRepository.delete(noteModel)
                updateViews(context, recyclerView, navController)
            }
        }
        mAdapter.attachListener(mListener)
    }

    fun updateViews(context: Context, recyclerView: RecyclerView, navController: NavController) {
        val notesRepository = NotesRepository.getDatabase(context).notesDAO()
        setupRecycler(context, notesRepository.getAll(), recyclerView, navController)
    }
}