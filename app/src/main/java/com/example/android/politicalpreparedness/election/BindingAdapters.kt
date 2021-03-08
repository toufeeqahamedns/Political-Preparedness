package com.example.android.politicalpreparedness.election

import android.view.View
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.politicalpreparedness.election.adapter.ElectionListAdapter
import com.example.android.politicalpreparedness.network.jsonadapter.ElectionAdapter
import com.example.android.politicalpreparedness.network.models.Election

@BindingAdapter("listData")
fun bindDataToRecyclerView(recyclerView: RecyclerView, elections: List<Election>?) {
    val adapter = recyclerView.adapter as ElectionListAdapter
    adapter.submitList(elections)
}

@BindingAdapter("apiStatus")
fun bindApiStatus(progressbar: ProgressBar, apiStatus: ApiStatus?) {
    when (apiStatus) {
        ApiStatus.LOADING -> {
            progressbar.visibility = View.VISIBLE
        }
        ApiStatus.DONE -> {
            progressbar.visibility = View.GONE
        }
        else -> progressbar.visibility = View.GONE
    }
}

enum class ApiStatus {
    LOADING,
    ERROR,
    DONE
}