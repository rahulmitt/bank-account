package com.rahul.bankaccount;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {
                "src/integration-test/resources/features/a_deposits.feature",
                "src/integration-test/resources/features/b_withdrawal.feature",
                "src/integration-test/resources/features/c_check_balance.feature",
                "src/integration-test/resources/features/d_list_all_transactions.feature"
        },
        format = {
                "pretty",
                "html:target/reports/cucumber/html",
                "json:target/cucumber.json",
                "usage:target/usage.json",
                "junit:target/junit.xml"
        }
)
public class CucumberIntegrationIT {
}
