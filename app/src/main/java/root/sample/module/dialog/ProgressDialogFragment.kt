package root.sample.module.dialog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import root.sample.R
import root.sample.base.BaseDialogFragment
import root.sample.databinding.DialogFragmentProgressBinding

/**
 * A simple [Fragment] subclass.
 * Use the [ProgressDialogFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProgressDialogFragment : BaseDialogFragment<DialogFragmentProgressBinding>() {

    private var mFragmentManager: FragmentManager? = null
    private var isShowing = false

    override fun getLayoutId() = R.layout.dialog_fragment_progress

    override fun isNoTitle(): Boolean = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        isCancelable = false
    }

    fun show(fragmentManager: FragmentManager) {
        this.mFragmentManager = fragmentManager

        if (!(this.dialog?.isShowing == true && !this.isRemoving)) {
            if (!isShowing) {
                show(fragmentManager, TAG)
                isShowing = true
            }
        }
    }

    fun hide() {
        if (mFragmentManager?.findFragmentByTag(TAG) != null && this.dialog?.isShowing == true) {
            dismiss()
            isShowing = false
        }
    }

    companion object {
        const val TAG = "progress_dialog"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment ProgressDialogFragment.
         */
        @JvmStatic
        fun newInstance() = ProgressDialogFragment()
    }
}