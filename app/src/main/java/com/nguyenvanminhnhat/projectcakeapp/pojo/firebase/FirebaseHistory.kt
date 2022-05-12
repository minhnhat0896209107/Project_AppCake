package com.nguyenvanminhnhat.projectcakeapp.pojo.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.nguyenvanminhnhat.projectcakeapp.itf.IResultHistory
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.CakeModel
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.HistoryModel

class FirebaseHistory {
    var firebaseAuth: FirebaseAuth? = null
    private var root: DatabaseReference? = null
    private lateinit var iResultHistory: IResultHistory
    private var nCake = ""
    private var pr = ""
    val listHistory = mutableListOf<HistoryModel>()
    fun firebaseHistory(iResultHistory: IResultHistory){
        this.iResultHistory = iResultHistory
        root = FirebaseDatabase.getInstance().reference.child("HistoryBuy")
    }

    fun getHistory(){
        listHistory.clear()
        root?.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children){
                    val historyModel = snapshot.getValue(HistoryModel::class.java)
                    val cakeModel = CakeModel()

                    val add = dataSnapshot.child("address").value.toString()
                    val nUser = dataSnapshot.child("nameUser").value.toString()
                    val p = dataSnapshot.child("phone").value.toString()
                    val t = dataSnapshot.child("time").value.toString()
                    val totalP = dataSnapshot.child("totalPrice").value.toString()
                    val countt = dataSnapshot.child("cake").childrenCount
                    historyModel?.apply {
                        address = add
                        nameUser = nUser
                        phoneUser = p
                        time = t
                        totalPrice = totalP.toLong()
                        count = countt
                    }
                    historyModel?.let { listHistory.add(it) }
                }
                iResultHistory.onSuccess(listHistory)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

}