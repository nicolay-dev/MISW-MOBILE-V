package com.example.vinylteam8.ui.album

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.vinylteam8.viewmodels.AlbumCreateViewModel
import android.text.TextUtils
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.AppCompatSpinner
import androidx.navigation.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.example.vinylteam8.databinding.FragmentAlbumCreate2Binding
import org.json.JSONObject

@Suppress("DEPRECATION")
class AlbumCreateFragment : Fragment() {

    lateinit var nameAlbum: TextInputEditText
    lateinit var imageURLAlbum: TextInputEditText
    lateinit var descriptionAlbum: TextInputEditText
    lateinit var releaseDateAlbum: TextInputEditText
    lateinit var genreAlbum: AppCompatSpinner
    lateinit var rLabelAlbum: AppCompatSpinner
    lateinit var createAlbum: Button
    lateinit var cancelAlbum: Button

    companion object;

    private var _binding: FragmentAlbumCreate2Binding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var viewModel: AlbumCreateViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlbumCreate2Binding.inflate(inflater, container, false)
        return binding.root
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }

        viewModel = ViewModelProvider(this).get(AlbumCreateViewModel::class.java)
        // TODO: Use the ViewModel

        nameAlbum = _binding!!.txtPostName
        imageURLAlbum = _binding!!.txtPostImage
        descriptionAlbum = _binding!!.txtPostDescription
        releaseDateAlbum = _binding!!.txtPostReleaseDate
        genreAlbum = _binding!!.txtPostGenre
        rLabelAlbum = _binding!!.txtPostRecordLabel
        createAlbum = _binding!!.postButton
        cancelAlbum = _binding!!.cancelButton

        createAlbum.setOnClickListener {
            if (TextUtils.isEmpty(nameAlbum.text) || TextUtils.isEmpty(imageURLAlbum.text) || TextUtils.isEmpty(descriptionAlbum.text) || TextUtils.isEmpty(releaseDateAlbum.text) || TextUtils.isEmpty(genreAlbum.selectedItem.toString()) || TextUtils.isEmpty(rLabelAlbum.selectedItem.toString())) {
                Toast.makeText(this.context, "Favor llenar todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                addAlbum(
                    nameAlbum.text.toString(),
                    imageURLAlbum.text.toString(),
                    descriptionAlbum.text.toString(),
                    releaseDateAlbum.text.toString(),
                    genreAlbum.selectedItem.toString(),
                    rLabelAlbum.selectedItem.toString()
                )
            }
        }

        cancelAlbum.setOnClickListener {
            nameAlbum.setText("")
            imageURLAlbum.setText("")
            descriptionAlbum.setText("")
            releaseDateAlbum.setText("")

            val action = AlbumCreateFragmentDirections.actionFragmentAlbumCreate2ToNavigationAlbum()
            // Navigate using that action
            view?.findNavController()?.navigate(action)

        }
    }

    private fun addAlbum (albumName: String, albumImageURL: String, albumDescription: String, albumReleaseDate: String, albumGenre: String, albumRLabel: String) {

        val strAlbum =
            "{\n    \"name\": \"$albumName\",\n    \"cover\": \"$albumImageURL\",\n    \"releaseDate\": \"$albumReleaseDate\",\n    \"description\": \"$albumDescription\",\n    \"genre\": \"$albumGenre\",\n    \"recordLabel\": \"$albumRLabel\"\n}"

        viewModel.createAlbumFromNetwork(JSONObject(strAlbum))

        nameAlbum.setText("")
        imageURLAlbum.setText("")
        descriptionAlbum.setText("")
        releaseDateAlbum.setText("")

        // on below line we are displaying a toast message as data updated.
        Toast.makeText(this.context, "Álbum agregado..", Toast.LENGTH_SHORT).show()

        val action = AlbumCreateFragmentDirections.actionFragmentAlbumCreate2ToNavigationAlbum()
        // Navigate using that action
        view?.findNavController()?.navigate(action)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}