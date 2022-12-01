package com.example.vinylteam8.ui.album

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.AppCompatSpinner
import androidx.core.view.isEmpty
import androidx.fragment.app.Fragment
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.vinylteam8.databinding.FragmentAlbumCreateBinding
import com.example.vinylteam8.ui.performer.PerformerDetailsFragmentArgs
import com.example.vinylteam8.viewmodels.AlbumCreateViewModel
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_album_create.*
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import org.json.JSONException
import org.json.JSONObject

class AlbumCreateFragment : Fragment() {

    lateinit var nameAlbum: TextInputEditText
    lateinit var imageURLAlbum: TextInputEditText
    lateinit var descriptionAlbum: TextInputEditText
    lateinit var releaseDateAlbum: TextInputEditText
    lateinit var genreAlbum: AppCompatSpinner
    lateinit var rLabelAlbum: AppCompatSpinner
    lateinit var createAlbum: Button
    val BASE_URL = "https://backend-vynils-tsdl.herokuapp.com/albums"

    private var _binding: FragmentAlbumCreateBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var viewModel: AlbumCreateViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlbumCreateBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }

        nameAlbum = _binding!!.txtPostName
        imageURLAlbum = _binding!!.txtPostImage
        descriptionAlbum = _binding!!.txtPostDescription
        releaseDateAlbum = _binding!!.txtPostReleaseDate
        genreAlbum = _binding!!.txtPostGenre
        rLabelAlbum = _binding!!.txtPostRecordLabel
        createAlbum = _binding!!.postButton

        createAlbum.setOnClickListener {
            if (TextUtils.isEmpty(nameAlbum.text) || TextUtils.isEmpty(imageURLAlbum.text) || TextUtils.isEmpty(descriptionAlbum.text) || TextUtils.isEmpty(releaseDateAlbum.text) || TextUtils.isEmpty(genreAlbum.selectedItem.toString()) || TextUtils.isEmpty(rLabelAlbum.selectedItem.toString())) {
                // Agregar mensaje de error
                Toast.makeText(this.context, "Favor completar todos los campos", Toast.LENGTH_SHORT).show()
            }
            addAlbum(nameAlbum.text.toString(), imageURLAlbum.text.toString(), descriptionAlbum.text.toString(), releaseDateAlbum.text.toString(), genreAlbum.selectedItem.toString(), rLabelAlbum.selectedItem.toString())
        }

    }

    private fun addAlbum (albumName: String, albumImageURL: String, albumDescription: String, albumReleaseDate: String, albumGenre: String, albumRLabel: String) {
        // creating a new variable for our request queue
        val queue = Volley.newRequestQueue(this.context)

        // making a string request to update our data and
        // passing method as PUT. to update our data.
        val request: StringRequest =
            object : StringRequest(Request.Method.PUT, BASE_URL,
                Response.Listener { response ->
                    // inside on response method we are
                    // setting our edit text to empty.
                    nameAlbum.setText("")
                    imageURLAlbum.setText("")
                    descriptionAlbum.setText("")
                    releaseDateAlbum.setText("")
                    genreAlbum.isEmpty()
                    rLabelAlbum.isEmpty()

                    // on below line we are displaying a toast message as data updated.
                    Toast.makeText(this.context, "Álbum agregado..", Toast.LENGTH_SHORT).show()

                    try {
                        // on below line we are extracting data from our json object
                        // and passing our response to our json object.
                        val jsonObject = JSONObject(response)

                        println(jsonObject.getString("description"))

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }, Response.ErrorListener { error -> // displaying toast message on response failure.
                Log.e("tag", "error is " + error!!.message)
            }) {
                override fun getParams(): MutableMap<String, String>? {

                    // below line we are creating a map for storing
                    // our values in key and value pair.
                    val params: MutableMap<String, String> = HashMap()

                    // on below line we are passing our key
                    // and value pair to our parameters.
                    params["name"] = albumName
                    params["cover"] = albumImageURL
                    params["releaseDate"] = albumReleaseDate
                    params["description"] = albumDescription
                    params["genre"] = albumGenre
                    params["recordLabel"] = albumRLabel

                    // at last we are
                    // returning our params.
                    return params
                }
            }
        // below line is to make
        // a json object request.
        queue.add(request)
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