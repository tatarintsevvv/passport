package online.tatarintsev.testforpassport.data.model

import online.tatarintsev.testforpassport.data.api.ApiService

class ApiHelper(private val apiService: ApiService) {
    suspend fun getImages() = apiService.getImages()
}