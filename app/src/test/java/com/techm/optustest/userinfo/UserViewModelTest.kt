package com.techm.optustest.userinfo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.techm.optustest.TestCoroutineRule
import com.techm.optustest.data.model.UserInfoResponseModel
import com.techm.optustest.data.repository.UserInfoRepository
import com.techm.optustest.ui.userinfo.UserViewModel
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
class UserViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var userRepository: UserInfoRepository

    @Mock
    private lateinit var userObserver: Observer<Result<List<UserInfoResponseModel>>>

    /**Test for testing api call success case**/
    @Test
    fun userResponseSuccess() {

        testCoroutineRule.runBlockingTest {
            doReturn(emptyList<UserInfoResponseModel>())
                .`when`(userRepository).getUsers()

            val userViewModel = UserViewModel(userRepository)
            userViewModel.getUserList().observeForever(userObserver)
            verify(userRepository).getUsers()
            verify(userObserver).onChanged(Result.success(emptyList()))
            userViewModel.getUserList().removeObserver(userObserver)
        }
    }

    /**Test for testing api call error case**/
    @Test
    fun userResponseFail() {
        testCoroutineRule.runBlockingTest {
            doThrow(RuntimeException(FAILURE_MESSAGE))
                .`when`(userRepository)
                .getUsers()

            val userViewModel = UserViewModel(userRepository)
            userViewModel.getUserList().observeForever(userObserver)
            verify(userRepository).getUsers()
            verify(userObserver).onChanged(Result.error(FAILURE_MESSAGE , null))
            userViewModel.getUserList().removeObserver(userObserver)
        }
    }

}
