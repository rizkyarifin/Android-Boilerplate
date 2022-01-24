package root.sample.base

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import com.google.android.material.snackbar.Snackbar
import root.sample.R

abstract class BaseDialogFragment<out T: ViewDataBinding>: DialogFragment() {

    private lateinit var viewBinder: T

    protected fun getViewBinder() = viewBinder

    open fun isFullScreen(): Boolean {
        return false
    }

    open fun isNoTitle(): Boolean {
        return false
    }

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (isFullScreen()) {
            setStyle(STYLE_NORMAL, R.style.Fullscreen)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        if (isNoTitle()) {
            dialog.window?.apply {
                requestFeature(Window.FEATURE_NO_TITLE)
                setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
            }
        }
        return dialog
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
