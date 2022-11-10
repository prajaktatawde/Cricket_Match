package com.example.demo_sports.ui.fragment

import android.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demo_sports.DISPLAY_ALL
import com.example.demo_sports.databinding.FragmentSquadDetailsBinding
import com.example.demo_sports.model.Players
import com.example.demo_sports.model.ResponseFromApi
import com.example.demo_sports.ui.adapter.PlayersAdapter
import com.example.demo_sports.viewmodel.SquadDetailViewModel


class SquadDetailsFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var fragmentSquadDetailsBinding: FragmentSquadDetailsBinding
    private val args: SquadDetailsFragmentArgs by navArgs()
    private lateinit var squadDetailViewModel: SquadDetailViewModel
    private lateinit var playersAdapter: PlayersAdapter
    lateinit var playerListTotal: ArrayList<Players>
    lateinit var playerListHome: ArrayList<Players>
    lateinit var playerListAway: ArrayList<Players>
    lateinit var filter_array: Array<String>
    lateinit var team_home: String
    lateinit var team_away: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val sqaudDetails = args.matchData
        squadDetailViewModel = initViewModel()
        setUpBinding(inflater)
        setUpData(sqaudDetails)
        return fragmentSquadDetailsBinding.root
    }

    private fun setUpData(sqaudDetails: ResponseFromApi) {
        squadDetailViewModel.sqaudDetailData.value = sqaudDetails
        prepareData(sqaudDetails)
        displayAllPlayers()
        spinner_SetData(sqaudDetails)
    }

    private fun spinner_SetData(sqaudDetails: ResponseFromApi) {
        team_home = sqaudDetails.teams[sqaudDetails.matchdetail.Team_Home]!!.Name_Full
        team_away = sqaudDetails.teams[sqaudDetails.matchdetail.Team_Away]!!.Name_Full

        filter_array = arrayOf(DISPLAY_ALL, team_home, team_away)
        val arrayAdapter =
            ArrayAdapter(requireContext(), R.layout.simple_spinner_item, filter_array)
        arrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        fragmentSquadDetailsBinding.spinnerFilter.setAdapter(arrayAdapter)
    }

    private fun prepareData(sqaudDetails: ResponseFromApi) {
        playerListTotal = ArrayList()
        playerListHome =
            ArrayList(sqaudDetails.teams[sqaudDetails.matchdetail.Team_Home]!!.players.values)
        playerListAway =
            ArrayList(sqaudDetails.teams[sqaudDetails.matchdetail.Team_Away]!!.players.values)
        playerListTotal.addAll(playerListHome)
        playerListTotal.addAll(playerListAway)
    }

    private fun initViewModel(): SquadDetailViewModel {
        return ViewModelProvider(this)[SquadDetailViewModel::class.java]
    }

    private fun setUpBinding(inflater: LayoutInflater) {
        fragmentSquadDetailsBinding = FragmentSquadDetailsBinding.inflate(inflater)
        fragmentSquadDetailsBinding.executePendingBindings()
        fragmentSquadDetailsBinding.recyclerSquad.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            playersAdapter = PlayersAdapter()
            adapter = playersAdapter
        }

        fragmentSquadDetailsBinding.spinnerFilter.setOnItemSelectedListener(this)

    }


    //When you select filter from spinner call this method based on selection
    private fun filterTeamHomePlayer() {
        playersAdapter.setsquadPlayerListData(playerListHome)
        playersAdapter.notifyDataSetChanged()
    }

    //When you select filter from spinner call this method based on selection
    private fun filterTeamAwayPlayer() {
        playersAdapter.setsquadPlayerListData(playerListAway)
        playersAdapter.notifyDataSetChanged()
    }

    //When you select displayAll filter from spinner call this method based on selection
    private fun displayAllPlayers() {
        playersAdapter.setsquadPlayerListData(playerListTotal)
        playersAdapter.notifyDataSetChanged()
    }

    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {
        when (filter_array.get(position)) {
            team_home -> filterTeamHomePlayer()
            team_away -> filterTeamAwayPlayer()
            DISPLAY_ALL -> displayAllPlayers()
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

}