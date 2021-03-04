package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.databinding.FragmentElectionBinding
import com.example.android.politicalpreparedness.election.adapter.ElectionListAdapter
import com.example.android.politicalpreparedness.election.adapter.ElectionListener

class ElectionsFragment : Fragment() {

    private val viewModel: ElectionsViewModel by lazy {
        ViewModelProvider(this, ElectionsViewModelFactory(requireActivity().application)).get(ElectionsViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding = FragmentElectionBinding.inflate(inflater)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        binding.upcomingRecyclerview.adapter = ElectionListAdapter(ElectionListener {
            findNavController().navigate(ElectionsFragmentDirections
                    .actionElectionsFragmentToVoterInfoFragment(it.id, it.division))
        })

        binding.savedRecyclerview.adapter = ElectionListAdapter(ElectionListener {
            findNavController().navigate(ElectionsFragmentDirections
                    .actionElectionsFragmentToVoterInfoFragment(it.id, it.division))
        })

        return binding.root
    }
}