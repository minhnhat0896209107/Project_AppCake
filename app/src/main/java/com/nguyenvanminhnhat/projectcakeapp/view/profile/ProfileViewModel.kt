package com.nguyenvanminhnhat.projectcakeapp.view.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nguyenvanminhnhat.projectcakeapp.itf.IResultProfile
import com.nguyenvanminhnhat.projectcakeapp.pojo.firebase.FirebaseProfile
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.UserModel

class ProfileViewModel : ViewModel(), IResultProfile {
    private var repo: FirebaseProfile = FirebaseProfile()
    private var userLogout = MutableLiveData<Boolean>()
    private var userLiveData = MutableLiveData<UserModel>()
    var user = userLiveData

    init{
        userLogout = repo.getUserLoggedMutableLiveData()
        repo.firebaseProfile(this)
        repo.getUser()
    }

    fun getUser(){
        repo.getUser()

    }
    fun getUserLogout(): MutableLiveData<Boolean> {
        return userLogout
    }

    fun logout() {
        repo.logOut()
    }

    override fun onSuccess(success: UserModel) {
        userLiveData.postValue(success)
    }


}