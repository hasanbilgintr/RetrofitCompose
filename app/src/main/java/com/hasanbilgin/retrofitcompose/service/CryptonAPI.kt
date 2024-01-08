package com.hasanbilgin.retrofitcompose.service

import com.hasanbilgin.retrofitcompose.model.CrytoModel
import retrofit2.Call
import retrofit2.http.GET

interface CryptonAPI {

    @GET("atilsamancioglu/K21-JSONDataSet/master/crypto.json")
    //Call retrofittiir
    fun getData():Call<List<CrytoModel>>


}