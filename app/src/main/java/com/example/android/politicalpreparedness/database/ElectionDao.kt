package com.example.android.politicalpreparedness.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android.politicalpreparedness.network.models.Election

@Dao
interface ElectionDao {

    @Query("Delete from election_table where id = :id")
    suspend fun deleteElectionById(id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveElection(election: Election)

    @Query("Select * from election_table")
    fun getSavedElections(): LiveData<List<Election>>

    @Query("Select * from election_table where id = :id")
    suspend fun getSavedElectionById(id: Int): Election?
}