package online.tatarintsev.testforpassport.data.api

import online.tatarintsev.testforpassport.data.model.ImageList
import retrofit2.http.GET

interface ApiService {
    //http://app.passport.com.ru/android-dev-task.php

    @GET("android-dev-task.php")
    suspend fun getImages(): List<String>
}