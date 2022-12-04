package com.example.vinylteam8.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.vinylteam8.R
import com.example.vinylteam8.databinding.FragmentAlbumListBinding
import com.example.vinylteam8.databinding.FragmentAlbumTracksBinding
import com.example.vinylteam8.models.Album
import com.example.vinylteam8.models.Track
import com.example.vinylteam8.ui.album.AlbumFragmentDirections


class AlbumDetailsAdapter : RecyclerView.Adapter<AlbumDetailsAdapter.AlbumDetailViewHolder>(){

    var tracks :List<Track> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumDetailViewHolder {
        val withDataBinding: FragmentAlbumTracksBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            AlbumDetailViewHolder.LAYOUT,
            parent,
            false)
        return AlbumDetailViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: AlbumDetailViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.track = tracks[position]
        }

    }

    override fun getItemCount(): Int {
        return tracks.size
    }


    class AlbumDetailViewHolder(val viewDataBinding: FragmentAlbumTracksBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.fragment_album_tracks
        }

    }
}