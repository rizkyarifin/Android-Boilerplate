package root.sample.module.news

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import root.sample.model.entity.NewsEntity
import root.sample.model.entity.NewsViewParam
import root.sample.module.news.contract.NewsRepositoryContract
import root.sample.network.api.NewsApiService
import root.sample.network.response.ApiResult
import root.sample.utils.getError
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val newsApiService: NewsApiService
) : NewsRepositoryContract, PagingSource<Int, NewsViewParam.NewsArticleViewParam>() {

    val listData = Pager(PagingConfig(pageSize = 10)) {
        this
    }.flow

    override suspend fun onRequestNews(): ApiResult<NewsEntity> {
        return try {
            val response = newsApiService.requestListNews(1, 10)
            response.getResult(response)
        } catch (e: Exception) {
            e.getError()
        }
    }

    override fun getRefreshKey(state: PagingState<Int, NewsViewParam.NewsArticleViewParam>): Int = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsViewParam.NewsArticleViewParam> {
        val pageNumber = params.key ?: 1
        return try {
            val response = newsApiService.requestListNews(pageNumber, 10)

            val nextPageNumber: Int = pageNumber + 1

            LoadResult.Page(
                data = NewsEntity.mapToViewParam(response).articles,
                prevKey = null,
                nextKey = nextPageNumber
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}