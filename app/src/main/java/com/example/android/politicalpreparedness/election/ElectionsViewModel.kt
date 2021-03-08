package com.example.android.politicalpreparedness.election

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.repository.Repository
import com.example.android.politicalpreparedness.util.ApiStatus
import kotlinx.coroutines.launch
import java.lang.Exception

class ElectionsViewModel(application: Application) : ViewModel() {

    private val repository = Repository(application)

    private val _upcomingElections = MutableLiveData<List<Election>>()
    val upcomingElections: LiveData<List<Election>>
        get() = _upcomingElections

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    val savedElections = repository.savedElections

    init {
        fetchUpcomingElection()
    }

    private fun fetchUpcomingElection() {
        viewModelScope.launch {
            try {
                _status.value = ApiStatus.LOADING
                _upcomingElections.value = repository.fetchUpcomingElections()
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                Log.i("ElectionsFragment", e.toString())
                _upcomingElections.value = emptyList()
                _status.value = ApiStatus.ERROR
            }
        }
    }
}