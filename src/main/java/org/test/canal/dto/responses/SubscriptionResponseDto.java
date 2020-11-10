package org.test.canal.dto.responses;

import org.test.canal.dto.AddressDto;

public class SubscriptionResponseDto {
    Long id;
    String canalName;
    AddressDto addressDto;
    Long movementId;
    public SubscriptionResponseDto() {

    }

    public SubscriptionResponseDto(AddressDto addressDto, Long movementId) {
        this.addressDto = addressDto;
        this.movementId = movementId;
    }

    public SubscriptionResponseDto(Long id, AddressDto addressDto, Long movementId) {
        this.id = id;
        this.addressDto = addressDto;
        this.movementId = movementId;
    }

    public SubscriptionResponseDto(Long id, String canalName, AddressDto addressDto, Long movementId) {
        this.id = id;
        this.canalName = canalName;
        this.addressDto = addressDto;
        this.movementId = movementId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AddressDto getAddressDto() {
        return addressDto;
    }

    public void setAddressDto(AddressDto addressDto) {
        this.addressDto = addressDto;
    }

    public Long getMovementId() {
        return movementId;
    }

    public void setMovementId(Long movementId) {
        this.movementId = movementId;
    }

    public String getCanalName() {
        return canalName;
    }

    public void setCanalName(String canal) {
        this.canalName = canal;
    }
}
