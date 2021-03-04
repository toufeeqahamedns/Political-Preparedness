package com.example.android.politicalpreparedness.election

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.politicalpreparedness.R
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

        viewModel.status.observe(viewLifecycleOwner, Observer {
            if (it.equals(ApiStatus.ERROR)) {
                AlertDialog.Builder(requireContext())
                        .setCancelable(false)
                        .setMessage("Election selected doesn't contain valid data, please try a different one!")
                        .setPositiveButton("Ok") { dialog, _ ->
                            // Dismiss the dialog and return to the previous screen
                            dialog.dismiss()
                            this.findNavController().popBackStack()
                        }.show()
            }
        })

        return binding.root
    }

    fun loadUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
        viewModel.navigateToUrlCompleted()
    }
}