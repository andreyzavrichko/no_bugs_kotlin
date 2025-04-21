package ph.salmon.test.asserts

import org.assertj.core.api.AbstractAssert
import org.assertj.core.api.Assertions.assertThat
import ph.salmon.test.models.Post

class PostAssert(actual: Post?) : AbstractAssert<PostAssert, Post?>(actual, PostAssert::class.java) {

    fun hasSameFieldsAs(expected: Post): PostAssert {
        isNotNull

        assertThat(actual!!.id)
            .withFailMessage("Expected id to be <%s> but was <%s>", expected.id, actual!!.id)
            .isEqualTo(expected.id)

        assertThat(actual!!.title)
            .withFailMessage("Expected title to be <%s> but was <%s>", expected.title, actual!!.title)
            .isEqualTo(expected.title)

        assertThat(actual!!.body)
            .withFailMessage("Expected body to be <%s> but was <%s>", expected.body, actual!!.body)
            .isEqualTo(expected.body)

        assertThat(actual!!.userId)
            .withFailMessage("Expected userId to be <%s> but was <%s>", expected.userId, actual!!.userId)
            .isEqualTo(expected.userId)

        return this
    }

    companion object {
        fun assertThatPost(actual: Post?): PostAssert {
            return PostAssert(actual)
        }
    }
}
