package com.rahul.bankaccount.features;

import com.rahul.bankaccount.CucumberRoot;
import com.rahul.bankaccount.RestUtility;
import com.rahul.bankaccount.model.BankTransaction;
import com.rahul.bankaccount.model.TransactionType;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

public class WithdrawalFeature extends CucumberRoot {

    private BigDecimal amount;

    private String accountId;

    private ResponseEntity<?> responseEntity;

    private HttpStatus actualHttpStatus;

    private BankTransaction bankTransaction;

    @Given("^a client tries to withdraw Rs ([0-9]*) from accountId ([A-Z0-9]*)$")
    public void given(BigDecimal amount, String accountId) {
        this.amount = amount;
        this.accountId = accountId;
    }

    @When("^the client withdraws the amount by making a POST call to (.*)$")
    public void step1(String url) {
        url = enrich(url);
        bankTransaction = doPost(url, amount);
    }

    @Then("^for the withdrawal transaction the client receives response code of (\\d+)$")
    public void step2(int expectedStatusCode) {
        Assert.assertThat(
                "status code is incorrect : " + responseEntity.getBody(),
                actualHttpStatus.value(),
                Is.is(expectedStatusCode)
        );
    }

    @And("^for the withdrawal transaction the client receives a banking transaction " +
            "for accountId ([A-Z0-9]*) of type ([a-zA-Z]*) for amount Rs ([0-9]*) with successful as (true|false)$")
    public void step3(String expectedAccountId, String transactionType, BigDecimal expectedAmount, boolean expectedStatus) {
        TransactionType expectedTransationType = TransactionType.valueOf(transactionType.toUpperCase());

        Assert.assertEquals(expectedAccountId, bankTransaction.getAccountId());
        Assert.assertEquals(expectedTransationType, bankTransaction.getTransactionType());
        Assert.assertEquals(expectedAmount, bankTransaction.getAmount());
        Assert.assertEquals(expectedStatus, bankTransaction.getSuccessful());
    }

    private String enrich(String url) {
        return url.replace("{accountId}", accountId);
    }

    protected BankTransaction doPost(String url, BigDecimal amount) {
        responseEntity = new RestUtility().post(restTemplate, url, amount, BankTransaction.class);

        actualHttpStatus = responseEntity.getStatusCode();

        if (actualHttpStatus == HttpStatus.OK) {
            BankTransaction bankTransaction = (BankTransaction) responseEntity.getBody();
            return bankTransaction;
//            JsonParser jsonParser = new JsonParser();
//            JsonObject jsonObject = (JsonObject) jsonParser.parse(bankTransaction);
//            String accountId = jsonObject.get("accountId").getAsString();
//            String transactionType = jsonObject.get("transactionType").getAsString();
//
//            String amt = jsonObject.get("amount").getAsString();
//            String status = jsonObject.get("status").getAsString();
//            String dateTime = jsonObject.get("dateTime").getAsString();
        } else {
            return null;
        }

    }
}
