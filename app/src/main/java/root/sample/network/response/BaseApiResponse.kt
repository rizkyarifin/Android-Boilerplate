package root.sample.network.response

import com.google.gson.annotations.SerializedName

open class BaseApiResponse {

    companion object {
        const val GENERAL_ERROR = "General Error"
        const val NETWORK_ERROR = "Network Error"
        const val SERVER_ERROR = "Server Error"
        const val ERROR_TOKEN = ""
        const val SUCCESS = "SUCCESS"
    }

    @SerializedName("status")
    var status: String? = null

    @SerializedName("message")
    var message: String? = null

    open fun <T : Any> getResult(data: T): ApiResult<T> {
        return if(status == "ok") {
            ApiResult.Success(data)
        } else {
            ApiResult.Error(Throwable("error"))
        }

        /*val errorResult = getErrorResult()
        return if (errorResult.isBlank()) ApiResult.Success(data)
        else ApiResult.Error(Throwable(errorResult))*/
    }

    protected open fun getErrorResult(): String {
        val responseCode: String = status ?: ""
        val msg: String = message ?: ""

        if (msg == "Success") return ""

        return if (!responseCode.equals(SUCCESS, true)) {
            message ?: GENERAL_ERROR
        } else {
            ""
        }

        //return if (!responseCode.equals(SUCCESS, true)) message ?: GENERAL_ERROR else ""
    }

}
