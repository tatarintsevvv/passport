package online.tatarintsev.testforpassport.data.repository

import online.tatarintsev.testforpassport.data.model.ApiHelper

class ImagesRepository(private val apiHelper: ApiHelper) {

    suspend fun getImages() = apiHelper.getImages()
}