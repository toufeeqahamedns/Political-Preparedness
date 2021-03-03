package com.example.android.politicalpreparedness.repository

import android.app.Application
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.VoterInfoResponse

class Repository(private val application: Application) {

    init {

    }

    suspend fun fetchUpcomingElections(): List<Election> {
        return CivicsApi.retrofitService.getElections().elections
    }

    suspend fun fetchVoterInfo(address: String, electionId: Long): VoterInfoResponse {
        return CivicsApi.retrofitService.getVoterInfo(address, electionId, false)
    }
}