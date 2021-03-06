package com.example.basicnotepad.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeSharedViewModel : ViewModel(){

    var note = MutableLiveData<String>()

}