package com.example.demo_sports.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.demo_sports.model.ResponseFromApi

class SquadDetailViewModel: ViewModel() {

    val sqaudDetailData : MutableLiveData<ResponseFromApi> = MutableLiveData()
}