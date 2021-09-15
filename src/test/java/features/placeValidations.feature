Feature: Validating Place API's

  Scenario: Verify if the place is being successfully added using AddPlace API
     Given Add place payload
     When user calls "AddPlaceAPI" with Post http request
     Then the API call is success with the status code 200
     And "status" in response body is "OK"
     And "scope" in response body is "APP"

