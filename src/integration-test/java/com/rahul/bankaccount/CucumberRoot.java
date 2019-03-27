package com.rahul.bankaccount;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.Collections;

@SpringBootTest(classes = BankAccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("local")
@ContextConfiguration
public class CucumberRoot {

    @Autowired
    protected TestRestTemplate restTemplate;

    @Before
    public void before() {
        // adding custom header globally for the http request in test rest template, e.g., user header
        restTemplate.getRestTemplate().setInterceptors(Collections.singletonList((request, body, execution) -> {
            request.getHeaders()
                    .add("userHeader", "user");
            return execution.execute(request, body);
        }));
    }
}
