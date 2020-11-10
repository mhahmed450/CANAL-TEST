package org.test.canal.stepDefinition;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import cucumber.api.java.Before;
import cucumber.api.java.fr.Alors;
import cucumber.api.java.fr.Etantdonné;
import cucumber.api.java.fr.Lorsque;
import org.test.canal.dto.AddressDto;
import org.test.canal.dto.MovementDto;
import org.test.canal.dto.SubscriberDto;
import org.test.canal.dto.responses.ContractsSubscriberResponseDto;
import org.test.canal.dto.responses.SubscriptionResponseDto;
import org.test.canal.mock.SubscriptionServiceMock;
import org.test.canal.service.SubsciptionService;
import org.test.canal.dto.requests.SubscriptionDto;

import org.test.canal.parameterTypes.Active;
import org.test.canal.parameterTypes.Canal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriTemplateHandler;
import org.springframework.web.util.UriTemplateHandler;
import org.test.canal.utils.Status;

public class EmployeeSteps extends AbstractSteps {
    private Active active;
    private Canal canal;
    private SubscriberDto subscriberDto;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    SubsciptionService subsciptionService;

    @Autowired
    SubscriptionServiceMock subscriptionServiceMock;

    private SubscriptionResponseDto subscriptionResponseDto;
    private ContractsSubscriberResponseDto contractsSubscriberResponseDto;

    @Before
    public void setup() {
        subscriptionServiceMock.setBaseUrl(baseUrl());

    }

    @Etantdonné("un abonné avec une adresse principale {string} en France")
    public void un_abonné_avec_une_adresse_principale_en_france(String isactive) {
        this.active = new Active(isactive);

        AddressDto addressDto = new AddressDto("test_address", Status.fromText(isactive).get());
        this.subscriberDto = new SubscriberDto("test_abonné", addressDto);


    }


    @Lorsque("le conseiller connecté à {string} modifie l'adresse de l'abonné")
    public void le_conseiller_connecté_à_modifie_l_adresse_de_l_abonné(String canalName) throws JsonProcessingException {
        this.canal = new Canal(canalName);
        MockRestServiceServer mockServer = subscriptionServiceMock.modifyAddress( this.canal.getCanalName(), this.subscriberDto);
        this.subscriptionResponseDto = subsciptionService.modifyAddress(canal.getCanalName(), this.subscriberDto);
        mockServer.verify();
        // test implicit de changement d'adresse : la reponse de de modification d'adresse
        assertEquals(this.subscriptionResponseDto.getCanalName(), canal.getCanalName());
        assertEquals(this.subscriptionResponseDto.getAddressDto().getStatus(), Status.fromText(active.getIsActive()).get());

    }

    @Alors("l'adresse de l'abonné modifiée est enregistrée sur l'ensemble des contrats de l'abonné")
    public void l_adresse_de_l_abonné_modifiée_est_enregistrée_sur_l_ensemble_des_contrats_de_l_abonné() throws JsonProcessingException {


        // verifcation de changement d'adresse
        MockRestServiceServer mockServerGet = subscriptionServiceMock.getAddress( canal.getCanalName(), this.subscriptionResponseDto);
        AddressDto addressResponse = subsciptionService.getAddress(canal.getCanalName(), this.subscriptionResponseDto.getId());
        mockServerGet.verify();

        assertEquals(addressResponse.getName(),subscriberDto.getMainAddress().getName());
        assertEquals(addressResponse.getStatus(),Status.fromText(active.getIsActive()).get());

        // verifiaction de changement d'adresse dans tous les contracts liée a cette adresse
        MockRestServiceServer mockServer = subscriptionServiceMock.getContractBySubscriber( this.subscriptionResponseDto.getId());
        this.contractsSubscriberResponseDto = subsciptionService.getContractBySubscriber(this.subscriptionResponseDto.getId());
        mockServer.verify();

        this.contractsSubscriberResponseDto.getContractDtos().forEach(contract -> {
            assertEquals(contract.getAddress().getName(),subscriberDto.getMainAddress().getName());
            assertEquals(contract.getAddress().getStatus(), Status.fromText(active.getIsActive()).get());
        });
        assertEquals(this.contractsSubscriberResponseDto.getIdSubscriber(), this.subscriptionResponseDto.getId());
    }

    @Alors("un mouvement de modification d'adresse est créé")
    public void un_mouvement_de_modification_d_adresse_est_créé() throws JsonProcessingException {

        // verfication qu'un mouvement est crée
        MockRestServiceServer mockServer = subscriptionServiceMock.addressModificationMovementCheck(this.subscriptionResponseDto.getMovementId());
        MovementDto movementDto = subsciptionService.addressModificationMovementCheck(this.subscriptionResponseDto.getMovementId());
        mockServer.verify();

        assertEquals(movementDto.getId(),this.subscriptionResponseDto.getMovementId());
        assertNotNull(movementDto.getDate());
        assertEquals(movementDto.getAddressDto(),this.subscriptionResponseDto.getAddressDto());

    }

}
