package com.rahul.bankaccount.features;

import com.rahul.bankaccount.CucumberRoot;
import com.rahul.bankaccount.RestUtility;
import com.rahul.bankaccount.model.AmountDto;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

public class CheckBalanceFeature extends CucumberRoot {

    private String accountId;

    private ResponseEntity<?> responseEntity;

    private HttpStatus actualHttpStatus;

    private AmountDto amountDto;

    @Given("^a client tries to check their balance for accountId ([A-Z0-9]*)$")
    public void given(String accountId) {
        this.accountId = accountId;
    }

    @When("^the client checks their balance by making a GET call to (.*)$")
    public void step1(String url) {
        url = enrich(url);
        amountDto = doGet(url);
    }

    @Then("^for the check balance transaction the client receives response code of (\\d+)$")
    public void step2(int expectedStatusCode) {
        Assert.assertThat(
                "status code is incorrect : " + responseEntity.getBody(),
                actualHttpStatus.value(),
                Is.is(expectedStatusCode)
        );
    }

    @And("^for the check balance transaction the client receives a balance of Rs (\\d+)")
    public void step3(BigDecimal expectedAmount) {
        Assert.assertEquals(expectedAmount, amountDto.getAmount());
    }

    private String enrich(String url) {
        return url.replace("{accountId}", accountId);
    }

    private AmountDto doGet(String url) {
        responseEntity = new RestUtility().get(restTemplate, url, AmountDto.class);
        actualHttpStatus = responseEntity.getStatusCode();
        if (actualHttpStatus == HttpStatus.OK) {
            AmountDto dto = (AmountDto) responseEntity.getBody();
            return dto;
        } else {
            return null;
        }
    }

}
