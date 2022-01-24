package root.sample.module.news

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import root.sample.R
import root.sample.base.BaseBindingAdapter
import root.sample.base.BaseBindingViewHolder
import root.sample.databinding.ItemNewsBinding
import root.sample.model.entity.NewsViewParam

class NewsAdapter(private val context: Context) : BaseBindingAdapter() {

    private val listData = mutableListOf<NewsViewParam.NewsArticleViewParam>()

    @SuppressLint("NotifyDataSetChanged")
    fun addList(list: List<NewsViewParam.NewsArticleViewParam>) {
        listData.apply {
            clear()
            addAll(list)
        }
        notifyDataSetChanged()
    }

    override fun getLayoutResource(): Int = R.layout.item_news

    override fun updateBinding(binding: ViewDataBinding, position: Int) {
        with(binding as ItemNewsBinding) {
            val data = listData[position]
            Glide.with(context).load(data.urlToImage).into(imgNewsBanner)
            txtNewsName.text = data.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseBindingViewHolder {
        return BaseBindingViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_news, parent, false
            )
        )
    }

    override fun getItemCount(): Int = listData.size
}