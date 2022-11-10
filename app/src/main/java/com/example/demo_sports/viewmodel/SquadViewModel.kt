package com.example.demo_sports.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demo_sports.model.ResponseFromApi
import com.example.demo_sports.network.RetroInstance
import com.example.sports_demo.network.RetroService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SquadViewModel : ViewModel() {
    var responseFromApi: MutableLiveData<List<ResponseFromApi>?>
    var responseToArrayList: ArrayList<ResponseFromApi>
    var responsefromApiToMutableList: MutableLiveData<List<ResponseFromApi>?>


    init {
        responseFromApi = MutableLiveData()
        responseToArrayList = ArrayList()
        responsefromApiToMutableList = MutableLiveData()
    }

    fun getData(): MutableLiveData<List<ResponseFromApi>?> {
        return responsefromApiToMutableList
    }

    fun makeApiCall() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val retroInstance =
                    RetroInstance.getRetroInstance().create(RetroService::class.java)

                val nzinResponseFromApi = async { retroInstance.getDataForNZIN() }
                val sapkResponseFromApi = async { retroInstance.getDataForSAPK() }

                val nzinResponse = nzinResponseFromApi.await()
                val sapkResponse = sapkResponseFromApi.await()

                responseToArrayList.clear()
                responseToArrayList.add(nzinResponse)
                responseToArrayList.add(sapkResponse)

                responsefromApiToMutableList.postValue(responseToArrayList)

            } catch (e: Exception) {
                responsefromApiToMutableList.postValue(null)
            }
        }
    }
}