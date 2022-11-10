package com.example.demo_sports.ui.fragment

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demo_sports.R
import com.example.demo_sports.databinding.FragmentSquadBinding
import com.example.demo_sports.model.ResponseFromApi
import com.example.demo_sports.ui.adapter.MatchListAdapter
import com.example.demo_sports.viewmodel.SquadViewModel


class SquadFragment : Fragment() {

    private lateinit var fragmentSquadBinding: FragmentSquadBinding
    private lateinit var squadViewModel: SquadViewModel
    private lateinit var matchListAdapter: MatchListAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        squadViewModel = initViewModel()
        setUpBinding(inflater)
        setData()
        return fragmentSquadBinding.root
    }

    private fun setData() {
        squadViewModel.getData().observe(requireActivity(), Observer<List<ResponseFromApi>?> {
            fragmentSquadBinding.progressBar.visibility = View.INVISIBLE

            if (it != null) {
                //update our apdater
                fragmentSquadBinding.recyclerSquad.visibility = View.VISIBLE
                fragmentSquadBinding.noInternet.visibility = View.GONE
                matchListAdapter.setRepoListData(it)
                matchListAdapter.notifyDataSetChanged()
            } else {
                fragmentSquadBinding.recyclerSquad.visibility = View.GONE
                fragmentSquadBinding.noInternet.visibility = View.VISIBLE
                fragmentSquadBinding.wifiImage.visibility = View.INVISIBLE
                fragmentSquadBinding.txtNoInternet.text = getString(R.string.error)
            }
        })

        if (!checkForInternet(requireContext())) {
            fragmentSquadBinding.wifiImage.visibility = View.VISIBLE
            fragmentSquadBinding.noInternet.visibility = View.VISIBLE
            fragmentSquadBinding.txtNoInternet.text = getString(R.string.no_internet)
        } else {
            fragmentSquadBinding.noInternet.visibility = View.GONE
            fragmentSquadBinding.progressBar.visibility = View.VISIBLE
            squadViewModel.makeApiCall()
        }


    }

    private fun initViewModel(): SquadViewModel {
        val viewModel = ViewModelProvider(this).get(SquadViewModel::class.java)
        return viewModel
    }

    private fun setUpBinding(inflater: LayoutInflater) {
        fragmentSquadBinding = FragmentSquadBinding.inflate(inflater)
        fragmentSquadBinding.executePendingBindings()
        fragmentSquadBinding.recyclerSquad.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            matchListAdapter = MatchListAdapter()
            adapter = matchListAdapter
        }
    }

    private fun checkForInternet(context: Context): Boolean {

        // register activity with the connectivity manager service
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // if the android version is equal to M
        // or greater we need to use the
        // NetworkCapabilities to check what type of
        // network has the internet connection
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // Returns a Network object corresponding to
            // the currently active default data network.

            val network = connectivityManager.activeNetwork ?: return false

            // Representation of the capabilities of an active network.
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                // Indicates this network uses a Wi-Fi transport,
                // or WiFi has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                // Indicates this network uses a Cellular transport. or
                // Cellular has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                // else return false
                else -> false
            }
        } else {
            // if the android version is below M
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }
}