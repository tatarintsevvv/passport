package online.tatarintsev.testforpassport.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import online.tatarintsev.testforpassport.R
import online.tatarintsev.testforpassport.data.model.ApiHelper
import online.tatarintsev.testforpassport.data.model.ImageList
import online.tatarintsev.testforpassport.data.model.RetrofitBuilder
import online.tatarintsev.testforpassport.data.repository.ImagesRepository
import online.tatarintsev.testforpassport.data.states.GalleryUiState

class GalleryViewModel : ViewModel() {

    val _uiState = MutableLiveData<GalleryUiState>()
    var uiState: LiveData<GalleryUiState> = _uiState


    val mainRepository = ImagesRepository(ApiHelper(RetrofitBuilder.apiService))

    init {
        retrieveGalleryList()
    }
    fun retrieveGalleryList() {
        viewModelScope.launch {
            runCatching {
                actionUiState(showProgress = true)
                mainRepository.getImages()
            }.onSuccess {
                actionUiState(images = it)
             }.onFailure {
                it.printStackTrace()
                actionUiState(error = R.string.internet_failure_error)
            }
        }

    }

    private fun actionUiState(
        showProgress : Boolean = false,
        images: List<String>? = null,
        error : Int? = null
    ) {
        val dataState = GalleryUiState(showProgress, images, error)
        _uiState.postValue(dataState)
    }

}