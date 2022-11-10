package com.example.demo_sports.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
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
            if (it != null) {
                matchListAdapter.setRepoListData(it)
                matchListAdapter.notifyDataSetChanged()

            } else {

            }
        })
        squadViewModel.makeApiCall()

    }

    private fun initViewModel(): SquadViewModel {
        val viewModel = ViewModelProvider(this).get(SquadViewModel::class.java)
        return viewModel
    }

    private fun setUpBinding(inflater: LayoutInflater) {
        fragmentSquadBinding = FragmentSquadBinding.inflate(inflater)
        fragmentSquadBinding.executePendingBindings()
        fragmentSquadBinding.recyclerSquad.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            matchListAdapter = MatchListAdapter()
            adapter = matchListAdapter
        }
    }

}