package org.test.canal.stepDefinition;




import org.test.canal.annotations.ServerHost;
import org.test.canal.annotations.ServerPort;


public abstract class AbstractSteps {

  @ServerPort
  private String port;

  @ServerHost
  private String host;
  
  public String baseUrl() {
    return "http://"+host+":" + port+"/";
  }

}
