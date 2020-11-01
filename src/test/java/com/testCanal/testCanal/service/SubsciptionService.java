package com.testCanal.testCanal.service;


import com.testCanal.testCanal.dto.SubscriptionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Component
@ContextConfiguration(classes = ApplicationRunner.class)
public class SubsciptionService {

    private final String url;
    @Value("${url.subscription}")
    String subscriptionUrl;
    @Value("${url.movementCheck}")
    String movementCheck;
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    public SubsciptionService(@Value("${url}") final String url){
        this.url =url;
    }
    public String modifyAddress(String canalName, String statusSubscriber ){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        SubscriptionDto subscriptionDto= new SubscriptionDto(canalName,statusSubscriber);
        HttpEntity<SubscriptionDto> entity = new HttpEntity<SubscriptionDto>(subscriptionDto,headers);
       return restTemplate.exchange(subscriptionUrl, HttpMethod.POST, entity, String.class).getBody();
    }
    public String addressModifcationMovementCheck(String canalName, String statusSubscriber){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        SubscriptionDto subscriptionDto= new SubscriptionDto(canalName,statusSubscriber);
        HttpEntity<SubscriptionDto> entity = new HttpEntity<SubscriptionDto>(subscriptionDto,headers);
        return restTemplate.exchange(movementCheck, HttpMethod.POST, entity, String.class).getBody();
    }
}
