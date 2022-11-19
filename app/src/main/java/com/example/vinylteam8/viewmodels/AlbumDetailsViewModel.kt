package com.example.vinylteam8.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.vinylteam8.database.VinylRoomDatabase
import com.example.vinylteam8.models.Album
import com.example.vinylteam8.models.AlbumDetails
import com.example.vinylteam8.repositories.AlbumRepository
import com.example.vinylteam8.ui.album.AlbumDetailsFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AlbumDetailsViewModel(application: Application, albumId: Int) : AndroidViewModel(application) {
    private val albumsRepository = AlbumRepository(application, VinylRoomDatabase.getDatabase(application.applicationContext).albumsDao())

    private val _album = MutableLiveData<AlbumDetails>()

    val album: LiveData<AlbumDetails>
        get() = _album

    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    val id:Int = albumId

    init {
        refreshDataFromNetwork()
    }

    private fun refreshDataFromNetwork() {
        try {
            viewModelScope.launch(Dispatchers.Default){
                withContext(Dispatchers.IO){
                    var data = albumsRepository.refreshDataDetails(id)
                    _album.postValue(data)
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

    class Factory(val app: Application, val albumId: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AlbumDetailsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AlbumDetailsViewModel(app, albumId) as T
            }
            throw IllegalArgumentException("Unable to construct detailsviewmodel")
        }
    }
}