package root.sample.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.snackbar.Snackbar
import root.sample.R

abstract class BaseActivity<out T : ViewDataBinding> : AppCompatActivity() {

    private lateinit var viewBinder: T

    protected fun getViewBinder() = viewBinder

    @LayoutRes
    abstract fun getLayoutId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performViewBinding()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    private fun performViewBinding() {
        viewBinder = DataBindingUtil.setContentView(this, getLayoutId())
        viewBinder.executePendingBindings()
    }

    protected fun showSnackBar(message: String) {
        Snackbar.make(
            viewBinder.root, message,
            Snackbar.LENGTH_LONG
        ).show()
    }

    protected fun showErrorSnackBar(message: String) {
        Snackbar.make(viewBinder.root, message, Snackbar.LENGTH_LONG)
            .setBackgroundTint(ContextCompat.getColor(this, R.color.red_error))
            .show()
    }

}
