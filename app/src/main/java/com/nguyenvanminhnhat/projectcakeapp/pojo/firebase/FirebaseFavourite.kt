package com.nguyenvanminhnhat.projectcakeapp.pojo.firebase

import android.net.Uri
import android.util.Log
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.CategoryModel
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.FavouriteModel
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.ImageModel
import java.io.File

class FirebaseFavourite {
    private lateinit var resultFav: IResultFav
    private var root: DatabaseReference? = null
    private var favQuery: Query?= null
    val listFav = mutableListOf<FavouriteModel>()

    fun firebaseFavourite(resultFav : IResultFav){
        this.resultFav = resultFav
        root = FirebaseDatabase.getInstance().getReference("ImageFavourite")
        favQuery = root?.orderByChild("nameCake")
    }

    fun removeFav(nameCake: String){
        listFav.clear()
        favQuery?.equalTo(nameCake)?.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot: DataSnapshot in snapshot.children){
                    dataSnapshot.ref.removeValue()
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e("ABC", "Worng", error.toException())
            }

        })
    }

    fun getListFav(){
        listFav.clear()
        root?.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot: DataSnapshot in snapshot.children) {
                    val favModel = FavouriteModel()
                    val img = dataSnapshot.child("imageCake").value.toString()
                    val name = dataSnapshot.child("nameCake").value.toString()

                    favModel.apply {
                        nameCake = name
                        imgCake = img
                    }
                    listFav.add(favModel)
                    Log.d("ktra", img)
                }
                resultFav.onSuccess(listFav)

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    interface IResultFav {
        fun onSuccess(success: List<FavouriteModel>)
        fun onError(error: String)
    }
}