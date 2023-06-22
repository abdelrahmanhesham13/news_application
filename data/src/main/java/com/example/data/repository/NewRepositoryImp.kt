package com.example.data.repository

import com.example.common.Mapper
import com.example.common.PagingModel
import com.example.data.model.NewDTO
import com.example.domain.entity.NewEntity
import com.example.domain.repository.NewRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewRepositoryImp @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val newMapper: Mapper<NewDTO, NewEntity>
) : NewRepository {

    override suspend fun getNews(page: Int): Flow<PagingModel<List<NewEntity>>> {
        return flow {
            try {
                val data = remoteDataSource.getNews(page)
                emit(
                    PagingModel(
                        data = newMapper.fromList(data.data),
                        total = data.total,
                        currentPage = page
                    )
                )

            } catch (e: Exception) {
                e.printStackTrace()
                throw e
            }
        }
    }
}