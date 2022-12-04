package com.example.vinylteam8.ui.collector

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
import com.example.vinylteam8.databinding.FragmentCollectorDetailsBinding
import com.example.vinylteam8.databinding.FragmentPerformerDetailsBinding
import com.example.vinylteam8.models.CollectorDetails
import com.example.vinylteam8.models.Collectors
import com.example.vinylteam8.models.Performer
import com.example.vinylteam8.models.PerformerDetails
import com.example.vinylteam8.viewmodels.CollectorDetailsViewModel
import com.example.vinylteam8.viewmodels.PerformerDetailsViewModel

class CollectorDetailsFragment : Fragment() {

    private var _binding: FragmentCollectorDetailsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var viewModel: CollectorDetailsViewModel

    private lateinit var collector : Collectors

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCollectorDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        val args:  CollectorDetailsFragmentArgs by navArgs()
        Log.d("Args", args.collectorId.toString())
        viewModel = ViewModelProvider(this, CollectorDetailsViewModel.Factory(activity.application, args.collectorId)).get(
            CollectorDetailsViewModel::class.java)
        viewModel.collector.observe(viewLifecycleOwner, Observer<CollectorDetails> {
            it.apply {
                _binding!!.collector = this

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