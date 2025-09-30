package ph.salmon.test

import io.qameta.allure.AllureId
import io.qameta.allure.Issue
import org.junit.jupiter.api.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import ph.salmon.test.asserts.ModelAssert.Companion.assertThatModel
import ph.salmon.test.generators.TestDataGenerator.generate
import ph.salmon.test.models.Post
import ph.salmon.test.requests.PostService
import ph.salmon.test.spec.spec
import java.lang.Math.random
import java.util.stream.Stream
import kotlin.test.assertEquals

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
    @DisplayName("Пользователь получает корректный пост по id")
    fun `should return correct post by id`() {
        val actualResponse = postService.read(expectedPost.id)

        assertThatModel(actualResponse, Post::class.java).hasSameFieldsAs(expectedPost)
    }

    @Test
    @AllureId("some generated id")
    @Issue("some issue/task id")
    @DisplayName("Пользователь может успешно создать новый пост")
    fun `should create a new post successfully`() {
        val actualResponse = postService.create(expectedPost)
        assertThatModel(actualResponse, Post::class.java).hasSameFieldsAs(expectedPost)
    }

    @Test
    @AllureId("some generated id")
    @Issue("some issue/task id")
    @DisplayName("Пользователь может успешно обновить пост")
    fun `should update a post successfully`() {
        val actualResponse = postService.update(expectedPost.id, expectedPost)
        assertThatModel(actualResponse, Post::class.java).hasSameFieldsAs(expectedPost)
    }


    @Test
    @AllureId("some generated id")
    @Issue("some issue/task id")
    @DisplayName("Пользователь может успешно удалить пост")
    fun `should update a delete successfully`() {
        postService.delete(expectedPost.id)
    }

    @Test
    @AllureId("some generated id")
    @Issue("some issue/task id")
    @DisplayName("Пользователь может успешно удалить пост")
    fun `should update a delete successfully unreal id`() {
        postService.delete(random().toInt())
    }

    @Test
    @AllureId("some generated id")
    @Issue("some issue/task id")
    @DisplayName("Пользователь может успешно создать пост без title")
    fun `should create correct post without title`() {
        val postWithEmptyTitle = generate(Post::class.java, mapOf("title" to ""))
        val createdPost = postService.create(postWithEmptyTitle)

        assertEquals("", createdPost.title)
    }

    @Test
    @AllureId("some generated id")
    @Issue("some issue/task id")
    @DisplayName("Пользователь может успешно создать пост без body")
    fun `should create correct post without body`() {
        val postWithNullBody = generate(Post::class.java, mapOf("body" to ""))
        val createdPost = postService.create(postWithNullBody)
        assertEquals("", createdPost.body)
    }

    @Test
    @AllureId("some generated id")
    @Issue("some issue/task id")
    @DisplayName("Пользователь получает 404 при запросе поста по несуществующему id")
    fun `should return 404 when post not found`() {
        postService.readNotFound(Int.MAX_VALUE)
    }

    @ParameterizedTest
    @MethodSource("titleLengthProvider")
    @AllureId("title-length-id")
    @Issue("some-issue")
    @DisplayName("Пользователь может создать пост с допустимой длиной title")
    fun `should create post with valid title length`(title: String) {
        val post = generate(Post::class.java, mapOf("title" to title))
        val createdPost = postService.create(post)

        val expectedPost = post.copy(id = 101)
        assertThatModel(createdPost, Post::class.java).hasSameFieldsAs(expectedPost)
    }

    companion object {
        @JvmStatic
        fun titleLengthProvider(): Stream<String> = Stream.of(
            "T",
            "T".repeat(255)
        )
    }

}
