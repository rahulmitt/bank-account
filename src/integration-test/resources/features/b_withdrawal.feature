Feature: Withdrawals are debited from bank account successfully

    Scenario: client makes a POST call and withdraws an amount to /account/{accountId}/debit/
        Given a client tries to withdraw Rs 20 from accountId S1
        When the client withdraws the amount by making a POST call to /account/{accountId}/debit/
        Then for the withdrawal transaction the client receives response code of 200
        And for the withdrawal transaction the client receives a banking transaction for accountId S1 of type debit for amount Rs 20 with successful as true
