package technology.yeti.currency_convertor.domain.model

data class ApiResponse(
    val meta: MetaDataFromApi,
    val data: Map<String, CurrencyObject>
)

data class MetaDataFromApi(
    val last_updated_at: String
)

data class CurrencyObject(
    val code: String,
    val value: Double
)