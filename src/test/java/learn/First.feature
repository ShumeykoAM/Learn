Feature: my first feature test

  Background:
    Given url 'http://localhost:8888' + '/CSABackApp'

  Scenario: register mobile app
    Given path 'rest-api/factor/code-words/get'
    And param consumerId = '2'
    And param uidClient = 'FM34890FU5789N0H578011'
    And param uidMember = 'GHYUBIG5Y3465I6G45YUI11'
    And param type = 'PERSONAL'
    And param version = '1'
    And param login = 'loggg'
    When method GET
    Then status 200
    #And match response /response/status/code == 0
    #And def resp_mGUID = /response/confirmRegistrationStage/mGUID