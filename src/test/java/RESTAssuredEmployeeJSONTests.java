import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

public class RESTAssuredEmployeeJSONTests {

    private int empId;

    @Before
    public void setUp() throws Exception {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 4000;
        empId = 1;
    }
    
    public Response getEmployeeList() {
        Response response = RestAssured.get("/emp/list");
        return response;
    }

    @Test
    public void onCalling_ReturnemployeeList() {
        Response employeeList = getEmployeeList();
        System.out.println("At First:"+ employeeList.asString());
        employeeList.then().body("id", Matchers.hasItems(1,4,6));
        employeeList.then().body("name",Matchers.hasItems("Shivanjali"));
    }

    @Test
    public void givenEmployee_OnPost_ShouldReturnAddingEmployee() {
           Response response = RestAssured.given()
                   .contentType(ContentType.JSON)
                   .accept(ContentType.JSON)
                   .body("{\"name\":\"Virat\",\"salary\":\"2000\"}")
                   .when()
                   .post("/emp/create");
        String respAsStr = response.asString();
        JsonObject jsonObject = new Gson().fromJson(respAsStr, JsonObject.class);
        int id = jsonObject.get("id").getAsInt();
        response.then().body("id",Matchers.any(Integer.class));
        response.then().body("name",Matchers.is("Virat"));
    }

    @Test
    public void givenEmployee_OnUpdate_ShouldReturnUpdateEmployee() {
        Response response= RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\"name\":\"Shivanjali\",\"salary\":\"5\"}")
                .when()
                .put("/emp/update/"+empId);
        String string = response.asString();
        response.then().body("id",Matchers.any(Integer.class));
        response.then().body("name",Matchers.is("Shivanjali"));
        response.then().body("salary",Matchers.is("5"));
    }

    @Test
    public void givenEmployeeId_OnDelete_ShouldReturnSuccessStatus() {
        Response response = RestAssured.delete("/emp/delete/"+empId);
        String respAsStr = response.asString();
        int statusCode = response.getStatusCode();
        MatcherAssert.assertThat(statusCode, CoreMatchers.is(200));
        response = getEmployeeList();
        System.out.println("AT END:"+response.asString());
        response.then().body("id",Matchers.not(empId));
    }
}
