package technology.yeti.currency_convertor.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import cafe.adriel.voyager.core.screen.Screen
import technology.yeti.currency_convertor.data.remote.thirdpartyapi.ForeignCurrencyExchangeApiServiceImpl

class HomePage: Screen{
    @Composable
    override fun Content() {
        LaunchedEffect(Unit){
            ForeignCurrencyExchangeApiServiceImpl().retrieveLatestForeignCurrencyExchangeRates()
        }
    }

}