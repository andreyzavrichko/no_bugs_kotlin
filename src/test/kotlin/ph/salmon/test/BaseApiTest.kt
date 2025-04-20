package ph.salmon.test

import io.qameta.allure.restassured.AllureRestAssured
import io.restassured.RestAssured
import io.restassured.filter.log.RequestLoggingFilter
import io.restassured.filter.log.ResponseLoggingFilter
import org.assertj.core.api.SoftAssertions
import org.junit.jupiter.api.*
import java.util.concurrent.TimeUnit

@Timeout(value = 20, unit = TimeUnit.SECONDS)
open class BaseApiTest {

    companion object {
        const val BASE_URL = "https://jsonplaceholder.typicode.com"

        @JvmStatic
        @BeforeAll
        fun setupRestAssured() {
            RestAssured.baseURI = BASE_URL
            RestAssured.filters(
                RequestLoggingFilter(), ResponseLoggingFilter(),
                AllureRestAssured()
            )
        }
    }

    protected lateinit var softly: SoftAssertions

    @BeforeEach
    fun setupSoftAssertions() {
        softly = SoftAssertions()
    }

    @AfterEach
    fun assertSoftAssertions() {
        softly.assertAll()
    }
}
