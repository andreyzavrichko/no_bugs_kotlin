package ph.salmon.test

import io.qameta.allure.restassured.AllureRestAssured
import io.restassured.RestAssured
import io.restassured.filter.log.RequestLoggingFilter
import io.restassured.filter.log.ResponseLoggingFilter
import org.assertj.core.api.SoftAssertions
import org.junit.jupiter.api.*
import ph.salmon.test.config.TestConfig
import java.util.concurrent.TimeUnit

@Timeout(value = 20, unit = TimeUnit.SECONDS)
open class BaseApiTest {

    companion object {

        @JvmStatic
        @BeforeAll
        fun setupRestAssured() {
            RestAssured.baseURI = TestConfig.baseUrl
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
