package org.test.canal.stepDefinition;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;



import cucumber.api.java.Before;
import cucumber.api.java.fr.Alors;
import cucumber.api.java.fr.Etantdonné;
import cucumber.api.java.fr.Lorsque;
import org.test.canal.service.SubsciptionService;
import org.test.canal.dto.SubscriptionDto;

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

public class EmployeeSteps extends AbstractSteps  {
  private Active active;
  private Canal canal;

  @Value("${uri.subscription}")
  private String subscriptionUrl;
  @Value("${uri.movementCheck}")
  private String movementCheck;

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  SubsciptionService subsciptionService;

  @Before
  public void setup(){


  }
  
  @Etantdonné("un abonné avec une adresse principale {string} en France")
  public void un_abonné_avec_une_adresse_principale_en_france(String isactive) {

    this.active=new Active(isactive);


  }



  @Lorsque("le conseiller connecté à {string} modifie l'adresse de l'abonné")
  public void le_conseiller_connecté_à_modifie_l_adresse_de_l_abonné(String canalName) {
    this.canal=new Canal(canalName);
  

  }
  @Alors("l'adresse de l'abonné modifiée est enregistrée sur l'ensemble des contrats de l'abonné")
  public void l_adresse_de_l_abonné_modifiée_est_enregistrée_sur_l_ensemble_des_contrats_de_l_abonné() throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    MockRestServiceServer mockServer=MockRestServiceServer.createServer(restTemplate);
    SubscriptionDto subscriptionDto= new SubscriptionDto(canal.getCanalName(),active.getIsActive());
    UriTemplateHandler uriTemplate= new DefaultUriTemplateHandler();
    String uriExpanded = uriTemplate.expand(baseUrl()+subscriptionUrl,uriTemplate).toString();
    mockServer.expect(requestTo(uriExpanded)).andExpect(method(HttpMethod.POST))
            .andExpect(content().json(mapper.writeValueAsString(subscriptionDto)))
            .andRespond(withSuccess("modified successfully", MediaType.TEXT_PLAIN));

    String requestResponse = subsciptionService.modifyAddress(canal.getCanalName(), active.getIsActive());
    mockServer.verify();
    assertEquals(requestResponse, "modified successfully");

  }
  @Alors("un mouvement de modification d'adresse est créé")
  public void un_mouvement_de_modification_d_adresse_est_créé() throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
   
    MockRestServiceServer mockServer=MockRestServiceServer.createServer(restTemplate);
    SubscriptionDto subscriptionDto= new SubscriptionDto(canal.getCanalName(),active.getIsActive());
    UriTemplateHandler uriTemplate= new DefaultUriTemplateHandler();
    String uriExpanded = uriTemplate.expand(baseUrl()+movementCheck,uriTemplate).toString();
    mockServer.expect(requestTo(uriExpanded)).andExpect(method(HttpMethod.POST))
            .andExpect(content().json(mapper.writeValueAsString(subscriptionDto)))
            .andRespond(withSuccess("created successfully", MediaType.TEXT_PLAIN));

    String requestResponse =  subsciptionService.addressModifcationMovementCheck(canal.getCanalName(), active.getIsActive());
    mockServer.verify();
    assertEquals(requestResponse, "created successfully");


  }

}
