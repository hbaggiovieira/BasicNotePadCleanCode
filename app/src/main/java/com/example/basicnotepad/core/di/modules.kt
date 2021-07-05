package com.example.basicnotepad.core.di

import com.example.basicnotepad.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val notesModule = module {
    viewModel { HomeViewModel() }
}