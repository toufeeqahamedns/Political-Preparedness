package com.example.android.politicalpreparedness.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.android.politicalpreparedness.database.ElectionDao
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.VoterInfoResponse

class Repository(application: Application) {

    private var electionDao: ElectionDao = ElectionDatabase.getInstance(application).electionDao

    suspend fun fetchUpcomingElections(): List<Election> {
        return CivicsApi.retrofitService.getElections().elections
    }

    suspend fun fetchVoterInfo(address: String, electionId: Long): VoterInfoResponse {
        return CivicsApi.retrofitService.getVoterInfo(address, electionId, false)
    }

    val savedElections = electionDao.getSavedElections()

    suspend fun getSavedElectionById(electionId: Int): Election? {
        return electionDao.getSavedElectionById(electionId)
    }

    suspend fun deleteElectionById(electionId: Int) {
        electionDao.deleteElectionById(electionId)
    }

    suspend fun saveElection(election: Election) {
        electionDao.saveElection(election)
    }
}