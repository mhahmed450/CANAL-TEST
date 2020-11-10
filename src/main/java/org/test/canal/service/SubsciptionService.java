package org.test.canal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;


import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import org.test.canal.dto.AddressDto;

import org.test.canal.dto.MovementDto;
import org.test.canal.dto.SubscriberDto;
import org.test.canal.dto.requests.SubscriptionDto;
import org.test.canal.dto.responses.ContractsSubscriberResponseDto;
import org.test.canal.dto.responses.SubscriptionResponseDto;

import java.util.Arrays;

@Component
public class SubsciptionService {

    @Value("${url.subscription}")
    String subscriptionUrl;

    @Value("${url.getAddress}")
    String getAddressUrl;

    @Value("${url.getContractsBySubscriber}")
    String getContractsBySubscriberUrl;
    @Value("${url.movementCheck}")
    String movementCheck;
    @Autowired
    RestTemplate restTemplate;

  
    public SubscriptionResponseDto modifyAddress(String canalName, SubscriberDto subscriberDto){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        SubscriptionDto subscriptionDto= new SubscriptionDto(canalName,subscriberDto);
        HttpEntity<SubscriptionDto> entity = new HttpEntity<SubscriptionDto>(subscriptionDto,headers);
       return restTemplate.exchange(subscriptionUrl, HttpMethod.POST, entity, SubscriptionResponseDto.class).getBody();
    }

    public AddressDto getAddress(String canalName, Long subscriberId){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<AddressDto> entity = new HttpEntity<>(null,headers);
        return restTemplate.exchange(getAddressUrl+"/"+canalName+"/"+subscriberId, HttpMethod.GET, entity, AddressDto.class).getBody();
    }
    public ContractsSubscriberResponseDto getContractBySubscriber(Long idSubscriber){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<SubscriptionDto> requestEntity = new HttpEntity<SubscriptionDto>(null,headers);
        return restTemplate.exchange(getContractsBySubscriberUrl+"/"+idSubscriber, HttpMethod.GET, requestEntity, ContractsSubscriberResponseDto.class).getBody();

    }

    public MovementDto addressModificationMovementCheck(Long idMovement){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<SubscriptionDto> entity = new HttpEntity<SubscriptionDto>(null,headers);
        return restTemplate.exchange(movementCheck+"/"+idMovement, HttpMethod.GET, entity, MovementDto.class).getBody();
    }
}
