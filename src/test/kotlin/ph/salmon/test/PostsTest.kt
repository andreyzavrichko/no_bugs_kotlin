package ph.salmon.test

import io.qameta.allure.AllureId
import io.qameta.allure.Issue
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import ph.salmon.test.generators.TestDataGenerator.generate
import ph.salmon.test.models.Post
import ph.salmon.test.requests.PostService
import ph.salmon.test.spec.spec

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PostsTest : BaseApiTest() {
    private lateinit var postService: PostService
    private lateinit var expectedPost: Post


    @BeforeEach
    fun setupTestData() {
        postService = PostService(spec())
        expectedPost = generate(Post::class.java)
    }

    @AfterEach
    fun cleanTestData() {
        postService.allCreatedPosts.forEach { postService.delete(it) }
    }

    @Test
    @AllureId("some generated id")
    @Issue("some issue/task id")
    @DisplayName("Get a post")
    fun `get a post`() {
        val actualResponse = postService.read(expectedPost.id)

        softly.assertThat(actualResponse.id).isNotNull()
        softly.assertThat(actualResponse.title).isEqualTo(expectedPost.title)
        softly.assertThat(actualResponse.body).isEqualTo(expectedPost.body)

    }

    @Test
    @AllureId("some generated id")
    @Issue("some issue/task id")
    @DisplayName("Create a post")
    fun `create a post`() {
        val actualResponse = postService.create(expectedPost)
        softly.assertThat(actualResponse.userId).isEqualTo(expectedPost.userId)
        softly.assertThat(actualResponse.title).isEqualTo(expectedPost.title)
        softly.assertThat(actualResponse.body).isEqualTo(expectedPost.body)
    }

    @Test
    @AllureId("some generated id")
    @Issue("some issue/task id")
    @DisplayName("Update a post")
    fun `update a post`() {
        val actualResponse = postService.update(expectedPost.id, expectedPost)
        softly.assertThat(actualResponse).isEqualTo(expectedPost)
    }


    @Test
    @AllureId("some generated id")
    @Issue("some issue/task id")
    @DisplayName("Delete a post")
    fun `delete a post`() {
        postService.delete(expectedPost.id)
    }


}
