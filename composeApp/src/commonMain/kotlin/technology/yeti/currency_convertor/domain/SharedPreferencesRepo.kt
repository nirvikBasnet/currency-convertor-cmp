package technology.yeti.currency_convertor.domain

interface SharedPreferencesRepo {
    suspend fun storeWhenLastUpdated(lastUpdated: String)
    suspend fun isFetchedDataFresh(currentTimeStamp: Long): Boolean
}