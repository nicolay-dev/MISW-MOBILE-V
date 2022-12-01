package com.example.vinylteam8.ui.album

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.vinylteam8.R
import com.example.vinylteam8.viewmodels.AlbumCreateViewModel

class AlbumCreateFragment : Fragment() {

    companion object {
        fun newInstance() = AlbumCreateFragment()
    }

    private lateinit var viewModel: AlbumCreateViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_album_create_2, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AlbumCreateViewModel::class.java)
        // TODO: Use the ViewModel
    }

}