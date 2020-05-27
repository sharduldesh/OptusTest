package com.techm.optustest.album

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.techm.optustest.TestCoroutineRule
import com.techm.optustest.data.model.AlbumResponseModel
import com.techm.optustest.data.repository.AlbumRepository
import com.techm.optustest.ui.album.AlbumViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import  com.techm.optustest.util.Result
import  com.techm.optustest.util.Constants.Companion.FAILURE_MESSAGE

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class AlbumViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()
    private val id = 5
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var albumRepository: AlbumRepository

    @Mock
    private lateinit var albumObserver: Observer<Result<List<AlbumResponseModel>>>

    /**Test for testing api call success case**/
    @Test
    fun albumResponseSuccess() {

        testCoroutineRule.runBlockingTest {
            doReturn(emptyList<AlbumResponseModel>())
                .`when`(albumRepository)
                .getAlbumApi(id)

            val albumViewModel = AlbumViewModel(albumRepository)
            albumViewModel.getAlbumList(id).observeForever(albumObserver)
            verify(albumRepository).getAlbumApi(id)
            verify(albumObserver).onChanged(Result.success(emptyList()))
            albumViewModel.getAlbumList(id).removeObserver(albumObserver)
        }
    }

    /**Test for testing api call error case**/
    @Test
    fun albumResponseFailure() {
        testCoroutineRule.runBlockingTest {
            doThrow(RuntimeException(FAILURE_MESSAGE))
                .`when`(albumRepository)
                .getAlbumApi(id)

            val albumViewModel = AlbumViewModel(albumRepository)
            albumViewModel.getAlbumList(id).observeForever(albumObserver)
            verify(albumRepository).getAlbumApi(id)
            verify(albumObserver).onChanged(Result.error(FAILURE_MESSAGE, null))
            albumViewModel.getAlbumList(id).removeObserver(albumObserver)
        }
    }
}