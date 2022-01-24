package root.sample.model.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import root.sample.network.response.BaseApiResponse
import java.io.Serializable

data class NewsEntity(val totalResults: Int?,
                      val articles: List<NewsArticleEntity?>?) : BaseApiResponse() {

    data class NewsArticleEntity(
        val source: NewsArticleSourceEntity?,
        val author: String?,
        val title: String?,
        val description: String?,
        val url: String?,
        val urlToImage: String?,
        val publishedAt: String?,
        val content: String?
    )

    data class NewsArticleSourceEntity(val id: String?, val name: String?) : Serializable

    companion object {
        fun mapToViewParam(entity: NewsEntity) : NewsViewParam {
            return NewsViewParam(entity.totalResults ?: 0,
                mutableListOf<NewsViewParam.NewsArticleViewParam>().apply {
                    entity.articles?.forEach {
                        this.add(
                            NewsViewParam.NewsArticleViewParam(
                                NewsViewParam.NewsArticleSourceViewParam(
                                    it?.source?.id.orEmpty(), it?.source?.name.orEmpty()
                                ), it?.author.orEmpty(), it?.title.orEmpty(), it?.description.orEmpty(),
                                it?.url.orEmpty(), it?.urlToImage.orEmpty(), it?.publishedAt.orEmpty(),
                                it?.content.orEmpty()
                            ))
                    }
                })
        }
    }
}

@Parcelize
data class NewsViewParam(val totalResults: Int,
                      val articles: List<NewsArticleViewParam>) : BaseApiResponse(), Parcelable {

    @Parcelize
    data class NewsArticleViewParam(
        val source: NewsArticleSourceViewParam,
        val author: String,
        val title: String,
        val description: String,
        val url: String,
        val urlToImage: String,
        val publishedAt: String,
        val content: String
    ) : Parcelable

    @Parcelize
    data class NewsArticleSourceViewParam(val id: String, val name: String) : Parcelable
}