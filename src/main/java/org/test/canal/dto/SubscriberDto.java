package org.test.canal.dto;

import java.io.Serializable;
import java.util.List;

public class SubscriberDto implements Serializable {
    Long id;
    String name ;
    AddressDto mainAddress;
    List<ContractDto> contractDtos;

    public SubscriberDto() {
    }

    public SubscriberDto(AddressDto mainAddress) {
        this.mainAddress = mainAddress;
    }

    public SubscriberDto(String name, AddressDto mainAddress) {
        this.name = name;
        this.mainAddress = mainAddress;
    }

    public SubscriberDto(Long id, AddressDto mainAddress, List<ContractDto> contractDtos) {
        this.id = id;
        this.mainAddress = mainAddress;
        this.contractDtos = contractDtos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AddressDto getMainAddress() {
        return mainAddress;
    }

    public void setMainAddress(AddressDto mainAddress) {
        this.mainAddress = mainAddress;
    }

    public List<ContractDto> getContractDtos() {
        return contractDtos;
    }

    public void setContractDtos(List<ContractDto> contractDtos) {
        this.contractDtos = contractDtos;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
