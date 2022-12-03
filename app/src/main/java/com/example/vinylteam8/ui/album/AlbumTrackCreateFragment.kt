package com.example.vinylteam8.ui.album

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.vinylteam8.viewmodels.AlbumTrackCreateViewModel
import com.example.vinylteam8.R

class AlbumTrackCreateFragment : Fragment() {

    companion object {
        fun newInstance() = AlbumTrackCreateFragment()
    }

    private lateinit var viewModel: AlbumTrackCreateViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_album_track_create, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AlbumTrackCreateViewModel::class.java)
        // TODO: Use the ViewModel
    }

}