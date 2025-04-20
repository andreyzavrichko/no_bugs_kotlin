package ph.salmon.test.spec

import io.restassured.builder.RequestSpecBuilder
import io.restassured.http.ContentType
import io.restassured.specification.RequestSpecification


fun spec(): RequestSpecification {
    val reqSpecBuilder = RequestSpecBuilder()
    reqSpecBuilder.setContentType(ContentType.JSON)
    return reqSpecBuilder.build()
}
