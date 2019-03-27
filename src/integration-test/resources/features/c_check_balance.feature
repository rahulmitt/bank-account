Feature: Balance checking functionality returns the correct currentBalance

    Scenario: client makes a GET call for checking the bank currentBalance to /account/{accountId}/check/
        Given a client tries to check their balance for accountId S1
        When the client checks their balance by making a GET call to /account/{accountId}/check/
        Then for the check balance transaction the client receives response code of 200
        And for the check balance transaction the client receives a balance of Rs 80