package com.example.basicnotepad.home.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.basicnotepad.R
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.layout_dafault_toolbar.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {
    private val navigator get() = findNavController()
    private val viewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtons()
        setupToolbar()
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateViews(requireContext(), recyclerNotes, navigator)
    }

    private fun setupToolbar() {
        requireActivity().toolbar.apply {
            rightButtonClick = {
                Toast.makeText(requireContext(), "Aasddsadsa", Toast.LENGTH_SHORT).show()
            }
            titleLabel.text = getString(R.string.title_label_home_fragment)
        }
    }

    private fun setupButtons() {
        fab.setOnClickListener {
            navigator.navigate(HomeFragmentDirections.actionHomeFragmentToAddNoteFragment(true))
        }
        buttonFilter.setOnClickListener {
            viewModel.toggleFilterByDate(requireContext(), it, recyclerNotes, navigator)
        }
    }
}