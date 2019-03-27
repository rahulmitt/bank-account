Feature: Deposits are credited into bank account successfully

    Scenario: client makes a POST call and deposits an amount to /account/{accountId}/credit/
        Given a client tries to deposit Rs 100 to accountId S1
        When the client deposits the amount by making a POST call to /account/{accountId}/credit/
        Then for the deposit transaction the client receives response code of 200
        And for the deposit transaction the client receives a banking transaction for accountId S1 of type credit for amount Rs 100 with successful as true
