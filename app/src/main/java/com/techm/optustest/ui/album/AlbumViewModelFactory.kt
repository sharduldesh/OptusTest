package com.techm.optustest.ui.album

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.techm.optustest.data.repository.AlbumRepository


/**factory method to instantiate viewmodel**/
@Suppress("UNCHECKED_CAST")
class AlbumViewModelFactory(private val albumRepository: AlbumRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AlbumViewModel(albumRepository) as T
    }
}