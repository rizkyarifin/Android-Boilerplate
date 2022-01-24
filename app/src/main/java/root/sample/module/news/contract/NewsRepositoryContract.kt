package root.sample.module.news.contract

import root.sample.model.entity.NewsEntity
import root.sample.network.response.ApiResult

interface NewsRepositoryContract {
    suspend fun onRequestNews() : ApiResult<NewsEntity>
}