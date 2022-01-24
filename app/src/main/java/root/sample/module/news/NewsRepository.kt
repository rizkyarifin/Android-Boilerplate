package root.sample.module.news

import android.util.Log
import root.sample.model.entity.NewsEntity
import root.sample.module.news.contract.NewsRepositoryContract
import root.sample.network.api.NewsApiService
import root.sample.network.response.ApiResult
import root.sample.utils.getError
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val newsApiService: NewsApiService
) : NewsRepositoryContract {

    override suspend fun onRequestNews(): ApiResult<NewsEntity> {
        return try {
            val response = newsApiService.requestListNews(1, 10)
            response.getResult(response)
        } catch (e: Exception) {
            e.getError()
        }
    }
}