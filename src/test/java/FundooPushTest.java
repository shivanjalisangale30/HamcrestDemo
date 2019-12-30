import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

public class FundooPushTest {

    @Test
    public void givenCreatingWithNewUser_OnPostForResgistration_ShouldReturnUserGetAdded() {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .body("{\"email\" : \"jyotiYadev@gmail.com\", \"password\" : \"123456\"}")
                .post("https://fundoopush-backend-dev.bridgelabz.com/registration");
        int statusCode = response.getStatusCode();
        Assert.assertEquals(201, statusCode);
    }

    @Test
    public void givenCreatingUserWithAlreadyExist_OnPostForResgistration_ShouldReturnStatusCode() {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .body("{\"email\" : \"abc@gmail.com\", \"password\" : \"123456\"}")
                .post("https://fundoopush-backend-dev.bridgelabz.com/registration");
        int statusCode = response.getStatusCode();
        Assert.assertEquals(409, statusCode);
    }

    @Test
    public void givenCreatingUserWithNullEmailId_OnPostForResgistration_ShouldReturnStatusCode() {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .body("{\"email\" : null, \"password\" : \"123456\"}")
                .post("https://fundoopush-backend-dev.bridgelabz.com/registration");
        int statusCode = response.getStatusCode();
        Assert.assertEquals(400, statusCode);
    }

    @Test
    public void givenCreatingUserWithNullValues_OnPostForResgistration_ShouldReturnStatusCode() {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .body("{\"email\" : null, \"password\" : null }")
                .post("https://fundoopush-backend-dev.bridgelabz.com/registration");
        int statusCode = response.getStatusCode();
        Assert.assertEquals(400, statusCode);
    }

    @Test
    public void givenUserForLogin_OnPostForLogin_ShouldReturnCorrectCode() {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .body("{\"email\" : \"shivanjalisangale@gmail.com\", \"password\" : \"123456\"}")
                .post("https://fundoopush-backend-dev.bridgelabz.com/login");
        int statusCode = response.getStatusCode();
        Assert.assertEquals(200, statusCode);
    }

    @Test
    public void givenUserForLoginWithNoEmailId_OnPostForLogin_ShouldReturnCorrectCode() {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .body("{\"email\" : null, \"password\" : \"12346\"}")
                .post("https://fundoopush-backend-dev.bridgelabz.com/login");
        int statusCode = response.getStatusCode();
        Assert.assertEquals(400, statusCode);
    }

    @Test
    public void givenUserForLoginWithNullValues_OnPostForLogin_ShouldReturnCorrectCode() {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .body("{\"email\" : null, \"password\" : null}")
                .post("https://fundoopush-backend-dev.bridgelabz.com/login");
        int statusCode = response.getStatusCode();
        Assert.assertEquals(400, statusCode);
    }

    @Test
    public void givenUserForLoginWithNotRegisterdUser_OnPostForLogin_ShouldReturnCorrectCode() {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .body("{\"email\" : \"sunil@gmail.com\", \"password\" : \"123456\"}")
                .post("https://fundoopush-backend-dev.bridgelabz.com/login");
        int statusCode = response.getStatusCode();
        Assert.assertEquals(404, statusCode);
    }

    @Test
    public void givenUserForLogoutWithProperToken_OnPostForLogout_ShouldReturnCorrectStatusCode() {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .header("token","eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjp7Il9pZCI6IjVlMDljYzU0NGQyMjY3MDAzMjUzMGY4ZiJ9LCJpYXQiOjE1Nzc3MDU0NzMsImV4cCI6MTU3Nzc5MTg3M30." +
                        "d4jbc8dWOk-3OZtYFUM9ncqS0hvL6RK5pCe7lfhCkuo")
                .post("https://fundoopush-backend-dev.bridgelabz.com/logout");
        int statusCode = response.getStatusCode();
        Assert.assertEquals(200, statusCode);
    }

    @Test
    public void givenRedirectOperation_OnPostRedirect_ShouldReturnCorrectStatusCode() {
        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .when()
                .header("token","eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9." +
                        "eyJkYXRhIjp7Il9pZCI6IjVlMDljYzU0NGQyMjY3MDAzMjUzMGY4ZiJ9LCJpYXQiOjE1Nzc3MDY2ODksImV4cCI6MTU3Nzc5MzA4OX0.c8pMaU9RPWv7C3Xlf3k5OVOYayGAYAUv5sDFcTnnQgI")
                .formParam("image","/home/admin1/Pictures")
                .formParam("title","Butterfly")
                .formParam("description","ButterflyPicture")
                .formParam("redirect_link","www.google.com")
                .post("https://fundoopush-backend-dev.bridgelabz.com/redirects");
        int statusCode = response.getStatusCode();
        Assert.assertEquals(200, statusCode);
    }

}
