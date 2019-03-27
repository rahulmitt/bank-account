package com.rahul.bankaccount.features;

import com.rahul.bankaccount.CucumberRoot;
import com.rahul.bankaccount.RestUtility;
import com.rahul.bankaccount.model.BankTransaction;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

public class ListAllTransactionsFeature extends CucumberRoot {

    private String accountId;

    private ResponseEntity<?> responseEntity;

    private HttpStatus actualHttpStatus;

    private List<BankTransaction> bankTransactionList;

    @Given("^a client tries to list all transactions for accountId ([A-Z0-9]*)$")
    public void given(String accountId) {
        this.accountId = accountId;
    }

    @When("^the client lists all transactions by making a GET call to (.*)$")
    public void step1(String url) {
        url = enrich(url);
        bankTransactionList = doGet(url);
    }

    @Then("^for the listing operation the client receives response code of (\\d+)$")
    public void step2(int expectedStatusCode) {
        Assert.assertThat(
                "status code is incorrect : " + responseEntity.getBody(),
                actualHttpStatus.value(),
                Is.is(expectedStatusCode)
        );
    }

    @And("^for the listing operation the client receives (\\d+) transactions$")
    public void step3(int expectedTransactionCount) {
        Assert.assertEquals(expectedTransactionCount, bankTransactionList.size());
    }

    private String enrich(String url) {
        return url.replace("{accountId}", accountId);
    }

    private List<BankTransaction> doGet(String url) {
        responseEntity = new RestUtility().get(restTemplate, url, BankTransaction[].class);
        actualHttpStatus = responseEntity.getStatusCode();
        if (actualHttpStatus == HttpStatus.OK) {

            List<BankTransaction> transactionList = Arrays.asList((BankTransaction[]) responseEntity.getBody());
            return transactionList;
        } else {
            return null;
        }
    }

}
