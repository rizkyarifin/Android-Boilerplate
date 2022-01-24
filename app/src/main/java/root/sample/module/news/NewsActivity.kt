package root.sample.module.news

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import root.sample.R
import root.sample.base.BaseActivity
import root.sample.databinding.ActivityNewsBinding
import root.sample.module.dialog.ProgressDialogFragment
import root.sample.utils.reObserve

@AndroidEntryPoint
class NewsActivity : BaseActivity<ActivityNewsBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_news

    private val viewModel: NewsViewModel by viewModels()
    private val newsAdapter: NewsAdapter by lazy { NewsAdapter(this) }
    private val progressDialog by lazy { ProgressDialogFragment.newInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        subscribeLiveData()

    }

    private fun initView() {
        with(getViewBinder()) {
            rvNews.adapter = newsAdapter
        }
    }

    private fun subscribeLiveData() {
        with(viewModel) {

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