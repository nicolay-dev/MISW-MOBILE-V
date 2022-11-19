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
import com.example.vinylteam8.models.Album
import com.example.vinylteam8.ui.album.AlbumFragmentDirections


class AlbumsAdapter : RecyclerView.Adapter<AlbumsAdapter.AlbumViewHolder>(){

    var albums :List<Album> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val withDataBinding: FragmentAlbumListBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            AlbumViewHolder.LAYOUT,
            parent,
            false)
        return AlbumViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.album = albums[position]
        }

        holder.bind(albums[position])


        holder.viewDataBinding.root.setOnClickListener {
            val action = AlbumFragmentDirections.actionNavigationAlbumToAlbumDetailsFragment(albums[position].albumId)
            // Navigate using that action
            holder.viewDataBinding.root.findNavController().navigate(action)
        }


    }

    override fun getItemCount(): Int {
        return albums.size
    }


    class AlbumViewHolder(val viewDataBinding: FragmentAlbumListBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.fragment_album_list
        }
        fun bind(album: Album) {
            Glide.with(itemView)
                .load(album.cover.toUri().buildUpon().scheme("https").build())
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.loading_animation)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.drawable.ic_broken_image))
                .into(viewDataBinding.imagelist)
        }
    }


}