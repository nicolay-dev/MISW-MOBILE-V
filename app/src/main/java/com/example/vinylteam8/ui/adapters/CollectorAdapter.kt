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
import com.example.vinylteam8.databinding.FragmentCollectorListBinding
import com.example.vinylteam8.models.Album
import com.example.vinylteam8.models.Collectors
import com.example.vinylteam8.ui.album.AlbumFragmentDirections
import com.example.vinylteam8.ui.collector.CollectorFragmentDirections


class CollectorAdapter : RecyclerView.Adapter<CollectorAdapter.CollectorViewHolder>(){

    var collectors :List<Collectors> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectorViewHolder {
        val withDataBinding: FragmentCollectorListBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            CollectorViewHolder.LAYOUT,
            parent,
            false)
        return CollectorViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: CollectorViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.collector = collectors[position]
        }


        holder.viewDataBinding.root.setOnClickListener {
            val action = CollectorFragmentDirections.actionNavigationCollectorToCollectorDetailsFragment(collectors[position].collectorID)
            // Navigate using that action
            holder.viewDataBinding.root.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return collectors.size
    }


    class CollectorViewHolder(val viewDataBinding: FragmentCollectorListBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.fragment_collector_list
        }
    }



}