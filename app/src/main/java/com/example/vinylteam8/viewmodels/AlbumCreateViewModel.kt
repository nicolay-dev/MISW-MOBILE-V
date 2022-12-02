package com.example.vinylteam8.viewmodels


import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.vinylteam8.database.VinylRoomDatabase
import com.example.vinylteam8.models.Album
import com.example.vinylteam8.network.NetworkServiceAdapter
import com.example.vinylteam8.repositories.AlbumRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

class AlbumCreateViewModel(application: Application) :  AndroidViewModel(application) {

    private val albumsRepository = AlbumRepository(application, VinylRoomDatabase.getDatabase(application.applicationContext).albumsDao())

    private val _albums = MutableLiveData<List<Album>>()
    private val _album = MutableLiveData<Album>()

    val albums: LiveData<List<Album>>
        get() = _albums

    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    init {
        refreshDataFromNetwork()
    }

    private fun refreshDataFromNetwork() {
        try {
            viewModelScope.launch(Dispatchers.Default){
                withContext(Dispatchers.IO){
                    var data = albumsRepository.refreshData()
                    _albums.postValue(data)
                }
                _eventNetworkError.postValue(false)
                _isNetworkErrorShown.postValue(false)
            }
        }
        catch (e:Exception){
            _eventNetworkError.value = true
        }
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AlbumCreateViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AlbumCreateViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

    fun createAlbumFromNetwork(album: JSONObject):Int {
        var id:Int=0
        try {
            viewModelScope.launch (Dispatchers.Default){
                withContext(Dispatchers.IO){
                    var data = albumsRepository.refreshDataCreate(album)
                    _album.postValue(data)
                    id=data.albumId
                }
                _eventNetworkError.postValue(false)
                _isNetworkErrorShown.postValue(false)
            }
        }
        catch (e:Exception){
            _eventNetworkError.value = true
        }
        return id
    }

}
