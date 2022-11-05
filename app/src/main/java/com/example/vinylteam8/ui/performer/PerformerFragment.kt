package com.example.vinylteam8.ui.performer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vinylteam8.R
import com.example.vinylteam8.databinding.FragmentPerfomerBinding
import com.example.vinylteam8.models.Performer
import com.example.vinylteam8.ui.adapters.PerformerAdapter
import com.example.vinylteam8.viewmodels.PerformerViewModel

class PerformerFragment : Fragment() {

    private var _binding: FragmentPerfomerBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: PerformerViewModel
    private var viewModelAdapter: PerformerAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPerfomerBinding.inflate(inflater, container, false)
        val root: View = binding.root
        viewModelAdapter = PerformerAdapter()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.perfomerRv
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = viewModelAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        activity.actionBar?.title = getString(R.string.title_performer)
        viewModel = ViewModelProvider(this, PerformerViewModel.Factory(activity.application)).get(PerformerViewModel::class.java)
        viewModel.performers.observe(viewLifecycleOwner, Observer<List<Performer>> {
            it.apply {
                viewModelAdapter!!.performers = this
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