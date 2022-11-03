package com.example.vinylteam8.ui.collection

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CollectionViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Collection Fragment"
    }
    val text: LiveData<String> = _text
}