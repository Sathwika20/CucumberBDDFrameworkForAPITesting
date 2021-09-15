package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.junit.Cucumber;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.Assert;
import org.junit.runner.RunWith;
import resources.TestDataBuild;
import static io.restassured.RestAssured.given;

@RunWith(Cucumber.class)
public class StepDefinition {
    RequestSpecification res;
    ResponseSpecification responseSpecification;
    Response response;
    TestDataBuild data = new TestDataBuild();
    @Given("^Add place payload$")
    public void add_place_payload() throws Throwable {
        RestAssured.baseURI = "https://rahulshettyacademy.com";
        RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .addQueryParam("key","qalick123")
                .setContentType(ContentType.JSON).build();
        responseSpecification =new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
        res = given().spec(req)
                .body(data.addPlacePayload());
    }
    @When("^user calls \"([^\"]*)\" with Post http request$")
    public void user_calls_something_with_post_http_request(String strArg1) throws Throwable {
        response = res.when().log().all()
                .post("/maps/api/place/add/json")
                .then().log().all().spec(responseSpecification).extract().response();
    }
    @Then("^the API call is success with the status code 200$")
    public void the_api_call_is_success_with_the_status_code_200() throws Throwable {
        Assert.assertEquals(response.getStatusCode(),200);
    }
    @And("^\"([^\"]*)\" in response body is \"([^\"]*)\"$")
    public void something_in_response_body_is_something(String keyValue, String ExpectedValue) throws Throwable {
        String resp = response.asString();
        JsonPath js = new JsonPath(resp);
        Assert.assertEquals(js.get(keyValue).toString(),ExpectedValue);
    }
}
