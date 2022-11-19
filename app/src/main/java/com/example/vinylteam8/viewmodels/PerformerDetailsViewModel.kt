package com.example.vinylteam8.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.vinylteam8.database.VinylRoomDatabase
import com.example.vinylteam8.models.PerformerDetails
import com.example.vinylteam8.repositories.PerformerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PerformerDetailsViewModel(application: Application, performerId: Int) : AndroidViewModel(application){
    private val performersRepository = PerformerRepository(application, VinylRoomDatabase.getDatabase(application.applicationContext).performerDao())

    private val _performer = MutableLiveData<PerformerDetails>()

    val performer: LiveData<PerformerDetails>
        get() = _performer

    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    val id:Int = performerId

    init {
        refreshDataFromNetwork()
    }

    private fun refreshDataFromNetwork() {
        try {
            viewModelScope.launch(Dispatchers.Default){
                withContext(Dispatchers.IO){
                    var data = performersRepository.refreshDataDetails(id)
                    _performer.postValue(data)
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

    class Factory(val app: Application, val performerId: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PerformerDetailsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return PerformerDetailsViewModel(app, performerId) as T
            }
            throw IllegalArgumentException("Unable to construct detailsviewmodel")
        }
    }

}