package com.example.android.politicalpreparedness.election

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.android.politicalpreparedness.databinding.FragmentVoterInfoBinding

class VoterInfoFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this, VoterInfoViewModelFactory(requireActivity().application)).get(VoterInfoViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val binding = FragmentVoterInfoBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val arguments = VoterInfoFragmentArgs.fromBundle(requireArguments())

        viewModel.getVoterInfo(arguments.argElectionId, arguments.argDivision)

        viewModel.navigateToStateLocation.observe(viewLifecycleOwner, Observer {
           it?.let {
               loadUrl(it)
           }
        })

        viewModel.navigateToBallot.observe(viewLifecycleOwner, Observer {
            it?.let {
                loadUrl(it)
            }
        })

        //TODO: Populate voter info -- hide views without provided data.
        /**
        Hint: You will need to ensure proper data is provided from previous fragment.
         */


        //TODO: Handle loading of URLs

        //TODO: Handle save button UI state
        //TODO: cont'd Handle save button clicks
        return binding.root
    }

    //TODO: Create method to load URL intents
    fun loadUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
        viewModel.navigateToUrlCompleted()
    }

}