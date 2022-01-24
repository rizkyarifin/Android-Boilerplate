package root.sample.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class BaseBindingViewHolder(var binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.root.tag = this
    }
}
