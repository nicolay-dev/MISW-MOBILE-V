package com.example.vinylteam8.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.vinylteam8.database.VinylRoomDatabase
import com.example.vinylteam8.models.Performer
import com.example.vinylteam8.network.NetworkServiceAdapter
import com.example.vinylteam8.repositories.PerformerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PerformerViewModel(application: Application): AndroidViewModel(application) {

    private val performerRepository = PerformerRepository(application, VinylRoomDatabase.getDatabase(application.applicationContext).performerDao())

    private val _performers = MutableLiveData<List<Performer>>()

    val performers: LiveData<List<Performer>>
        get() = _performers

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
                    var data = performerRepository.refreshData()
                    _performers.postValue(data)
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
            if (modelClass.isAssignableFrom(PerformerViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return PerformerViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}