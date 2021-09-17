package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.junit.Cucumber;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.Assert;
import org.junit.runner.RunWith;
import resources.ApiResources;
import resources.TestDataBuild;
import resources.Utils;

import java.io.IOException;

import static io.restassured.RestAssured.given;

@RunWith(Cucumber.class)
public class StepDefinition extends Utils {
    RequestSpecification res;
    ResponseSpecification responseSpecification;
    Response response;
    static String place_id;
    TestDataBuild data = new TestDataBuild();

    @Given("^Add place payload with \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void add_place_payload_with_name_language_address(String name, String language, String address) throws Throwable {
        res = given().spec(requestSpecification())
                .body(data.addPlacePayload(name, language, address));
    }

    @When("^user calls \"([^\"]*)\" with \"([^\"]*)\" http request$")
    public void user_calls_resource_with_method_http_request(String resource, String method) throws Throwable {
        ApiResources resourceApi = ApiResources.valueOf(resource);
        System.out.println(resourceApi.getResource());
        responseSpecification = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
        if (method.equalsIgnoreCase("POST")) {
            response = res.when().post(resourceApi.getResource());
        } else if (method.equalsIgnoreCase("GET")) {
            response = res.when().post(resourceApi.getResource());
        }
    }

    @Then("^the API call is success with the status code 200$")
    public void the_api_call_is_success_with_the_status_code_200() throws Throwable {
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @And("^\"([^\"]*)\" in response body is \"([^\"]*)\"$")
    public void keyValue_in_response_body_is_ExpectedValue(String keyValue, String ExpectedValue) throws Throwable {
        Assert.assertEquals(getJsonPath(response, keyValue), ExpectedValue);
    }

    @Then("verify place_id created maps to {string} using {string}")
    public void verify_place_id_created_maps_to_using(String expectedName, String resource) throws Throwable {
        //request spec
        place_id = getJsonPath(response, "place_id");
        res.given().spec(requestSpecification()
                .queryParam("place_id", place_id));
        user_calls_resource_with_method_http_request(resource, "GET");
        String actualName = getJsonPath(response, "name");
        Assert.assertEquals(actualName, expectedName);
    }

    @Given("DeletePlace Payload")
    public void delete_place_payload() throws IOException {
       res = given().spec(requestSpecification()).body(data.deletePlacePayload(place_id));
    }
    @Then("the API call got success with status code {int}")
    public void the_api_call_got_success_with_status_code(Integer int1) {
        Assert.assertEquals(response.getStatusCode(), 200);
        System.out.println(int1);
    }
}

