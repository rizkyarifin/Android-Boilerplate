package root.sample.module.news.contract

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Job
import root.sample.model.entity.NewsViewParam

interface NewsViewModelContract {

    fun onRequestNews() : Job

    fun observeNewsList() : LiveData<NewsViewParam>
    fun observeProgress(): LiveData<Boolean>
    fun observeError(): LiveData<String>
}