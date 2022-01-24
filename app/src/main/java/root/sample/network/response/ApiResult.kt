package root.sample.network.response

sealed class ApiResult<out T : Any> {

    class Success<out T : Any>(val data: T) : ApiResult<T>()

    class Error(val exception: Throwable) : ApiResult<Nothing>() {
        constructor(message: String?) : this(Throwable(message)) {
            this.message = message ?: BaseApiResponse.GENERAL_ERROR
        }
        var message: String = ""
            private set
    }

}
