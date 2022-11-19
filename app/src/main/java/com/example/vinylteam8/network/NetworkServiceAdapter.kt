package com.example.vinylteam8.network

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.vinylteam8.models.Album
import com.example.vinylteam8.models.AlbumDetails
import com.example.vinylteam8.models.Performer
import com.example.vinylteam8.models.PerformerDetails
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class NetworkServiceAdapter constructor(context: Context){
    companion object{
        const val BASE_URL= "https://backend-vynils-tsdl.herokuapp.com/"
        var instance: NetworkServiceAdapter? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: NetworkServiceAdapter(context).also {
                    instance = it
                }
            }
    }

    private val requestQueue: RequestQueue by lazy {
        // applicationContext keeps you from leaking the Activity or BroadcastReceiver if someone passes one in.
        Volley.newRequestQueue(context.applicationContext)
    }
    suspend fun getAlbums() = suspendCoroutine<List<Album>>{ cont->
        val list = mutableListOf<Album>()
        requestQueue.add(getRequest("albums",
            { response ->
                val resp = JSONArray(response)
                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)
                    val album = Album(albumId = item.getInt("id"),name = item.getString("name"), cover = item.getString("cover"), recordLabel = item.getString("recordLabel"), releaseDate = item.getString("releaseDate"), genre = item.getString("genre"), description = item.getString("description"))
                    list.add(i, album)
                }
                cont.resume(list)
            },
            {
                throw it
            }))
    }
    suspend fun getAlbum(albumId:Int) = suspendCoroutine<AlbumDetails>{ cont->
         requestQueue.add(getRequest("albums/$albumId",
            { response ->
                val item = JSONObject(response)
                val arrayPerformer = JSONArray(item.getString("performers"))
                val listperformer = mutableListOf<Performer>()
                for (i in 0 until arrayPerformer.length())
                {
                    val item = arrayPerformer.getJSONObject(i)
                    val perform =  Performer( performerID = item.getInt("id"), name = item.getString("name"), image = item.getString("image"), description = item.getString("description"))
                    listperformer.add(i, perform)
                }

                val album = AlbumDetails(albumId = item.getInt("id"),name = item.getString("name"), cover = item.getString("cover"), recordLabel = item.getString("recordLabel"), releaseDate = item.getString("releaseDate"), genre = item.getString("genre"), description = item.getString("description"), performers = listperformer )
                cont.resume(album)
            },
            {
                throw it
            }))
    }

    suspend fun getPerformers() = suspendCoroutine<List<Performer>>{ cont->
        val list = mutableListOf<Performer>()
        requestQueue.add(getRequest("bands",
            { response ->
                val resp = JSONArray(response)
                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)
                    list.add(i, Performer( performerID = item.getInt("id"), name = item.getString("name"), image = item.getString("image"), description = item.getString("description")))
                }
            },
            {
                cont.resumeWithException(it)
            }))
        requestQueue.add(getRequest("musicians",
            { response ->
                val resp = JSONArray(response)
                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)
                    list.add(i, Performer( performerID = item.getInt("id"), name = item.getString("name"), image = item.getString("image"), description = item.getString("description")))
                }
                cont.resume(list)
            },
            {
                cont.resumeWithException(it)
            }))
    }

    suspend fun getPerformer(performerId:Int) = suspendCoroutine<PerformerDetails>{ cont->
        requestQueue.add(getRequest("musicians/$performerId",
            { response ->
                val item = JSONObject(response)
                val arrayAlbums = JSONArray(item.getString("albums"))
                val listalbums = mutableListOf<Album>()
                for (i in 0 until arrayAlbums.length())
                {
                    val item = arrayAlbums.getJSONObject(i)
                    val album =  Album(albumId = item.getInt("id"),name = item.getString("name"), cover = item.getString("cover"), recordLabel = item.getString("recordLabel"), releaseDate = item.getString("releaseDate"), genre = item.getString("genre"), description = item.getString("description"))
                    listalbums.add(i, album)
                }

                val performer = PerformerDetails(performerID = item.getInt("id"), name = item.getString("name"), image = item.getString("image"), description = item.getString("description"), albums = listalbums )
                cont.resume(performer)
            },
            {
                requestQueue.add(getRequest("bands/$performerId",
                    { response ->
                        val item = JSONObject(response)
                        val arrayAlbums = JSONArray(item.getString("albums"))
                        val listalbums = mutableListOf<Album>()
                        for (i in 0 until arrayAlbums.length())
                        {
                            val item = arrayAlbums.getJSONObject(i)
                            val album =  Album(albumId = item.getInt("id"),name = item.getString("name"), cover = item.getString("cover"), recordLabel = item.getString("recordLabel"), releaseDate = item.getString("releaseDate"), genre = item.getString("genre"), description = item.getString("description"))
                            listalbums.add(i, album)
                        }

                        val performer = PerformerDetails(performerID = item.getInt("id"), name = item.getString("name"), image = item.getString("image"), description = item.getString("description"), albums = listalbums )
                        cont.resume(performer)
                    },
                    {
                        throw it
                    }))
            }))
    }

    private fun getRequest(path:String, responseListener: Response.Listener<String>, errorListener: Response.ErrorListener): StringRequest {
        return StringRequest(Request.Method.GET, BASE_URL+path, responseListener,errorListener)
    }
    private fun postRequest(path: String, body: JSONObject, responseListener: Response.Listener<JSONObject>, errorListener: Response.ErrorListener ): JsonObjectRequest {
        return  JsonObjectRequest(Request.Method.POST, BASE_URL+path, body, responseListener, errorListener)
    }
    private fun putRequest(path: String, body: JSONObject, responseListener: Response.Listener<JSONObject>, errorListener: Response.ErrorListener ): JsonObjectRequest {
        return  JsonObjectRequest(Request.Method.PUT, BASE_URL+path, body, responseListener, errorListener)
    }
}