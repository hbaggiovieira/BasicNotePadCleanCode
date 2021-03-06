package com.example.basicnotepad.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.basicnotepad.R
import com.example.basicnotepad.core.repository.SharedPreferencesRepository
import com.example.basicnotepad.home.HomeSharedViewModel
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.layout_dafault_toolbar.view.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HomeFragment : Fragment() {
    private val sharedViewModel by sharedViewModel<HomeSharedViewModel>()

    private val pref by lazy {
       SharedPreferencesRepository(requireContext())
    }

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

    private fun setupViews() {
        requireActivity().toolbar.titleLabel.text = getString(R.string.title_label_home_fragment)

        if (pref.getValueString("note").isNullOrEmpty()) {
            txtNote.text = "Adicione a sua primeira anotação!"
        } else {
            txtNote.text = pref.getValueString("note")
        }
    }

    private fun setupButtons() {
        fab.setOnClickListener {
            navigator.navigate(R.id.action_homeFragment_to_addNoteFragment)
        }
    }
}