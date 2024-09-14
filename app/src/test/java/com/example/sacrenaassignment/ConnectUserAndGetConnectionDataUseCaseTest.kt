package com.example.sacrenaassignment

import com.example.sacrenaassignment.domain.repository.AppRepository
import com.example.sacrenaassignment.fake_repo.FakeRepo
import com.example.sacrenaassignment.utils.NetworkClass
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ConnectUserAndGetConnectionDataUseCaseTest {
    private  lateinit var repo : FakeRepo

    @Before
    fun setup() {
        repo = FakeRepo()

    }

    @Test
    fun `test making getConnectedResult function call and checking the success case`() = runBlocking {
        // Set up the network repository to return success
        repo.setShouldGiveException(false)

        // Make the network call and get the result
        val data = repo.getConnectedResult()

        // Verify that the  call was successful
        TestCase.assertTrue(data is NetworkClass.Success)


    }

    @Test
    fun `test making getConnectedResult function call  and testing exception`() = runBlocking {

        repo.setShouldGiveException(true)

        // Make the  call and get the result
        val data = repo.getConnectedResult()

        // Verify that the  call was Failed and got exception
        TestCase.assertTrue(data is NetworkClass.Error)


    }

}