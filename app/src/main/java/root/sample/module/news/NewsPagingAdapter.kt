package root.sample.module.news

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import root.sample.R
import root.sample.base.BaseBindingViewHolder
import root.sample.databinding.ItemNewsBinding
import root.sample.model.entity.NewsViewParam
import root.sample.utils.State

class NewsPagingAdapter(val context: Context) :
    PagingDataAdapter<NewsViewParam.NewsArticleViewParam, BaseBindingViewHolder>(
        REPO_COMPARATOR
    ) {

    var onClickNews : ((NewsViewParam.NewsArticleViewParam) -> Unit)? = null

    private val DATA_VIEW_TYPE = 1
    private val FOOTER_VIEW_TYPE = 2

    private var state = State.LOADING

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseBindingViewHolder {
        return BaseBindingViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_news, parent, false
            ))
    }

    override fun onBindViewHolder(holder: BaseBindingViewHolder, position: Int) {
        updateBinding(holder.binding, position)
    }

    private fun updateBinding(binding: ViewDataBinding, position: Int) {
        if (binding is ItemNewsBinding) {
            with(binding) {
                val data = getItem(position)
                data?.let { news ->
                    this.root.setOnClickListener {
                        onClickNews?.invoke(news)
                    }
                    Glide.with(context).load(news.urlToImage).into(imgNewsBanner)
                    txtNewsName.text = news.title
                }
            }
        }
    }

    companion object {
        private val REPO_COMPARATOR =
            object : DiffUtil.ItemCallback<NewsViewParam.NewsArticleViewParam>() {
                override fun areItemsTheSame(
                    oldItem: NewsViewParam.NewsArticleViewParam,
                    newItem: NewsViewParam.NewsArticleViewParam
                ): Boolean =
                    oldItem.title == newItem.title

                override fun areContentsTheSame(
                    oldItem: NewsViewParam.NewsArticleViewParam,
                    newItem: NewsViewParam.NewsArticleViewParam
                ): Boolean =
                    oldItem == newItem
            }
    }
}