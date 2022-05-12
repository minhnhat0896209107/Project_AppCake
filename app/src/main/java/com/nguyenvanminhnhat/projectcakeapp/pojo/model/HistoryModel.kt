package com.nguyenvanminhnhat.projectcakeapp.pojo.model

data class HistoryModel(
    var address : String ?= null,
    var nameUser : String ?= null,
    var phoneUser: String ?= null,
    var time : String ?= null,
    var totalPrice: Long ?= 0,
    var count : Long ?= 0
//    var listCake : CakeModel ?= null
)

data class CakeModel(
    var count : Long ?= 0
//    var nameCake: List<String> ?= null,
)
