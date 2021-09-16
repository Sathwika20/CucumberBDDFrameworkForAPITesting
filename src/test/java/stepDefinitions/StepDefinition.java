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
import resources.ApiResources;
import resources.TestDataBuild;
import resources.Utils;

import static io.restassured.RestAssured.given;

@RunWith(Cucumber.class)
public class StepDefinition extends Utils {
    RequestSpecification res;
    ResponseSpecification responseSpecification;
    Response response;
    TestDataBuild data = new TestDataBuild();
    @Given("^Add place payload with \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void add_place_payload_with_something_something_something(String name, String language, String address) throws Throwable {
        res = given().spec(requestSpecification())
                .body(data.addPlacePayload(name,language,address));
    }
    @When("^user calls \"([^\"]*)\" with \"([^\"]*)\" http request$")
    public void user_calls_something_with_something_http_request(String resource, String method)throws Throwable {
        ApiResources resourceApi = ApiResources.valueOf(resource);
        System.out.println(resourceApi.getResource());
        responseSpecification =new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
        if(method.equalsIgnoreCase("POST")){
            response = res.when().post(resourceApi.getResource());
        }else if(method.equalsIgnoreCase("GET")) {
            response = res.when().post(resourceApi.getResource());
        }
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
