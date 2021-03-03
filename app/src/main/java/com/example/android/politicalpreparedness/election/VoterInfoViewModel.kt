package com.example.android.politicalpreparedness.election

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.database.ElectionDao
import com.example.android.politicalpreparedness.network.models.Division
import com.example.android.politicalpreparedness.network.models.VoterInfoResponse
import com.example.android.politicalpreparedness.repository.Repository
import kotlinx.coroutines.launch
import java.lang.Exception

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

    init {
        _navigateToStateLocation.value = null
        _navigateToBallot.value = null
    }

    //TODO: Add live data to hold voter info

    //TODO: Add var and methods to populate voter info
    fun getVoterInfo(electionId: Int, division: Division) {
        viewModelScope.launch {
            try {
                if (division.country.isNotEmpty() && division.state.isNotEmpty()) {
                    _voterInfo.value = repository.fetchVoterInfo(division.country + ", " + division.state, electionId.toLong())
                }
            } catch (e: Exception) {

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

    }

    //TODO: Add var and methods to save and remove elections to local database
    //TODO: cont'd -- Populate initial state of save button to reflect proper action based on election saved status

    /**
     * Hint: The saved state can be accomplished in multiple ways. It is directly related to how elections are saved/removed from the database.
     */

}