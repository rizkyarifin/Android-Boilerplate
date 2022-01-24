package root.sample.module.newsdetail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.bumptech.glide.Glide
import root.sample.R
import root.sample.base.BaseActivity
import root.sample.databinding.ActivityNewsDetailBinding
import root.sample.model.entity.NewsViewParam

class NewsDetailActivity : BaseActivity<ActivityNewsDetailBinding>() {

    companion object {
        const val NEWS_EXTRA = "news"

        fun startThisActivity(activity: Activity,
                              newsArticleViewParam: NewsViewParam.NewsArticleViewParam) {
            activity.startActivity(Intent(activity, NewsDetailActivity::class.java)
                .putExtra(NEWS_EXTRA, newsArticleViewParam))
        }
    }

    private val news by lazy { intent.getParcelableExtra<NewsViewParam.NewsArticleViewParam>(NEWS_EXTRA) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        news?.let { news ->
            with(getViewBinder()) {
                Glide.with(this@NewsDetailActivity).load(news.urlToImage).into(imgNews)
                tvTitle.text = news.title
                tvDescription.text = news.description
            }
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_news_detail
}