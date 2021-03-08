package com.example.android.politicalpreparedness.representative

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.election.ApiStatus
import com.example.android.politicalpreparedness.network.models.Address
import com.example.android.politicalpreparedness.repository.Repository
import com.example.android.politicalpreparedness.representative.model.Representative
import kotlinx.coroutines.launch

class RepresentativeViewModel(application: Application) : ViewModel() {

    private val TAG = "RepresentativeViewModel"

    private val repository = Repository(application)

    private val _representatives = MutableLiveData<List<Representative>>()
    val representatives: LiveData<List<Representative>>
        get() = _representatives

    private val _address = MutableLiveData<Address>()
    val address: LiveData<Address>
        get() = _address

    private val _status: MutableLiveData<ApiStatus> = MutableLiveData()
    val status: LiveData<ApiStatus>
        get() = _status

    fun getRepresentativesFromAddress() {
        viewModelScope.launch {
            _representatives.value = arrayListOf()
            address.value?.let {
                try {
                    _status.value = ApiStatus.LOADING
                    val (offices, officials) = repository.getRepresentatives(address.value!!)
                    _representatives.value = offices.flatMap { office -> office.getRepresentatives(officials) }
                    _status.value = ApiStatus.DONE
                } catch (e: Exception) {
                    Log.i(TAG, "Could not retrieve the representatives.")
                    _status.value = ApiStatus.ERROR
                }
            }
        }
    }

    fun setAddress(addressFetched: Address?) {
        _address.value = addressFetched
    }
}
