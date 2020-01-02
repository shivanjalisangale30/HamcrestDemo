import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

public class FundooPushTest {


    private String tokenValue;

    @Before
    public void setUp() throws Exception {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .body("{\"email\" : \"shivanjalisangale@gmail.com\", \"password\" : \"123456\"}")
                .post("https://fundoopush-backend-dev.bridgelabz.com/login");
        String resAsString = response.asString();
        JsonPath jsonPath = new JsonPath(resAsString);
        tokenValue = jsonPath.getString("token");
    }

    @Test
    public void givenCreatingWithNewUser_OnPostForResgistration_ShouldReturnUserGetAdded() throws ParseException {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .body("{\"email\" : \"jyotiYadev@gmail.com\", \"password\" : \"123456\"}")
                .post("https://fundoopush-backend-dev.bridgelabz.com/registration");
        ResponseBody body = response.getBody();
        int statusCode = response.getStatusCode();
        JSONObject object = (JSONObject) new JSONParser().parse(body.prettyPrint());
        String message = (String) object.get("message");

        Assert.assertEquals("Logged in Successfully", message);
        Assert.assertEquals(201, statusCode);
    }

    @Test
    public void givenCreatingUserWithAlreadyExist_OnPostForResgistration_ShouldReturnStatusCode() throws ParseException {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .body("{\"email\" : \"abc@gmail.com\", \"password\" : \"123456\"}")
                .post("https://fundoopush-backend-dev.bridgelabz.com/registration");
        ResponseBody body = response.getBody();
        int statusCode = response.getStatusCode();
        JSONObject object = (JSONObject) new JSONParser().parse(body.prettyPrint());
        String message = (String) object.get("message");

        Assert.assertEquals("Email id already exists", message);
        Assert.assertEquals(409, statusCode);
    }

    @Test
    public void givenCreatingUserWithNullEmailId_OnPostForResgistration_ShouldReturnStatusCode() throws ParseException {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .body("{\"email\" : null, \"password\" : \"123456\"}")
                .post("https://fundoopush-backend-dev.bridgelabz.com/registration");
        ResponseBody body = response.getBody();
        int statusCode = response.getStatusCode();
        JSONObject object = (JSONObject) new JSONParser().parse(body.prettyPrint());
        String message = (String) object.get("message");

        Assert.assertEquals("email is required", message);
        Assert.assertEquals(400, statusCode);
    }

    @Test
    public void givenCreatingUserWithNullValues_OnPostForResgistration_ShouldReturnStatusCode() throws ParseException {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .body("{\"email\" : null, \"password\" : null }")
                .post("https://fundoopush-backend-dev.bridgelabz.com/registration");
        ResponseBody body = response.getBody();
        int statusCode = response.getStatusCode();
        JSONObject object = (JSONObject) new JSONParser().parse(body.prettyPrint());
        String message = (String) object.get("message");

        Assert.assertEquals("email is required", message);
        Assert.assertEquals(400, statusCode);
    }

    @Test
    public void givenUserForLogin_OnPostForLogin_ShouldReturnCorrectCode() throws ParseException {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .body("{\"email\" : \"shivanjalisangale@gmail.com\", \"password\" : \"123456\"}")
                .post("https://fundoopush-backend-dev.bridgelabz.com/login");
        ResponseBody body = response.getBody();
        int statusCode = response.getStatusCode();
        JSONObject object = (JSONObject) new JSONParser().parse(body.prettyPrint());
        String message = (String) object.get("message");

        Assert.assertEquals("Logged in Successfully", message);
        Assert.assertEquals(200, statusCode);
    }

    @Test
    public void givenUserForLoginWithNoEmailId_OnPostForLogin_ShouldReturnCorrectCode() throws ParseException {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .body("{\"email\" : null, \"password\" : \"12346\"}")
                .post("https://fundoopush-backend-dev.bridgelabz.com/login");
        ResponseBody body = response.getBody();
        int statusCode = response.getStatusCode();
        JSONObject object = (JSONObject) new JSONParser().parse(body.prettyPrint());
        String message = (String) object.get("message");

        Assert.assertEquals("email is required", message);
        Assert.assertEquals(400, statusCode);
    }

    @Test
    public void givenUserForLoginWithNullValues_OnPostForLogin_ShouldReturnCorrectCode() throws ParseException {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .body("{\"email\" : null, \"password\" : null}")
                .post("https://fundoopush-backend-dev.bridgelabz.com/login");
        ResponseBody body = response.getBody();
        int statusCode = response.getStatusCode();
        JSONObject object = (JSONObject) new JSONParser().parse(body.prettyPrint());
        String message = (String) object.get("message");

        Assert.assertEquals("email is required", message);
        Assert.assertEquals(400, statusCode);
    }

    @Test
    public void givenUserForLoginWithNotRegisterdUser_OnPostForLogin_ShouldReturnCorrectCode() throws ParseException {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .body("{\"email\" : \"sunil@gmail.com\", \"password\" : \"123456\"}")
                .post("https://fundoopush-backend-dev.bridgelabz.com/login");
        ResponseBody body = response.getBody();
        int statusCode = response.getStatusCode();
        JSONObject object = (JSONObject) new JSONParser().parse(body.prettyPrint());
        String message = (String) object.get("message");

        Assert.assertEquals("Not a registered user", message);
        Assert.assertEquals(404, statusCode);
    }

    @Test
    public void givenUserForLogoutWithProperToken_OnPostForLogout_ShouldReturnCorrectStatusCode() throws ParseException {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .header("token", tokenValue)
                .post("https://fundoopush-backend-dev.bridgelabz.com/logout");
        ResponseBody body = response.getBody();
        int statusCode = response.getStatusCode();
        JSONObject object = (JSONObject) new JSONParser().parse(body.prettyPrint());
        String message = (String) object.get("message");

        Assert.assertEquals("Logged out successfully from the system", message);
        Assert.assertEquals(200, statusCode);
    }

    @Test
    public void givenRedirectOperation_OnPostRedirect_ShouldReturnCorrectStatusCode() throws ParseException {
        File file = new File("/home/admin1/Pictures/Firefox_wallpaper.png");
        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .header("token", tokenValue)
                .formParam("title", "Butterfly")
                .multiPart("image", file)
                .formParam("description", "ButterflyPicture")
                .formParam("redirect_link", "www.google.com")
                .when()
                .log()
                .all()
                .post("https://fundoopush-backend-dev.bridgelabz.com/redirects");
        ResponseBody body = response.getBody();
        int statusCode = response.getStatusCode();
        JSONObject object = (JSONObject) new JSONParser().parse(body.prettyPrint());
        String message = (String) object.get("message");

        Assert.assertEquals("Redirect added Successfully", message);
        Assert.assertEquals(201, statusCode);
    }

    @Test
    public void givenRedirectOperation_OnPutRedirect_ShouldReturnCorrectStatusCode() throws ParseException {
        File file = new File("/home/admin1/Pictures/Firefox_wallpaper.png");
        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .header("token", tokenValue)
                .multiPart("_id", "5e0ae3b94d22670032531050")
                .multiPart("image", file)
                .formParam("title", "ButterflyBird")
                .formParam("description", "ButterflyPicture")
                .when()
                .log()
                .all()
                .put("https://fundoopush-backend-dev.bridgelabz.com/redirects");
        ResponseBody body = response.getBody();
        int statusCode = response.getStatusCode();
        JSONObject object = (JSONObject) new JSONParser().parse(body.prettyPrint());
        String message = (String) object.get("message");

        Assert.assertEquals("Redirect updated Successfully", message);
        Assert.assertEquals(200, statusCode);
    }

    @Test
    public void givenRedirectOperation_OnGetRedirect_ShouldReturnCorrectStatusCode() throws ParseException {
        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .header("token", tokenValue)
                .when()
                .get("https://fundoopush-backend-dev.bridgelabz.com/redirects");
        ResponseBody body = response.getBody();
        int statusCode = response.getStatusCode();
        JSONObject object = (JSONObject) new JSONParser().parse(body.prettyPrint());
        String message = (String) object.get("message");

        Assert.assertEquals("All Redirects retrieved Successfully", message);
        Assert.assertEquals(200, statusCode);
    }

    @Test
    public void givenRedirectOperation_OnRedirectDeleteId_ShouldReturnCorrectStatusCode() throws ParseException {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("token", tokenValue)
                .when()
                .body("{\"_id\": \"5e098a684d22670032530f2b\"}")
                .post("https://fundoopush-backend-dev.bridgelabz.com/redirects/delete");
        ResponseBody body = response.getBody();
        int statusCode = response.getStatusCode();
        JSONObject object = (JSONObject) new JSONParser().parse(body.prettyPrint());
        String message = (String) object.get("message");

        Assert.assertEquals("Redirect deleted Successfully", message);
        Assert.assertEquals(200, statusCode);
    }

    @Test
    public void givenRedirectOperation_OnRedirectBlRedirecyts_ShouldReturnCorrectStatusCode() throws ParseException {
        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .when()
                .get("https://fundoopush-backend-dev.bridgelabz.com/bl-redirects");
        ResponseBody body = response.getBody();
        int statusCode = response.getStatusCode();
        JSONObject object = (JSONObject) new JSONParser().parse(body.prettyPrint());
        String message = (String) object.get("message");

        Assert.assertEquals("All Redirects retrieved Successfully", message);
        Assert.assertEquals(200, statusCode);
    }

    @Test
    public void givenRedirectOperation_OnHashtagEdit_ShouldReturnCorrectStatusCode() throws ParseException {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("token", tokenValue)
                .when()
                .body("{\"redirect_id\": \"5cdfbb5e274bba374fe68edb\",\"hashtag\": \"#bridgelabz #solutions #mumbai #bangalore #fundoopush\"}")
                .post("https://fundoopush-backend-dev.bridgelabz.com/hashtag/edit");
        ResponseBody body = response.getBody();
        int statusCode = response.getStatusCode();
        JSONObject object = (JSONObject) new JSONParser().parse(body.prettyPrint());
        String message = (String) object.get("message");

        Assert.assertEquals("Hashtag edit done Successfully", message);
        Assert.assertEquals(200, statusCode);
    }

    @Test
    public void givenRedirectOperation_OnRedirectHashtag_ShouldReturnCorrectStatusCode() throws ParseException {
        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .header("token", tokenValue)
                .formParam("hashtagname", "#mumbai")
                .when()
                .get("https://fundoopush-backend-dev.bridgelabz.com/redirects/hashtag/%23mumbai");
        ResponseBody body = response.getBody();
        int statusCode = response.getStatusCode();
        JSONObject object = (JSONObject) new JSONParser().parse(body.prettyPrint());
        String message = (String) object.get("message");

        Assert.assertEquals("Hashtag sent Successfully", message);
        Assert.assertEquals(200, statusCode);
    }

    @Test
    public void givenRedirectOperation_OnRedirectWebScraping_ShouldReturnCorrectStatusCode() throws ParseException {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("token", tokenValue)
                .when()
                .body("{\"url\": \"https://www.deccanchronicle.com/technology/in-other-news/270319/companies-that-are-changing-the-way-education-is-being-delivered-to-st.html\"}")
                .post("https://fundoopush-backend-dev.bridgelabz.com/web-scraping");
        ResponseBody body = response.getBody();
        int statusCode = response.getStatusCode();
        JSONObject object = (JSONObject) new JSONParser().parse(body.prettyPrint());
        String message = (String) object.get("message");

        Assert.assertEquals("Successfully scrapped data", message);
        Assert.assertEquals(200, statusCode);
    }

    @Test
    public void givenRedirectOperation_OnRedirectSearchHashTag_ShouldReturnCorrectStatusCode() throws ParseException {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("token", tokenValue)
                .when()
                .body("{\"hashtag\": \"#bridgelabz\"}")
                .post("https://fundoopush-backend-dev.bridgelabz.com/search/hashtag");
        ResponseBody body = response.getBody();
        int statusCode = response.getStatusCode();
        JSONObject object = (JSONObject) new JSONParser().parse(body.prettyPrint());
        String message = (String) object.get("message");

        Assert.assertEquals("Successfully searched data", message);
        Assert.assertEquals(200, statusCode);
    }

    @Test
    public void givenUser_JobAdded_ShouldReturnCorrectStatusCode() throws ParseException {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("token", tokenValue)
                .body("{\"redirect_id\":\"5e0b4281fe754c00321c8631\",\"years_of_experience\":\"1\",\"salary\":\"3.6\",\"location\":\"Mumbai\",\"company_profile\":\"Ideation\"}")
                .when()
                .post("https://fundoopush-backend-dev.bridgelabz.com/jobs");
        ResponseBody body = response.getBody();
        JSONObject object = (JSONObject) new JSONParser().parse(body.prettyPrint());
        int statusCode = response.getStatusCode();
        String message = (String) object.get("message");
        Assert.assertEquals(200, statusCode);
        Assert.assertEquals("Jobs added successfully", message);
    }

    @Test
    public void givenUser_JobHashtagAdded_ShouldReturnCorrectStatusCode() throws ParseException {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("token", tokenValue)
                .body("{\"job_id\": \"5e0df23122b60600323eb715\",\"hashtag\": \"#bridgelabz\"}")
                .when()
                .post("https://fundoopush-backend-dev.bridgelabz.com/jobs/hashtag/add");
        ResponseBody body = response.getBody();
        JSONObject object = (JSONObject) new JSONParser().parse(body.prettyPrint());
        int statusCode = response.getStatusCode();
        String message = (String) object.get("message");
        Assert.assertEquals(200, statusCode);
        Assert.assertEquals("Jobs hashtag added successfully", message);
    }

    @Test
    public void givenUser_JobHashtagRemove_ShouldReturnCorrectStatusCode() throws ParseException {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("token", tokenValue)
                .body("{ \"job_id\": \"5e0df23122b60600323eb715\",\"hashtag_id\": \"5d39926ab19c56004f263df6\"}")
                .when()
                .post("https://fundoopush-backend-dev.bridgelabz.com/jobs/hashtag/remove");
        ResponseBody body = response.getBody();
        JSONObject object = (JSONObject) new JSONParser().parse(body.prettyPrint());
        int statusCode = response.getStatusCode();
    }
}
