package root.sample.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseBindingAdapter : RecyclerView.Adapter<BaseBindingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BaseBindingViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
        getLayoutResource(), parent, false))

    override fun onBindViewHolder(holder: BaseBindingViewHolder, position: Int) {
        updateBinding(holder.binding, position)
    }

    protected abstract fun getLayoutResource(): Int

    protected abstract fun updateBinding(binding: ViewDataBinding, position: Int)

}
