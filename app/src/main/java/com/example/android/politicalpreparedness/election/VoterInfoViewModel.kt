package com.example.android.politicalpreparedness.election

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.network.models.Division
import com.example.android.politicalpreparedness.network.models.VoterInfoResponse
import com.example.android.politicalpreparedness.repository.Repository
import com.example.android.politicalpreparedness.util.ApiStatus
import kotlinx.coroutines.launch

class VoterInfoViewModel(private val application: Application) : ViewModel() {

    private val repository = Repository(application)

    private val _voterInfo = MutableLiveData<VoterInfoResponse>()
    val voterInfo: LiveData<VoterInfoResponse>
        get() = _voterInfo

    private val _navigateToStateLocation = MutableLiveData<String>()
    val navigateToStateLocation: LiveData<String>
        get() = _navigateToStateLocation

    private val _navigateToBallot = MutableLiveData<String>()
    val navigateToBallot: LiveData<String>
        get() = _navigateToBallot

    private val _isSavedElection = MutableLiveData<Boolean>()
    val isSavedElection: LiveData<Boolean>
        get() = _isSavedElection

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    init {
        _voterInfo.value = null
        _isSavedElection.value = false
        _navigateToStateLocation.value = null
        _navigateToBallot.value = null
    }

    fun getVoterInfo(electionId: Int, division: Division) {
        viewModelScope.launch {
            try {
                repository.getSavedElectionById(electionId).let {
                    Log.i("VoterInfoViewModel", "Saved Election $it")
                    it?.let {
                        _isSavedElection.value = true
                    }
                }
            } catch (e: Exception) {
                Log.i("VoterInfoViewModel", "Problem in getting saved elections ${e.message}")
            }

            try {
                _status.value = ApiStatus.LOADING
                val info = repository.fetchVoterInfo(division.country + ", " + division.state, electionId.toLong())
                _voterInfo.value = info
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                Log.i("VoterInfoViewModel", "Problem in getting voterInfo ${e.message}")
            }
        }
    }

    fun navigateToStateLocations() {
        _navigateToStateLocation.value =
                voterInfo.value?.state?.get(0)?.electionAdministrationBody?.votingLocationFinderUrl
    }

    fun navigateToBallot() {
        _navigateToBallot.value =
                voterInfo.value?.state?.get(0)?.electionAdministrationBody?.ballotInfoUrl
    }

    fun navigateToUrlCompleted() {
        _navigateToStateLocation.value = null
        _navigateToBallot.value = null
    }

    fun followElection() {
        viewModelScope.launch {
            try {
                if (_isSavedElection.value == true) {
                    voterInfo.value?.election?.id?.let {
                        repository.deleteElectionById(it)
                    }
                    _isSavedElection.value = false
                } else {
                    voterInfo.value?.election?.let {
                        repository.saveElection(it)
                    }
                    _isSavedElection.value = true
                }
            } catch (e: Exception) {
                Log.i("VoterInfoViewModel", "Problem in saving/deleting election ${e.message}")
            }
        }
    }
}