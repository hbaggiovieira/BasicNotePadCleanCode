package com.example.basicnotepad.home.di

import com.example.basicnotepad.home.HomeSharedViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val notesModule = module {

    viewModel { HomeSharedViewModel() }

}