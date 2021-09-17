Feature: Validating Place API's

@AddPlace
   Scenario Outline: Verify if the place is being successfully added using AddPlace API
      Given Add place payload with "<name>" "<language>" "<address>"
      When user calls "addPlaceAPI" with "POST" http request
      Then the API call is success with the status code 200
      And "status" in response body is "OK"
      And "scope" in response body is "APP"
      And verify place_id created maps to "<name>" using "getPlaceAPI"




      Examples:
         | name    | language |     address       |
         | AAhouse | English  | world cross centre|
         | BBhouse | Spanish  | sea crosee centre |

@DeletePlace
   Scenario: Verify if Delete place functionality is working
      Given DeletePlace Payload
      When user calls "deletePlaceAPI" with "POST" http request
      Then the API call got success with status code 200
      And "status" in response body is "OK"