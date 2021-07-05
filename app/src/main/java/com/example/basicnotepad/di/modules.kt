package com.example.basicnotepad.di

import com.example.basicnotepad.home.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val notesModule = module {
    viewModel { HomeViewModel() }
}