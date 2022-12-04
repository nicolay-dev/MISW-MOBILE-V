package com.example.vinylteam8.ui.album

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.net.toUri
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.vinylteam8.R
import com.example.vinylteam8.databinding.FragmentAlbumBinding
import com.example.vinylteam8.databinding.FragmentAlbumDetailsBinding
import com.example.vinylteam8.models.Album
import com.example.vinylteam8.models.AlbumDetails
import com.example.vinylteam8.ui.adapters.AlbumDetailsAdapter
import com.example.vinylteam8.ui.adapters.AlbumsAdapter
import com.example.vinylteam8.viewmodels.AlbumDetailsViewModel
import com.example.vinylteam8.viewmodels.AlbumViewModel

class AlbumDetailsFragment : Fragment() {

    private var _binding: FragmentAlbumDetailsBinding? = null
    private lateinit var recyclerView: RecyclerView
    private var viewModelAdapter: AlbumDetailsAdapter? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var viewModel: AlbumDetailsViewModel


//    companion object {
//        fun newInstance() = AlbumDetailsFragment()
//   }

    private lateinit var album :Album

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlbumDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        viewModelAdapter = AlbumDetailsAdapter()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        recyclerView = binding.tracksRv
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = viewModelAdapter

        val createTrackButton: Button = view.findViewById(R.id.create_track_button)

        val args: AlbumDetailsFragmentArgs by navArgs()
        Log.d("Args", args.albumId.toString())

        createTrackButton.setOnClickListener {
            val action = AlbumDetailsFragmentDirections.actionAlbumDetailsFragmentToAlbumTrackCreateFragment(args.albumId)
            // Navigate using that action
            view.findNavController().navigate(action)
        }

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        val args: AlbumDetailsFragmentArgs by navArgs()
        Log.d("Args", args.albumId.toString())

        viewModel = ViewModelProvider(this, AlbumDetailsViewModel.Factory(activity.application, args.albumId)).get(AlbumDetailsViewModel::class.java)
        viewModel.album.observe(viewLifecycleOwner, Observer<AlbumDetails> {
            it.apply {
                _binding!!.album = this
                Glide.with(requireContext())
                    .load(this.cover.toUri().buildUpon().scheme("https").build())
                    .apply(
                        RequestOptions()
                            .placeholder(R.drawable.loading_animation)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .fitCenter()
                            .override(500, 500)
                            .error(R.drawable.ic_broken_image))
                    .into(_binding!!.imageAlbumDetails)

                viewModelAdapter!!.tracks = this.tracks
            }


        })
        viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onNetworkError() {
        if(!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }

}