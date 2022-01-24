package root.sample.utils

import android.content.Context
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.chip.Chip
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import root.sample.network.response.ApiResult
import root.sample.network.response.BaseApiResponse
import root.sample.network.response.BaseErrorResponse
import java.io.File
import java.io.IOException
import java.net.NoRouteToHostException
import java.net.SocketTimeoutException
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

fun <T> LiveData<T>.reObserve(owner: LifecycleOwner, observer: Observer<T>) {
    removeObserver(observer)
    observe(owner, observer)
}

fun Throwable.getError(): ApiResult.Error {
    return when (this) {
        is SocketTimeoutException -> ApiResult.Error(Throwable(BaseApiResponse.GENERAL_ERROR))
        is NoRouteToHostException -> ApiResult.Error(Throwable(BaseApiResponse.GENERAL_ERROR))
        is IOException -> ApiResult.Error(Throwable(BaseApiResponse.NETWORK_ERROR))
        is HttpException -> {
            return try {
                val response = Gson().fromJson(
                    this.response()?.errorBody()?.string().orEmpty(),
                    BaseErrorResponse::class.java)
                if (response.message.isEmpty()) {
                    ApiResult.Error(Throwable(BaseApiResponse.GENERAL_ERROR))
                } else {
                    ApiResult.Error(Throwable(response.message))
                }
            } catch (e: Exception) {
                ApiResult.Error(Throwable(BaseApiResponse.GENERAL_ERROR))
            }
        }

        else -> ApiResult.Error(Throwable(BaseApiResponse.GENERAL_ERROR))
    }
}

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(editable: Editable?) {
            removeTextChangedListener(this)
            afterTextChanged.invoke(editable.toString())
            addTextChangedListener(this)
        }
    })
}

fun View.visible() {
    if (this.visibility == View.GONE || this.visibility == View.INVISIBLE) this.visibility =
        View.VISIBLE
}

fun View.gone() {
    if (this.visibility == View.VISIBLE) this.visibility = View.GONE
}

fun View.invisible() {
    if (this.visibility == View.VISIBLE) this.visibility = View.INVISIBLE
}

fun String.currencyToRupiahFormat(price: Double?): String {
    if (price == null)
        return "Rp "

    val localeID = Locale("id", "ID")
    val formatRupiah = NumberFormat.getCurrencyInstance(localeID)
    val tempPrice = formatRupiah.format(price).replace(",00", "")
    return tempPrice.replace("Rp", "Rp ")
}

interface DropDownCallback {
    fun onSelectedTitle(title: String, id: String?, dropDownType: String, subtitle : String? = null)
}

fun RecyclerView.setAppBarElevationListener(appBar: AppBarLayout?) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            appBar?.let {
                if (canScrollVertically(-1)) ViewCompat.setElevation(it, 6f)
                else ViewCompat.setElevation(it, 0f)
            }
        }
    })
}

fun AppCompatActivity.prepareFilePart(file: File): MultipartBody.Part {
    return MultipartBody.Part.createFormData(
        "image",
        file.name,
        file.inputStream()
            .readBytes()
            .toRequestBody("image/jpg".toMediaType())
    )
}

fun Fragment.prepareFilePart(file: File): MultipartBody.Part {
    return MultipartBody.Part.createFormData(
        "image",
        file.name,
        file.inputStream()
            .readBytes()
            .toRequestBody("image/jpg".toMediaType())
    )
}

fun AppCompatActivity.convertDpToPx(context: Context, valueInDp: Float): Float {
    val metrics = context.resources.displayMetrics
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics)
}

fun Fragment.convertDpToPx(context: Context, valueInDp: Float): Float {
    val metrics = context.resources.displayMetrics
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics)
}

fun String.convertStringToDateTime(context: Context): String {
    try {
        val dateTimeFormatter = SimpleDateFormat("dd MMM yyyy . HH:mm", getCurrentLocale(context))
        val originalFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", getCurrentLocale(context))
        val date = originalFormat.parse(this)
        return dateTimeFormatter.format(date)
    } catch (e: Exception) {
    }
    return ""
}

private fun getCurrentLocale(context: Context?): Locale {
    context?.let {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.resources.configuration.locales.get(0)
        } else {
            context.resources.configuration.locale
        }
    }
    return Locale.getDefault()
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun AppCompatActivity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

