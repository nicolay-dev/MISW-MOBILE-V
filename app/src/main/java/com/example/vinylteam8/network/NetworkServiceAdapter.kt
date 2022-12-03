package com.example.vinylteam8.network

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.vinylteam8.models.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.stream.Collector
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
                val arrayTracks = JSONArray(item.getString("tracks"))
                val listperformer = mutableListOf<Performer>()
                val listtracks = mutableListOf<Track>()
                for (i in 0 until arrayTracks.length())
                {
                    val item = arrayTracks.getJSONObject(i)
                    val track =  Track( trackId = item.getInt("id"), name = item.getString("name"), duration = item.getString("duration"))
                    listtracks.add(i, track)
                }
                for (i in 0 until arrayPerformer.length())
                {
                    val item = arrayPerformer.getJSONObject(i)
                    val perform =  Performer( performerID = item.getInt("id"), name = item.getString("name"), image = item.getString("image"), description = item.getString("description"))
                    listperformer.add(i, perform)
                }

                val album = AlbumDetails(albumId = item.getInt("id"),name = item.getString("name"), cover = item.getString("cover"), recordLabel = item.getString("recordLabel"), releaseDate = item.getString("releaseDate"), genre = item.getString("genre"), description = item.getString("description"), performers = listperformer, tracks = listtracks )
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


    suspend fun getCollectors() = suspendCoroutine<List<Collectors>>{ cont->
        val list = mutableListOf<Collectors>()
        requestQueue.add(getRequest("collectors",
            { response ->
                val resp = JSONArray(response)
                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)
                    val collector = Collectors(collectorID = item.getInt("id"),name = item.getString("name"), telephone = item.getString("telephone"), email = item.getString("email"))
                    list.add(i, collector)
                }
                cont.resume(list)
            },
            {
                throw it
            }))
    }

    //Getcollector
    suspend fun getCollector(collectorId:Int) = suspendCoroutine<CollectorDetails>{ cont->
        requestQueue.add(getRequest("collectors/$collectorId",
            { response ->
                val item = JSONObject(response)
                val arrayPerformer = JSONArray(item.getString("favoritePerformers"))
                val listperformer = mutableListOf<Performer>()
                for (i in 0 until arrayPerformer.length())
                {
                    val item = arrayPerformer.getJSONObject(i)
                    val perform =  Performer( performerID = item.getInt("id"), name = item.getString("name"), image = item.getString("image"), description = item.getString("description"))
                    listperformer.add(i, perform)
                }

                val collector = CollectorDetails(collectorID = item.getInt("id"),name = item.getString("name"), telephone = item.getString("telephone"), email = item.getString("email"), performers = listperformer )
                cont.resume(collector)
            },
            {
                throw it
            }))
    }

    suspend fun postAlbum(body: JSONObject) = suspendCoroutine<Album>{ cont->
        requestQueue.add(postRequest("albums", body,
            { response ->
                val album=Album(albumId = response.getInt("id"),name = response.getString("name"), cover = response.getString("cover"), recordLabel = response.getString("recordLabel"), releaseDate = response.getString("releaseDate"), genre = response.getString("genre"), description = response.getString("description"))
                cont.resume(album)
            },
            {
                cont.resumeWithException(it)
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