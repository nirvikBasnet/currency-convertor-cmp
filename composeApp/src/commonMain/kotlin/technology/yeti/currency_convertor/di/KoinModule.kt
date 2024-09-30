package technology.yeti.currency_convertor.di

import com.russhwolf.settings.Settings
import org.koin.core.context.startKoin
import org.koin.dsl.module
import technology.yeti.currency_convertor.data.local.SharedPreferencesImpl
import technology.yeti.currency_convertor.data.remote.thirdpartyapi.ForeignCurrencyExchangeApiServiceImpl
import technology.yeti.currency_convertor.domain.ForeignCurrencyExchangeApiService
import technology.yeti.currency_convertor.domain.SharedPreferencesRepo

val moduleApplication = module {
    single {
        Settings()
    }
    single<SharedPreferencesRepo>{
        SharedPreferencesImpl(settings = get())
    }
    single<ForeignCurrencyExchangeApiService> { ForeignCurrencyExchangeApiServiceImpl(sharedPreferencesRepo = get()) }
}

fun initKoin(){
    startKoin {
        module { moduleApplication }
    }
}