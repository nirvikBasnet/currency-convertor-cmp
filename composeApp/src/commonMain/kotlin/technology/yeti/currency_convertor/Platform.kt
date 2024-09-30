package technology.yeti.currency_convertor

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform