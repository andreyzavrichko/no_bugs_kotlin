package ph.salmon.test.config

import java.util.*

object TestConfig {
    private val properties: Properties = Properties()
    private val environment: String

    init {
        val stream = this::class.java.classLoader.getResourceAsStream("test-config.properties")
            ?: throw IllegalStateException("test-config.properties not found in resources")
        properties.load(stream)

        environment = System.getProperty("env")
            ?: System.getenv("ENV")
                    ?: properties.getProperty("env")
                    ?: "local"
    }

    val baseUrl: String
        get() = properties.getProperty("$environment.baseUrl")
            ?: throw IllegalStateException("Base URL for '$environment' not found in config")
}
