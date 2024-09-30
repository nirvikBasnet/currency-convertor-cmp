package technology.yeti.currency_convertor.domain.model

sealed class RequestCondition<out T> {
    data object IdleCondition: RequestCondition<Nothing>()
    data object LoadingCondition: RequestCondition<Nothing>()
    data class SuccessCondition<out T>(val data: T): RequestCondition<T>()
    data class ErrorCondition(val message: String): RequestCondition<Nothing>()

    fun isLoading(): Boolean = this is LoadingCondition
    fun isError(): Boolean = this is ErrorCondition
    fun isSuccess(): Boolean = this is SuccessCondition

    fun getSuccessInfo() = (this as SuccessCondition).data
    fun getErrorMessageInfo(): String = (this as ErrorCondition).message
}