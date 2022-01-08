package online.tatarintsev.testforpassport.data.states

import online.tatarintsev.testforpassport.data.model.ImageList

data class GalleryUiState(
    val showProgress : Boolean,
    val imageList : List<String>?,
    val error : Int?
)