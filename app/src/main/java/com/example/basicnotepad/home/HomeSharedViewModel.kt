package com.example.basicnotepad.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.basicnotepad.core.models.Notes

class HomeSharedViewModel : ViewModel(){

    var note = MutableLiveData<Notes>()

}