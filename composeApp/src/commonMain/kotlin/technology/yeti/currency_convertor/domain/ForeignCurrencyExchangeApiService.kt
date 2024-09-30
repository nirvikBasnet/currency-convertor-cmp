package technology.yeti.currency_convertor.domain
import  technology.yeti.currency_convertor.domain.model.RequestCondition
import  technology.yeti.currency_convertor.domain.model.CurrencyObject
interface ForeignCurrencyExchangeApiService {
    suspend fun retrieveLatestForeignCurrencyExchangeRates(): RequestCondition<List<CurrencyObject>>
}