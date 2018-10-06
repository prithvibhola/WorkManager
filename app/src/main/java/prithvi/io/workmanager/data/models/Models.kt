package prithvi.io.workmanager.data.models

data class Response<out T>(
        val status: Status,
        val data: T? = null,
        val error: Throwable? = null
) {
    enum class Status { LOADING, SUCCESS, ERROR }

    companion object {
        fun <T> loading(): Response<T> = Response(Status.LOADING, null, null)
        fun <T> success(data: T? = null): Response<T> = Response(Status.SUCCESS, data, null)
        fun <T> error(error: Throwable): Response<T> = Response(Status.ERROR, null, error)
    }
}