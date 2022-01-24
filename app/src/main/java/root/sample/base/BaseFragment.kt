package root.sample.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import root.sample.R

abstract class BaseFragment<out T: ViewDataBinding>: Fragment() {

    private lateinit var viewBinder: T

    protected fun getViewBinder() = viewBinder

    @LayoutRes
    abstract fun getLayoutId(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinder = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return viewBinder.root
    }

    protected fun showSnackBar(message: String) {
        Snackbar.make(
            viewBinder.root, message,
            Snackbar.LENGTH_LONG
        ).show()
    }

    protected fun showErrorSnackBar(message: String) {
        Snackbar.make(viewBinder.root, message, Snackbar.LENGTH_LONG)
            .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.red_error))
            .show()
    }
}
