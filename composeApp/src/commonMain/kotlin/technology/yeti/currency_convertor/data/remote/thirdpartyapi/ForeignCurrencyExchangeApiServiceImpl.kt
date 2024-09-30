package technology.yeti.currency_convertor.data.remote.thirdpartyapi


import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import technology.yeti.currency_convertor.domain.ForeignCurrencyExchangeApiService
import technology.yeti.currency_convertor.domain.model.ApiResponse
import technology.yeti.currency_convertor.domain.model.CurrencyObject
import technology.yeti.currency_convertor.domain.model.RequestCondition

class ForeignCurrencyExchangeApiServiceImpl : ForeignCurrencyExchangeApiService {

    companion object
    {
        const val EndPointAPI = "https://api.currencyapi.com/v3/latest"
        const val API_KEY_CurrencyExchange = "cur_live_yriGlDrIImrQv3nSvN0g6p8AG05K7JrEyXQar3aD"
    }

    //ktor
    private val httpClientInstance = HttpClient{
        install(ContentNegotiation){
            json(Json{
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
        install(HttpTimeout){
            requestTimeoutMillis = 16000 //16 sec
        }
        install(DefaultRequest){
            headers{
                append("apiKey", API_KEY_CurrencyExchange)
            }
        }
    }

    /**
     * Retrieves the latest foreign currency exchange rates from the API.
     * The function performs an HTTP GET request using the provided httpClientInstance
     * to fetch exchange rates from a remote endpoint. It returns a `RequestCondition`
     * object that reflects the current state of the request, such as success or failure.
     *
     * @return RequestCondition<List<CurrencyObject>> - A sealed class representing the result
     * of the API call. It can be either a success condition containing the list of currency data,
     * or an error condition with an error message.
     *
     * @author Nirvik
     */

    override suspend fun retrieveLatestForeignCurrencyExchangeRates(): RequestCondition<List<CurrencyObject>> {
        return try{
            val responseFromApi = httpClientInstance.get(EndPointAPI)
            if(responseFromApi.status.value == 200){
                println("Response from API="+ responseFromApi.body<String>())
                val responseDataFromApi = Json.decodeFromString<ApiResponse>(responseFromApi.body())
                RequestCondition.SuccessCondition(data = responseDataFromApi.data.values.toList())
            }else{
                RequestCondition.ErrorCondition(message = "HTTP Error: ${responseFromApi.status}")
            }
        }catch (error: Exception){
            RequestCondition.ErrorCondition(message = error.message.toString())
        }
    }
}