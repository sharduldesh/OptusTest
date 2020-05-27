package com.techm.optustest.userinfo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.techm.optustest.data.model.UserInfoResponseModel
import com.techm.optustest.data.network.APIInterface
import com.techm.optustest.data.repository.UserInfoRepository
import com.techm.optustest.ui.userinfo.UserViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

import com.techm.optustest.util.Constants.Companion.FAILURE_MESSAGE
import com.techm.optustest.util.Result
import org.mockito.Spy
import org.techm.optus.CoroutineTestRule
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class UserViewModelTest {

   /* @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()*/

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Mock
    private lateinit var userRepository: UserInfoRepository

    @Spy
    private lateinit var apiService: APIInterface

    @Spy
    private lateinit var userObserver: Observer<Result<Response<List<UserInfoResponseModel>>>>

    @Mock
    private lateinit var userList: Response<List<UserInfoResponseModel>>

    @Before
    fun setUp() {
        //todo
    }

    @Test
    fun testGivenUserResponseIsSuccess() {
        userRepository = UserInfoRepository(apiService)
        coroutineTestRule.runBlockingTest {
            doReturn(emptyList<UserInfoResponseModel>())
                .`when`(userRepository).getUsersApi()

            val userViewModel = UserViewModel(userRepository)
            userViewModel.getUserList().observeForever(userObserver)
            verify(userRepository).getUsersApi()
            verify(userObserver).onChanged(Result.success(userList))
            userViewModel.getUserList().removeObserver(userObserver)
        }
    }


    @Test
    fun testGivenUserResponseIsError() {
        coroutineTestRule.runBlockingTest {
            doThrow(RuntimeException(FAILURE_MESSAGE))
                .`when`(userRepository)
                .getUsersApi()

            val userViewModel = UserViewModel(userRepository)
            userViewModel.getUserList().observeForever(userObserver)
            verify(userRepository).getUsersApi()
            verify(userObserver).onChanged(Result.error(RuntimeException(FAILURE_MESSAGE).toString() , null))
            userViewModel.getUserList().removeObserver(userObserver)
        }
    }

}
