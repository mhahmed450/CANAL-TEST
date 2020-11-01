package org.test.canal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;


import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.test.canal.dto.SubscriptionDto;

import java.util.Arrays;

@Component
public class SubsciptionService {

    @Value("${url.subscription}")
    String subscriptionUrl;
    @Value("${url.movementCheck}")
    String movementCheck;
    @Autowired
    RestTemplate restTemplate;

  
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
