package org.test.canal.dto.requests;

import org.test.canal.dto.SubscriberDto;

public class SubscriptionDto {

    String canal;
    SubscriberDto subscriberDto;
    public SubscriptionDto() {

    }

    public SubscriptionDto(String canal, SubscriberDto subscriberDto) {
        this.canal = canal;
        this.subscriberDto = subscriberDto;
    }

    public String getCanal() {
        return canal;
    }

    public void setCanal(String canal) {
        this.canal = canal;
    }

    public SubscriberDto getSubscriberDto() {
        return subscriberDto;
    }

    public void setSubscriberDto(SubscriberDto subscriberDto) {
        this.subscriberDto = subscriberDto;
    }
}
