package com.example.basicnotepad.home.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.basicnotepad.R
import com.example.basicnotepad.core.services.repository.SharedPreferencesRepository
import com.example.basicnotepad.home.HomeSharedViewModel
import com.example.basicnotepad.home.recycler_view.NotesListAdapter
import com.example.basicnotepad.home.recycler_view.NotesListener
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.layout_dafault_toolbar.view.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HomeFragment : Fragment() {
    private val sharedViewModel by sharedViewModel<HomeSharedViewModel>()
    private val mAdapter: NotesListAdapter = NotesListAdapter()
    private lateinit var mListener: NotesListener

//    private val editDestination =
    private val navigator get() = findNavController()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

    private fun setupButtons() {
        fab.setOnClickListener {
            navigator.navigate(R.id.action_homeFragment_to_addNoteFragment)
        }
    }

    private fun setupRecycler() {
        //obter a recycler
        val recyclerView = recyclerNotes

        //definir um adapter
        recyclerView.adapter = mAdapter

        observer()
        sharedViewModel.load()

        mListener = object : NotesListener {
            override fun onClick(id: Int) {
                navigator.navigate(R.id.action_homeFragment_to_addNoteFragment)
            }
        }
        mAdapter.attachListener(mListener)
    }

    private fun observer() {
        sharedViewModel.noteList.observe(viewLifecycleOwner, Observer {
            mAdapter.update(it)
        })
    }
}