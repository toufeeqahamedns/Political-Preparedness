package com.example.android.politicalpreparedness.election

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.repository.Repository
import kotlinx.coroutines.launch
import java.lang.Exception

class ElectionsViewModel(private val application: Application): ViewModel() {

    private val repository = Repository()

    private val _upcomingElections = MutableLiveData<List<Election>>()
    val upcomingElections: LiveData<List<Election>>
        get() = _upcomingElections

    //TODO: Create live data val for saved elections

    //TODO: Create val and functions to populate live data for upcoming elections from the API and saved elections from local database

    //TODO: Create functions to navigate to saved or upcoming election voter info

    init {
        fetchUpcomingElection()
    }

    private fun fetchUpcomingElection() {
        viewModelScope.launch {
            try {
                _upcomingElections.value = repository.fetchUpcomingElections()
            } catch (e: Exception) {
                Log.i("ElectionsFragment", e.toString())
                _upcomingElections.value = emptyList()
            }
        }
    }

    fun addToSavedElections(election: Election) {
        Log.i("ElectionViewModels", "Adding to saved elections")
    }
 }