Feature: Listing all the transactions

    Scenario: client makes a GET call for listing all the bank transactions to /account/{accountId}/list/
        Given a client tries to list all transactions for accountId S1
        When the client lists all transactions by making a GET call to /account/{accountId}/list/
        Then for the listing operation the client receives response code of 200
        And for the listing operation the client receives 2 transactions
