package com.example.vinylteam8.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.vinylteam8.database.VinylRoomDatabase
import com.example.vinylteam8.models.Album
import com.example.vinylteam8.models.AlbumDetails
import com.example.vinylteam8.models.Track
import com.example.vinylteam8.repositories.AlbumRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject


class AlbumTrackCreateViewModel(application: Application, albumId: Int) : AndroidViewModel(application) {

    private val albumsRepository = AlbumRepository(application, VinylRoomDatabase.getDatabase(application.applicationContext).albumsDao())

    private val _track= MutableLiveData<Track>()

    val track: LiveData<Track>
        get() = _track

    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    val id:Int = albumId


    fun createTrackFromNetwork(track: JSONObject):Int {
        var trackid:Int=0
        try {
            viewModelScope.launch (Dispatchers.Default){
                withContext(Dispatchers.IO){
                    var data = albumsRepository.refreshDataCreateTrack(track, id)
                    _track.postValue(data)
                    trackid=data.trackId
                }
                _eventNetworkError.postValue(false)
                _isNetworkErrorShown.postValue(false)
            }
        }
        catch (e:Exception){
            _eventNetworkError.value = true
        }
        return trackid
    }


    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application, val albumId: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AlbumTrackCreateViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AlbumTrackCreateViewModel(app, albumId) as T
            }
            throw IllegalArgumentException("Unable to construct detailsviewmodel")
        }
    }

}