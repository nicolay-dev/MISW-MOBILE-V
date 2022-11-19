package com.example.vinylteam8.ui.performer

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.vinylteam8.R
import com.example.vinylteam8.databinding.FragmentPerformerDetailsBinding
import com.example.vinylteam8.models.Performer
import com.example.vinylteam8.models.PerformerDetails
import com.example.vinylteam8.viewmodels.PerformerDetailsViewModel

class PerformerDetailsFragment : Fragment() {

    private var _binding: FragmentPerformerDetailsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var viewModel: PerformerDetailsViewModel

    private lateinit var performer : Performer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPerformerDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        val args: PerformerDetailsFragmentArgs by navArgs()
        Log.d("Args", args.performerId.toString())
        viewModel = ViewModelProvider(this, PerformerDetailsViewModel.Factory(activity.application, args.performerId)).get(
            PerformerDetailsViewModel::class.java)
        viewModel.performer.observe(viewLifecycleOwner, Observer<PerformerDetails> {
            it.apply {
                _binding!!.performer = this
                Glide.with(requireContext())
                    .load(this.image.toUri().buildUpon().scheme("https").build())
                    .apply(
                        RequestOptions()
                            .placeholder(R.drawable.loading_animation)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .error(R.drawable.ic_broken_image))
                    .into(_binding!!.imagePerformerDetails)
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