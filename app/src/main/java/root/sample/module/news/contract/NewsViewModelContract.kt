package root.sample.module.news.contract

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import root.sample.model.entity.NewsViewParam

interface NewsViewModelContract {

    fun onRequestNews() : Job

    fun observeNewsList() : LiveData<NewsViewParam>
    fun observeProgress(): LiveData<Boolean>
    fun observeError(): LiveData<String>
    fun observeListData() : Flow<PagingData<NewsViewParam.NewsArticleViewParam>>
}