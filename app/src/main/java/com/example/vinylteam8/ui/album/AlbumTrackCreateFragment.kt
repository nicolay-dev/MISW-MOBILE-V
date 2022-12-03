package com.example.vinylteam8.ui.album

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.vinylteam8.viewmodels.AlbumTrackCreateViewModel
import com.example.vinylteam8.databinding.FragmentAlbumTrackCreateBinding
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONObject
import androidx.lifecycle.Observer
import com.example.vinylteam8.viewmodels.AlbumDetailsViewModel

class AlbumTrackCreateFragment : Fragment() {

    lateinit var nameTrack: TextInputEditText
    lateinit var duration: TextInputEditText
    lateinit var createTrack: Button
    lateinit var cancelTrack: Button

    companion object;
    private var _binding: FragmentAlbumTrackCreateBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
   private lateinit var viewModel: AlbumTrackCreateViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAlbumTrackCreateBinding.inflate(inflater, container, false)
        return binding.root

    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        val args: AlbumDetailsFragmentArgs by navArgs()
        Log.d("Args", args.albumId.toString())

        viewModel = ViewModelProvider(this, AlbumTrackCreateViewModel.Factory(activity.application, args.albumId)).get(
            AlbumTrackCreateViewModel::class.java)

        nameTrack = _binding!!.txtTrackName
        duration = _binding!!.txtTrackDuration
        createTrack = _binding!!.postTrackButton
        cancelTrack = _binding!!.cancelTrackButton

        createTrack.setOnClickListener {
            if (TextUtils.isEmpty(nameTrack.text) || TextUtils.isEmpty(duration.text)) {
                Toast.makeText(this.context, "Favor llenar todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                addTrack(
                    nameTrack.text.toString(),
                    duration.text.toString()
                )
            }
        }

        cancelTrack.setOnClickListener {
            nameTrack.setText("")
            duration.setText("")

            val args: AlbumDetailsFragmentArgs by navArgs()
            Log.d("Args", args.albumId.toString())

            val action = AlbumTrackCreateFragmentDirections.actionAlbumTrackCreateFragmentToAlbumDetailsFragment(args.albumId)
            // Navigate using that action
            view?.findNavController()?.navigate(action)

        }


        viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })

    }

    private fun addTrack(trackName: String, trackDuration: String) {

        val strTrack =
            "{\n    \"name\": \"$trackName\",\n    \"duration\": \"$trackDuration\"\n}"

        val args: AlbumDetailsFragmentArgs by navArgs()
        Log.d("Args", args.albumId.toString())

        viewModel.createTrackFromNetwork(JSONObject(strTrack))

        nameTrack.setText("")
        duration.setText("")

        // on below line we are displaying a toast message as data updated.
        Toast.makeText(this.context, "Track agregado..", Toast.LENGTH_SHORT).show()


        val action = AlbumTrackCreateFragmentDirections.actionAlbumTrackCreateFragmentToAlbumDetailsFragment(args.albumId)
        // Navigate using that action
        view?.findNavController()?.navigate(action)

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