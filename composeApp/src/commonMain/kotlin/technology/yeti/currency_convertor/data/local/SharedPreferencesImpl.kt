package technology.yeti.currency_convertor.data.local

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.FlowSettings
import com.russhwolf.settings.coroutines.toFlowSettings
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import technology.yeti.currency_convertor.domain.SharedPreferencesRepo

class SharedPreferencesImpl(private val settings: Settings): SharedPreferencesRepo {
    companion object{
        const val KeyTimeStamp = "whenLastUpdated"
    }

    private val settingsProceed: FlowSettings = (settings as ObservableSettings).toFlowSettings()
    @OptIn(ExperimentalSettingsApi::class)
    override suspend fun storeWhenLastUpdated(lastUpdated: String) {
       settingsProceed.putLong(
           key = KeyTimeStamp,
           value = Instant.parse(lastUpdated).toEpochMilliseconds()
       )
    }

    @OptIn(ExperimentalSettingsApi::class)
    override suspend fun isFetchedDataFresh(currentTimeStamp: Long): Boolean {
        val storedTimeStamp = settingsProceed.getLong(
            key = KeyTimeStamp,
            defaultValue = 0L
        )
        return if (storedTimeStamp != 0L) {
            val current = Instant. fromEpochMilliseconds (currentTimeStamp)
            val saved = Instant. fromEpochMilliseconds (storedTimeStamp)
            val currentDateTime = current. toLocalDateTime(TimeZone.currentSystemDefault())
            val savedDateTime = saved. toLocalDateTime(TimeZone.currentSystemDefault())
            val differenceInDays = currentDateTime.date.dayOfYear - savedDateTime.date.dayOfYear

                    differenceInDays < 1
        }
        else false
    }
}