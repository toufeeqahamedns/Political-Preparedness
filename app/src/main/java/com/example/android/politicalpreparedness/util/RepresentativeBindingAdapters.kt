package com.example.android.politicalpreparedness.util

import android.view.View
import android.widget.*
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.election.ElectionListAdapter
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.Representative
import com.example.android.politicalpreparedness.representative.RepresentativeListAdapter

@BindingAdapter("profileImage")
fun fetchImage(view: ImageView, src: String?) {
    src?.let {
        val uri = src.toUri().buildUpon().scheme("https").build()
        Glide.with(view.context).load(uri)
                .placeholder(R.drawable.ic_profile)
                .error(R.drawable.ic_profile)
                .circleCrop()
                .into(view)
    }
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

@BindingAdapter("electionList")
fun bindElectionToRecyclerView(recyclerView: RecyclerView, elections: List<Election>?) {
    val adapter = recyclerView.adapter as ElectionListAdapter
    adapter.submitList(elections)
}

@BindingAdapter("representativeList")
fun bindRepresentativeToRecyclerView(recyclerView: RecyclerView, representatives: List<Representative>?) {
    val adapter = recyclerView.adapter as RepresentativeListAdapter
    adapter.submitList(representatives)
}

@InverseBindingAdapter(attribute = "stateValue")
fun Spinner.getNewValue(): String {
    val states: Array<String> = resources.getStringArray(R.array.states)
    return states[this.selectedItemPosition]
}

@BindingAdapter("stateValueAttrChanged")
fun setStateListener(spinner: Spinner, stateChange: InverseBindingListener?) {
    if (stateChange == null) {
        spinner.onItemSelectedListener = null
    } else {
        val listener: AdapterView.OnItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                stateChange.onChange()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                stateChange.onChange()
            }
        }
        spinner.onItemSelectedListener = listener
    }
}

@BindingAdapter("stateValue")
fun Spinner.setNewValue(value: String?) {
    val adapter = toTypedAdapter<String>(this.adapter as ArrayAdapter<*>)
    val position = when (adapter.getItem(0)) {
        is String -> adapter.getPosition(value)
        else -> this.selectedItemPosition
    }
    if (position >= 0) {
        setSelection(position)
    }
}

inline fun <reified T> toTypedAdapter(adapter: ArrayAdapter<*>): ArrayAdapter<T> {
    return adapter as ArrayAdapter<T>
}


enum class ApiStatus {
    LOADING,
    ERROR,
    DONE
}