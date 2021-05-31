package com.stopmeifyoucan.makneya.AddNewBujang

class AddBujangModel {
    companion object{
        var obj : AddBujangModel? = null
        fun getinstance() : AddBujangModel {
            if (obj == null){
                obj =
                    AddBujangModel()
                return obj!!
            }
            else{
                return obj!!
            }
        }
    }
    var bujang_name: String? = null
    var ggondae: String? = null
    var hurry: String? = null
    var spicy: String? = null
    var drink: String? = null
}