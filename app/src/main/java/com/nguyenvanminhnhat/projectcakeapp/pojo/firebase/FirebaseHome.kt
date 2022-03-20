package com.nguyenvanminhnhat.projectcakeapp.pojo.firebase

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.firebase.database.*
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.CategoryModel
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.ImageModel
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.PopularCakeModel
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.Review


class FirebaseHome {
    private lateinit var resultImage: IResultImage
    private lateinit var resultPopular: IResultPopularImage
    private lateinit var resultCategory: IResultCategory
    private var root: DatabaseReference? = null
    val listImg = mutableListOf<ImageModel>()
    val listImgPopular = mutableListOf<PopularCakeModel>()
    var str: String? = ""
    val listCategory = mutableListOf<CategoryModel>()

    fun firebaseHome(
        resultImage: IResultImage,
        resultPopular: IResultPopularImage,
        resultCategory: IResultCategory
    ) {
        this.resultImage = resultImage
        this.resultPopular = resultPopular
        this.resultCategory = resultCategory
        root = FirebaseDatabase.getInstance().getReference("ImageCake")
    }

    fun getImage() {
        listImg.clear()
        root?.child("NewCake")?.addValueEventListener(object : ValueEventListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot: DataSnapshot in snapshot.children) {
                    val imageModel = ImageModel()
                    val uri = dataSnapshot.value.toString()
                    Log.d("Uri  = ", "$uri")
                    imageModel.image = uri
                    listImg.add(imageModel)
                }
                resultImage.onSuccess(listImg)
                Log.d("List", "${listImg.size}")
            }

            override fun onCancelled(error: DatabaseError) {
                resultImage.onError(error = error.message)
            }
        })
    }

    fun getPopularCake() {
        listImgPopular.clear()
        root?.child("PopularCake")?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot: DataSnapshot in snapshot.children) {
                    var review = Review()
                    val popularCakeModel = PopularCakeModel()
                    var listStr = mutableListOf<String>()

                    val descrip = dataSnapshot.child("descrip").value.toString()
                    val image = dataSnapshot.child("image").value.toString()
                    val name = dataSnapshot.child("name").value.toString()
                    val price = dataSnapshot.child("price").value.toString()
                    val listReview = dataSnapshot.child("review").children
                    listReview.forEach {
                        str = it.value.toString()
                        listStr.add(str!!)
                    }
                    review.rv = listStr

                    popularCakeModel.apply {
                        nameCake = name
                        imageCake = image
                        descripCake = descrip
                        priceCake = price.toInt()
                        reviewCake = review
                    }
                    listImgPopular.add(popularCakeModel)
                }
                resultPopular.onSuccessPopular(listImgPopular)
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun getCategory() {
        listCategory.clear()
        root?.child("CategoryCake")?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot: DataSnapshot in snapshot.children) {
                    val categoryModel = CategoryModel()

                    val name = dataSnapshot.child("name").value.toString()
                    val img = dataSnapshot.child("image").value.toString()
                    val price = dataSnapshot.child("price").value.toString()

                    categoryModel.apply {
                        nameCategory = name
                        imageCategory = img
                        priceCategory = price.toInt()
                    }

                    listCategory.add(categoryModel)
                }

                resultCategory.onSuccessCategory(listCategory)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    interface IResultImage {
        fun onSuccess(success: List<ImageModel>)
        fun onError(error: String)
    }

    interface IResultPopularImage {
        fun onSuccessPopular(success: List<PopularCakeModel>)
        fun onErrorPopular(error: String)
    }

    interface IResultCategory {
        fun onSuccessCategory(success: List<CategoryModel>)
        fun onErrorCategory(error: String)
    }
}