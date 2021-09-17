package stepDefinitions;

import io.cucumber.java.Before;

import java.io.IOException;

public class Hooks {
    @Before("@DeletePlace")
    public void beforeScenario() throws Throwable {
    	//execute this code only when place id is null
        //write a code that will give you place id

        StepDefinition m =new StepDefinition();
        if(StepDefinition.place_id==null) {

            m.add_place_payload_with_name_language_address("Shetty", "French", "Asia");
            m.user_calls_resource_with_method_http_request("AddPlaceAPI", "POST");
            m.verify_place_id_created_maps_to_using("Shetty", "getPlaceAPI");
        }
    }
}
