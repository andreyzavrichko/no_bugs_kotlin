package ph.salmon.test.requests

import io.qameta.allure.Step
import io.restassured.RestAssured.given
import io.restassured.response.ValidatableResponse
import io.restassured.specification.RequestSpecification
import org.apache.http.HttpStatus
import org.apache.http.HttpStatus.SC_NOT_FOUND
import ph.salmon.test.config.ApiEndpoints
import ph.salmon.test.models.Post

class PostService(val reqSpec: RequestSpecification) : CrudInterface<Post> {
    private val createdPosts: MutableList<Int> = mutableListOf()
    val allCreatedPosts: List<Int> get() = createdPosts

    @Step("Create post")
    override fun create(item: Post): Post {
        val createdPost = given()
            .spec(reqSpec)
            .body(item)
            .post(ApiEndpoints.POSTS)
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
            .get("${ApiEndpoints.POSTS}/$id")
            .then()
            .extract()
            .body()
            .`as`(Post::class.java)
    }

    @Step("Get post not found")
    fun readNotFound(id: Int): ValidatableResponse? {
        return given()
            .spec(reqSpec)
            .get("${ApiEndpoints.POSTS}/$id")
            .then()
            .assertThat()
            .statusCode(SC_NOT_FOUND)

    }

    @Step("Delete post")
    override fun delete(id: Int): String {
        return given()
            .spec(reqSpec)
            .delete("${ApiEndpoints.POSTS}/$id")
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
            .put("${ApiEndpoints.POSTS}/$id")
            .then()
            .assertThat()
            .statusCode(HttpStatus.SC_OK)
            .extract().body()
            .`as`(Post::class.java)
    }
}
