package com.rahul.bankaccount;

import com.rahul.bankaccount.model.AmountDto;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;

import java.math.BigDecimal;
import java.util.Arrays;

public class RestUtility {

    public ResponseEntity<?> post(TestRestTemplate restTemplate, String url, BigDecimal amount, Class clazz) {
//        LinkedMultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
//        body.add("amountDto", new AmountDto(amount));
        AmountDto payload = new AmountDto(amount);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<AmountDto> entity = new HttpEntity<>(payload, headers);
        return restTemplate.exchange(url, HttpMethod.POST, entity, clazz);
    }

    public ResponseEntity<?> get(TestRestTemplate restTemplate, String url, Class clazz) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//        headers.set("userName", userName);
        HttpEntity<LinkedMultiValueMap<String, Object>> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(url, HttpMethod.GET, entity, clazz);
    }
}
