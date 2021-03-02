package com.example.android.politicalpreparedness.repository

import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Election

class Repository() {

    suspend fun fetchUpcomingElections(): List<Election> {
        return CivicsApi.retrofitService.getElections().elections
    }
}