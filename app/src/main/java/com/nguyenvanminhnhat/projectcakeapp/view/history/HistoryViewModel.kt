package com.nguyenvanminhnhat.projectcakeapp.view.history

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nguyenvanminhnhat.projectcakeapp.itf.IResultHistory
import com.nguyenvanminhnhat.projectcakeapp.pojo.firebase.FirebaseHistory
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.HistoryModel

class HistoryViewModel : ViewModel(), IResultHistory {
    private var repo : FirebaseHistory = FirebaseHistory()
    private val historyLiveData = MutableLiveData<List<HistoryModel>>()
    var history = historyLiveData

    init {
        repo.firebaseHistory(this)
    }

    fun getHistory(){
        repo.getHistory()
    }
    override fun onSuccess(success: List<HistoryModel>) {
        historyLiveData.postValue(success)
    }
}