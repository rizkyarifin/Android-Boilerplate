package root.sample.module.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import root.sample.base.BaseViewModel
import root.sample.model.entity.NewsEntity
import root.sample.model.entity.NewsViewParam
import root.sample.module.news.contract.NewsViewModelContract
import root.sample.network.response.ApiResult
import root.sample.utils.getError
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: NewsRepository
) : BaseViewModel(), NewsViewModelContract {

    private val requestNewsList = MutableLiveData<NewsViewParam>()
    private val progressLiveData = MutableLiveData<Boolean>()
    private val errorLiveData = MutableLiveData<String>()

    init {
        onRequestNews()
    }

    override fun onRequestNews() = viewModelScope.launch(Dispatchers.IO + getSupervisorJob()) {
        progressLiveData.postValue(true)
        when (val result = repository.onRequestNews()) {
            is ApiResult.Success -> {
                progressLiveData.postValue(false)
                requestNewsList.postValue(NewsEntity.mapToViewParam(result.data))
            }
            is ApiResult.Error -> {
                progressLiveData.postValue(false)
                errorLiveData.postValue(result.exception.message)
            }
        }
    }

    override fun observeNewsList(): LiveData<NewsViewParam> = requestNewsList
    override fun observeProgress(): LiveData<Boolean> = progressLiveData
    override fun observeError(): LiveData<String> = errorLiveData
}