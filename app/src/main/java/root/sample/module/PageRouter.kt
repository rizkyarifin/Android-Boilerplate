package root.sample.module

import android.app.Activity
import root.sample.model.entity.NewsViewParam
import root.sample.module.newsdetail.NewsDetailActivity

object PageRouter {
    fun goToNewsDetail(activity: Activity,
                       newsArticleViewParam: NewsViewParam.NewsArticleViewParam) {
        NewsDetailActivity.startThisActivity(activity, newsArticleViewParam)
    }
}