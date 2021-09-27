package com.camp.mvvmkoin.view

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.*
import com.camp.mvvmkoin.localdb.AppResult
import com.camp.mvvmkoin.model.Hits
import com.camp.mvvmkoin.repo.Repository
import kotlinx.coroutines.launch

internal class AppViewModel(private var repository: Repository) :ViewModel(){
    val listOfHits = MutableLiveData<List<Hits>>()
    fun getMutableLiveData() {
        viewModelScope.launch() {
            when (val result = repository.getAllImages()) {
                is AppResult.Success -> {
                    listOfHits.value = result.successData!!
                }
                is AppResult.Error -> Log.d("dwd", "error")
            }
        }
    }
}