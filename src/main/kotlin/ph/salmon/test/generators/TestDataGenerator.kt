package ph.salmon.test.generators

import kotlin.random.Random
import kotlin.reflect.full.primaryConstructor

object TestDataGenerator {
    fun <T : Any> generate(
        clazz: Class<T>,
        overrides: Map<String, Any?> = emptyMap()
    ): T {
        val kClass = clazz.kotlin
        val constructor = kClass.primaryConstructor ?: throw Exception("Cannot generate data for class: ${clazz.name}")

        val args = constructor.parameters.map { param ->
            val overrideValue = overrides[param.name]
            when {
                overrideValue != null || overrides.containsKey(param.name) -> overrideValue
                param.type.classifier == Int::class -> Random.nextInt(1, 100)
                param.type.classifier == String::class -> generateRandomString(10)
                param.type.isMarkedNullable -> null
                else -> throw IllegalArgumentException("Unsupported type: ${param.type}")
            }
        }

        return constructor.call(*args.toTypedArray())
    }

    private fun generateRandomString(length: Int): String {
        val allowedChars = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }
}
