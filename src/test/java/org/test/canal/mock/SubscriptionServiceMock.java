package org.test.canal.mock;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriTemplateHandler;
import org.springframework.web.util.UriTemplateHandler;
import org.test.canal.dto.AddressDto;
import org.test.canal.dto.ContractDto;
import org.test.canal.dto.MovementDto;
import org.test.canal.dto.SubscriberDto;
import org.test.canal.dto.requests.SubscriptionDto;
import org.test.canal.dto.responses.ContractsSubscriberResponseDto;
import org.test.canal.dto.responses.SubscriptionResponseDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@Service

public class SubscriptionServiceMock {

    private String baseUrl;

    @Value("${uri.subscription}")
    private String subscriptionUrl;
    @Value("${uri.getAddress}")
    private String getAddress;

    @Value("${uri.movementCheck}")
    private String movementCheckUrl;
    @Value("${uri.getContractsBySubscriber}")
    private String getContractsBySubscriberUrl;
    @Autowired
    private RestTemplate restTemplate;
    private SubscriptionResponseDto subscriptionResponseDto;

    public MockRestServiceServer modifyAddress(String canalName, SubscriberDto subscriberDto) throws JsonProcessingException {
        AddressDto responseAddress = subscriberDto.getMainAddress();

        this.subscriptionResponseDto = new SubscriptionResponseDto(125478L, canalName, responseAddress, 1452L);

        ObjectMapper mapper = new ObjectMapper();
        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
        SubscriptionDto subscriptionDto = new SubscriptionDto(canalName, subscriberDto);
        UriTemplateHandler uriTemplate = new DefaultUriTemplateHandler();
        String uriExpanded = uriTemplate.expand(baseUrl + subscriptionUrl, uriTemplate).toString();
        mockServer.expect(requestTo(uriExpanded)).andExpect(method(HttpMethod.POST))
                .andExpect(content().json(mapper.writeValueAsString(subscriptionDto)))
                .andRespond(withSuccess(mapper.writeValueAsString(subscriptionResponseDto), MediaType.APPLICATION_JSON));


        return mockServer;
    }

    public MockRestServiceServer getAddress(String canalName, SubscriptionResponseDto subscriptionResponseDto) throws JsonProcessingException {

        AddressDto addressResponseDto = new AddressDto(125478L, "test_address", subscriptionResponseDto.getAddressDto().getStatus());

        ObjectMapper mapper = new ObjectMapper();
        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
        UriTemplateHandler uriTemplate = new DefaultUriTemplateHandler();
        String uriExpanded = uriTemplate.expand(baseUrl + getAddress+"/"+canalName+"/"+subscriptionResponseDto.getId(), uriTemplate).toString();
        mockServer.expect(requestTo(uriExpanded)).andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(mapper.writeValueAsString(addressResponseDto), MediaType.APPLICATION_JSON));


        return mockServer;
    }


    public MockRestServiceServer getContractBySubscriber(Long idSubscriber) throws JsonProcessingException {
        AddressDto addressDto = this.subscriptionResponseDto.getAddressDto();
        List<ContractDto> contractDtos = new ArrayList<>();
        contractDtos.add(new ContractDto(idSubscriber, "contract1", addressDto));
        contractDtos.add(new ContractDto(idSubscriber, "contract2", addressDto));
        contractDtos.add(new ContractDto(idSubscriber, "contract3", addressDto));
        ContractsSubscriberResponseDto contractsSubscriberResponseDto = new ContractsSubscriberResponseDto(idSubscriber, contractDtos);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
        UriTemplateHandler uriTemplate = new DefaultUriTemplateHandler();
        String uriExpanded = uriTemplate.expand(baseUrl + getContractsBySubscriberUrl + "/" + idSubscriber, uriTemplate).toString();
        mockServer.expect(requestTo(uriExpanded)).andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(mapper.writeValueAsString(contractsSubscriberResponseDto), MediaType.APPLICATION_JSON));


        return mockServer;
    }

    public MockRestServiceServer addressModificationMovementCheck(Long idMovement) throws JsonProcessingException {

        MovementDto movementDto = new MovementDto(idMovement, new Date(), this.subscriptionResponseDto.getAddressDto());

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
        UriTemplateHandler uriTemplate = new DefaultUriTemplateHandler();
        String uriExpanded = uriTemplate.expand(baseUrl + movementCheckUrl + "/" + idMovement, uriTemplate).toString();
        mockServer.expect(requestTo(uriExpanded)).andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(mapper.writeValueAsString(movementDto), MediaType.APPLICATION_JSON));


        return mockServer;

    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}
