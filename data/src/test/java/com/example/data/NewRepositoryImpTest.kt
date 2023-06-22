package com.example.data

import androidx.test.filters.SmallTest
import app.cash.turbine.test
import com.example.common.PagingModel
import com.example.data.mapper.NewDataDomainMapper
import com.example.data.repository.NewRepositoryImp
import com.example.data.repository.RemoteDataSource
import com.example.data.utils.TestDataGenerator
import com.example.domain.repository.NewRepository
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
@SmallTest
class NewRepositoryImpTest {

    @MockK
    private lateinit var remoteDataSource: RemoteDataSource

    private val newMapper = NewDataDomainMapper()

    private lateinit var newRepository: NewRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true) // turn relaxUnitFun on for all mocks
        // Create RepositoryImp before every test
        newRepository = NewRepositoryImp(
            remoteDataSource = remoteDataSource,
            newMapper = newMapper
        )
    }

    @Test
    fun `test get news remote success`() = runTest {

        val pagingModel = TestDataGenerator.generateNews()

        // Given
        coEvery { remoteDataSource.getNews(1) } returns pagingModel

        // When & Assertions
        val flow = newRepository.getNews(1)
        flow.test {
            // Expect Offer Items
            val expected = expectItem()
            Truth.assertThat(expected)
                .isEqualTo(PagingModel(newMapper.fromList(pagingModel.data), total = 6, currentPage = 1))
            expectComplete()
        }

        // Then
        coVerify { remoteDataSource.getNews(1) }
    }

    @Test(expected = Exception::class)
    fun `test get news remote fail`() = runTest {

        // Given
        coEvery { remoteDataSource.getNews(1) } throws Exception()

        // When & Assertions
        val flow = newRepository.getNews(1)
        flow.test {
            // Expect Error
            throw expectError()
        }

        // Then
        coVerify { remoteDataSource.getNews(1) }
    }

}