package ph.salmon.test.requests

import io.qameta.allure.Step
import io.restassured.RestAssured.given
import io.restassured.specification.RequestSpecification
import org.apache.http.HttpStatus
import ph.salmon.test.models.Post

class PostService(val reqSpec: RequestSpecification) : CrudInterface<Post> {
    private val createdPosts: MutableList<Int> = mutableListOf()
    val allCreatedPosts: List<Int> get() = createdPosts

    @Step("Create post")
    override fun create(item: Post): Post {
        val createdPost = given()
            .spec(reqSpec)
            .body(item)
            .post(POSTS_URL)
            .then()
            .assertThat()
            .statusCode(HttpStatus.SC_CREATED)
            .extract().body()
            .`as`(Post::class.java)
        createdPosts.add(createdPost.id)
        return createdPost
    }

    @Step("Get post")
    override fun read(id: Int): Post {
        return given()
            .spec(reqSpec)
            .get("$POSTS_URL/$id")
            .then()
            .extract()
            .body()
            .`as`(Post::class.java)
    }

    @Step("Delete post")
    override fun delete(id: Int): String {
        return given()
            .spec(reqSpec)
            .delete("$POSTS_URL/$id")
            .then()
            .assertThat()
            .statusCode(HttpStatus.SC_OK)
            .extract().body()
            .asString()
    }

    @Step("Update post")
    override fun update(id: Int, item: Post): Post? {
        return given()
            .spec(reqSpec)
            .body(item)
            .put("$POSTS_URL/$id")
            .then()
            .assertThat()
            .statusCode(HttpStatus.SC_OK)
            .extract().body()
            .`as`(Post::class.java)
    }

    companion object {
        const val POSTS_URL = "/posts"
    }
}