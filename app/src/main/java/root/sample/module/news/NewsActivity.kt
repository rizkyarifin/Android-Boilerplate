package root.sample.module.news

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import root.sample.R
import root.sample.base.BaseActivity
import root.sample.databinding.ActivityNewsBinding
import root.sample.module.PageRouter
import root.sample.module.dialog.ProgressDialogFragment
import root.sample.utils.reObserve

@AndroidEntryPoint
class NewsActivity : BaseActivity<ActivityNewsBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_news

    private val viewModel: NewsViewModel by viewModels()
    private val newsAdapter: NewsAdapter by lazy { NewsAdapter(this) }
    private val newsPagingAdapter: NewsPagingAdapter by lazy { NewsPagingAdapter(this) }
    private val progressDialog by lazy { ProgressDialogFragment.newInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        subscribeLiveData()
    }

    private fun initView() {
        with(getViewBinder()) {
            rvNews.adapter = newsPagingAdapter
            newsPagingAdapter.onClickNews = {
                PageRouter.goToNewsDetail(this@NewsActivity, it)
            }
        }
    }

    private fun subscribeLiveData() {
        with(viewModel) {
            lifecycleScope.launch {
                viewModel.observeListData().collect {
                    newsPagingAdapter.submitData(it)
                }
            }

            observeProgress().reObserve(this@NewsActivity) {
                if (it) progressDialog.show(supportFragmentManager) else progressDialog.hide()
            }

            observeNewsList().reObserve(this@NewsActivity) {
                newsAdapter.addList(it.articles)
            }

            observeError().reObserve(this@NewsActivity) {
                showErrorSnackBar(it)
            }
        }
    }
}