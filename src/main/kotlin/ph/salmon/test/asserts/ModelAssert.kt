package ph.salmon.test.asserts

import org.assertj.core.api.AbstractAssert
import org.assertj.core.api.Assertions.assertThat
import kotlin.reflect.full.memberProperties

class ModelAssert<T : Any>(
    actual: T?,
    private val clazz: Class<T>
) : AbstractAssert<ModelAssert<T>, T?>(actual, ModelAssert::class.java) {

    fun hasSameFieldsAs(expected: T): ModelAssert<T> {
        isNotNull

        val kClass = clazz.kotlin
        val fields = kClass.memberProperties

        for (property in fields) {
            val expectedValue = property.get(expected)
            val actualValue = property.get(actual!!)

            assertThat(actualValue)
                .withFailMessage(
                    "Expected %s to be <%s> but was <%s>",
                    property.name, expectedValue, actualValue
                )
                .isEqualTo(expectedValue)
        }

        return this
    }

    companion object {
        fun <T : Any> assertThatModel(actual: T?, clazz: Class<T>): ModelAssert<T> {
            return ModelAssert(actual, clazz)
        }
    }
}
